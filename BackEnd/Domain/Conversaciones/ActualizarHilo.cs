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
    public class ActualizarHilo : IRequest<EditarConversacionModel>
    {
        public ActualizarHilo(EditarConversacionModel editarConversacion)
        {
            this.editarConversacion = editarConversacion;
        }

        public EditarConversacionModel editarConversacion { get; set; }
    }
    public class ActualizarHiloHandler : IRequestHandler<ActualizarHilo, EditarConversacionModel>
    {
        private IMongoCollection<MongoConversacion> postHilos;

        public ActualizarHiloHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            postHilos = db.GetCollection<MongoConversacion>(settings.Collections[3]);
        }

        public async Task<EditarConversacionModel> Handle(ActualizarHilo request, CancellationToken cancellationToken)
        {

            var idCon = 0;
            var filter = Builders<MongoConversacion>.Filter.Eq("idHilo", request.editarConversacion.idHilo);
            var datos = await postHilos.FindAsync<MongoConversacion>(filter).Result.FirstOrDefaultAsync();
            var conver = datos.conversaciones;

            if (conver.Count == 0)
            {
                idCon = 1;
            }
            else
            {
                var ElementoUltimo = conver.OrderByDescending(x => x.idConversacion).FirstOrDefault();
                idCon = ElementoUltimo.idConversacion + 1;
            }
            var ListSort = conver.OrderByDescending(x => x.idConversacion).FirstOrDefault();

            var fecha = DateTime.Now.ToString("dd/MM/yyyy HH:mm:ss");
            request.editarConversacion.fechaConversacion = fecha;
            conver.Add(new Conversaciones()
            {
                fechaConversacion = fecha,
                idConversacion = idCon,
                mensaje = request.editarConversacion.conversacion.mensaje,
                usuario = request.editarConversacion.usuario
            });

            datos.conversaciones = conver;

            postHilos.ReplaceOne(filter, datos);



            return request.editarConversacion;
        }
    }
    public class EditarConversacionModel
    {
        public int idHilo { get; set; }
        public EditarConversacion conversacion { get; set; }
        public int usuario { get; set; }
        public string fechaConversacion { get;set;}
    }
    public class EditarConversacion
    {
        public string mensaje { get; set; }
    }

}
