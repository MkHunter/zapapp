using ApiZapapp.DBConfiguration;
using ApiZapapp.Models;
using MediatR;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace ApiZapapp.Domain.Conversaciones
{
    public class LeerHiloId : IRequest<DetalleConversacionModel>
    {
        public int id;
        public LeerHiloId(int id)
        {
            this.id = id;
        }
    }
    public class LeerHiloIdHandler : IRequestHandler<LeerHiloId, DetalleConversacionModel>
    {
        public LeerHiloIdHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            postHilos = db.GetCollection<MongoConversacion>(settings.Collections[3]);
        }

        public IMongoCollection<MongoConversacion> postHilos { get; private set; }

        public async Task<DetalleConversacionModel> Handle(LeerHiloId request, CancellationToken cancellationToken)
        {
            var filter = Builders<MongoConversacion>.Filter.Eq("idHilo", request.id);
            var res = (await postHilos.FindAsync<MongoConversacion>(filter)).FirstOrDefault();


            return new DetalleConversacionModel()
            {
                idHilo = res.idHilo,
                usuarioPost = res.usuarioPost,
                fechaPost = res.fechaPost,
                estado = res.estado,
                postPrincipal = res.postPrincipal,
                imagenPostPrincipal = res.imagenPostPrincipal,
                conversaciones = res.conversaciones,
                horaRequest = DateTime.Now.ToString("dd/MM/yyyy HH:mm:ss")
            };

        }
    }
    public class DetalleConversacionModel
    {
        public int idHilo { get; set; }
        public int usuarioPost { get; set; }
        public string fechaPost { get; set; }
        public int estado { get; set; }
        public string postPrincipal { get; set; }
        public List<string> imagenPostPrincipal { get; set; }
        public string horaRequest { get; set; }
        public List<Conversaciones> conversaciones { get; set; }
    }

}
