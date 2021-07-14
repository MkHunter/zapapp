using ApiZapapp.DBConfiguration;
using ApiZapapp.Models;
using MediatR;
using Microsoft.Extensions.Configuration;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace ApiZapapp.Domain.Conversaciones
{
    public class ActualizarEstadoHilo : IRequest<bool>
    {
        public ActualizarEstadoHilo(EditarEstadoModel editarEstadoModel)
        {
            this.editarEstadoModel = editarEstadoModel;
        }

        public EditarEstadoModel editarEstadoModel { get; set; }
    }
    public class ActualizarEstadoHiloHandler : IRequestHandler<ActualizarEstadoHilo, bool>
    {
        private IMongoCollection<MongoConversacion> postHilos;

        public ActualizarEstadoHiloHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            postHilos = db.GetCollection<MongoConversacion>(settings.Collections[3]);
        }

        public async Task<bool> Handle(ActualizarEstadoHilo request, CancellationToken cancellationToken)
        {

            var filter = Builders<MongoConversacion>.Filter.Eq("idHilo", request.editarEstadoModel.idHilo);
            var datos = await postHilos.FindAsync<MongoConversacion>(filter).Result.FirstOrDefaultAsync();

            datos.estado = request.editarEstadoModel.estado;
            postHilos.ReplaceOne(filter, datos);
            return true;
        }
    }
    public class EditarEstadoModel
    {
        public int idHilo { get; set; }
        public int estado { get; set; }
    }
}
