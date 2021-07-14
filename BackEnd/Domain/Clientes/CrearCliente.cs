using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using ApiZapapp.DBConfiguration;
using ApiZapapp.Models;
using MediatR;
using MongoDB.Driver;


namespace ApiZapapp.Domain
{
    public class CrearCliente : IRequest<bool>
    {
        public CrearCliente(UsuarioCrearModel usuario)
        {
            this.usuario = usuario;
        }
        public UsuarioCrearModel usuario { get; set; }
    }

    public class CrearClienteHandler : IRequestHandler<CrearCliente, bool>
    {
        private readonly IMongoCollection<MongoUsuario> Usuarios;

        public CrearClienteHandler(IZapatosDatabaseSettings settings)
        {

            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            Usuarios = db.GetCollection<MongoUsuario>(settings.Collections[0]);
        }

        public async Task<bool> Handle(CrearCliente request, CancellationToken cancellationToken)
        {
            var id = 0;
            var req = request.usuario;
            var filter = Builders<MongoUsuario>.Filter.Eq("entidad.telefono", request.usuario.entidad.telefono);
            var filter2 = Builders<MongoUsuario>.Filter.Eq("nombreUsuario", request.usuario.entidad.telefono);
            var sort = Builders<MongoUsuario>.Sort.Descending("idUsuario");
            var usuarioTask = await Usuarios.FindAsync<MongoUsuario>(filter | filter2);
            var usuariosLista = usuarioTask.ToList();

            if (usuariosLista.Count() > 0)
            {
                return false;
            }
            else
            {
                var res = await Usuarios.Find<MongoUsuario>(FilterDefinition<MongoUsuario>.Empty).Sort(sort).FirstOrDefaultAsync();
                if (request.usuario.entidad.correo is null)
                {
                    req.entidad.correo = " ";
                }
                if (res is null)
                {
                    id = 1;
                }
                else
                {
                    id = res.idUsuario + 1;
                }
                if(req.contraseña is null)
                {
                    req.contraseña = "hola";
                }
                await Usuarios.InsertOneAsync(new MongoUsuario()
                {
                    idUsuario = id,
                    nombreUsuario = req.entidad.telefono,
                    contraseña = req.contraseña,
                    rol = 1,
                    estado = 1,
                    entidad = new Entidad()
                    {
                        nombre = req.entidad.nombre,
                        apellido = req.entidad.apellido,
                        identificador = id,
                        disponible = 1,
                        correo = req.entidad.correo,
                        telefono = req.entidad.telefono
                    }
                });
                return true;

            }
        }
    }

    public class UsuarioCrearModel
    {
        public string contraseña { get; set; }
        public EntidadCrearModel entidad { get; set; }

    }
    public class EntidadCrearModel
    {       
        public string nombre { get; set; }
        public string apellido { get; set; }
        public string telefono { get; set; }
        public string correo { get; set; }
    }
}
