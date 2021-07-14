using ApiZapapp.DBConfiguration;
using ApiZapapp.Models;
using MediatR;
using MongoDB.Driver;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace ApiZapapp.Domain.Servicios
{
    public class LeerTodosClientes : IRequest<List<EntidadLeerModel>>
    {

    }
    public class LeerTodosClientesHandler : IRequestHandler<LeerTodosClientes, List<EntidadLeerModel>>
    {
        private readonly IMongoCollection<MongoUsuario> Usuarios;

        public LeerTodosClientesHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            Usuarios = db.GetCollection<MongoUsuario>(settings.Collections[0]);
        }

        public async Task<List<EntidadLeerModel>> Handle(LeerTodosClientes request, CancellationToken cancellationToken)
        {
            var filter = Builders<MongoUsuario>.Filter.Eq("rol",1);
            var resultado = await Usuarios.FindAsync<MongoUsuario>(filter);
            var res = resultado.ToList().Select(ent =>
                
                new EntidadLeerModel()
                {
                     identificador = ent.entidad.identificador,
                     nombre = ent.entidad.nombre,
                     apellido = ent.entidad.apellido,
                     correo = ent.entidad.correo,
                     telefono = ent.entidad.telefono
                });

            return res.ToList();
        }
    }
    public class EntidadLeerModel
    {
        public int identificador { get; set; }
        public string nombre { get; set; }
        public string apellido { get; set; }
        public string telefono { get; set; }
        public string correo { get; set; }
    }
}
