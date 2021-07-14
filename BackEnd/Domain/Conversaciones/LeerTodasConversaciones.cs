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
    public class LeerTodasConversaciones : IRequest<List<LeerConversacionModel>>
    {
        public LeerTodasConversaciones()
        {

        }
    }
    public class LeerConversacionHandler : IRequestHandler<LeerTodasConversaciones, List<LeerConversacionModel>>
    {
        public LeerConversacionHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            postHilos = db.GetCollection<MongoConversacion>(settings.Collections[3]);
        }

        public IMongoCollection<MongoConversacion> postHilos { get; private set; }

        public async Task<List<LeerConversacionModel>> Handle(LeerTodasConversaciones request, CancellationToken cancellationToken)
        {

            var res = await postHilos.FindAsync<MongoConversacion>(con => true);
            var conver = res.ToList().Select(hilo =>
                       new LeerConversacionModel()
                       {
                           idHilo = hilo.idHilo,
                           usuarioPost = hilo.usuarioPost,
                           fechaPost = hilo.fechaPost,
                           estado = hilo.estado,
                           postPrincipal = hilo.postPrincipal,
                           imagenPostPrincipal = hilo.imagenPostPrincipal,
                           horaRequest = DateTime.Now.ToString("dd/MM/yyyy HH:mm:ss")
                       });
            return conver.ToList();
        }
    }
    public class LeerConversacionModel
    {
        public int idHilo { get; set; }
        public int usuarioPost { get; set; }
        public string fechaPost { get; set; }
        public int estado { get; set; }
        public string postPrincipal { get; set; }
        public string horaRequest { get; set; }

        public List<string> imagenPostPrincipal { get; set; }
    }

}
