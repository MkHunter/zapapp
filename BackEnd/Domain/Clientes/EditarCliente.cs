using ApiZapapp.DBConfiguration;
using ApiZapapp.Domain.Pedidos;
using ApiZapapp.Models;
using MediatR;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace ApiZapapp.Domain.Clientes
{
    public class EditarCliente : IRequest<bool>
    {
        public EditarClienteModel editarCliente;
        public EditarCliente(EditarClienteModel editarCliente)
        {
            this.editarCliente = editarCliente;
        }
    }
    public class EditarClienteHandler : IRequestHandler<EditarCliente, bool>
    {
        public EditarClienteHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            Usuarios = db.GetCollection<MongoUsuario>(settings.Collections[0]);
        }

        public IMongoCollection<MongoUsuario> Usuarios { get; private set; }

        public async Task<bool> Handle(EditarCliente request, CancellationToken cancellationToken)
        {
            var entidad = request.editarCliente;
            var filter = Builders<MongoUsuario>.Filter.Eq("entidad.identificador", request.editarCliente.identificador);
            var filter2 = Builders<MongoUsuario>.Filter.Eq("rol", 1);
            var res = (await Usuarios.FindAsync<MongoUsuario>(filter & filter2)).FirstOrDefault();

            if(res is null)
            {
                return false;
            }
            res.entidad.nombre = entidad.nombre;
            res.entidad.apellido = entidad.apellido;
            res.entidad.correo = entidad.correo;

            Usuarios.ReplaceOne(us => us.entidad.identificador == entidad.identificador, res);
            return true;
        }

    }
    public class EditarClienteModel
    {
        public int identificador { get; set; }
        public string nombre { get; set; }
        public string apellido { get; set; }
        public string correo { get; set; }
    }
}