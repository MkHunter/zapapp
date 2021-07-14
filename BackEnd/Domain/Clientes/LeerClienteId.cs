using ApiZapapp.DBConfiguration;
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
    public class LeerClienteId : IRequest<LeerClienteIDModel>
    {
        public int _id;

        public LeerClienteId(int id)
        {
            this._id = id;
        }
    }
    public class LeerClienteIdHandler : IRequestHandler<LeerClienteId, LeerClienteIDModel>
    {
        private readonly IMongoCollection<MongoUsuario> entidad;

        public LeerClienteIdHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            entidad = db.GetCollection<MongoUsuario>(settings.Collections[0]);
        }

        public async Task<LeerClienteIDModel> Handle(LeerClienteId request, CancellationToken cancellationToken)
        {
            var filter = Builders<MongoUsuario>.Filter.Eq("entidad.identificador", request._id);
            var filter2 = Builders<MongoUsuario>.Filter.Eq("rol", 1);
            var res = (await entidad.FindAsync<MongoUsuario>(filter & filter2)).FirstOrDefault();


            return new LeerClienteIDModel()
            {
                identificador = res.entidad.identificador,
                nombre = res.entidad.nombre,
                apellido = res.entidad.apellido,
                correo = res.entidad.correo,
                telefono = res.entidad.telefono,

            };
        }
    }

    public class LeerClienteIDModel
    {
        public int identificador { get; set; }
        public string nombre { get; set; }
        public string apellido { get; set; }
        public string telefono { get; set; }
        public string correo { get; set; }
    }
    
}
