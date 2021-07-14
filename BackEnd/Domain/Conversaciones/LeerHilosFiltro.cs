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
    public class LeerHilosFiltro : IRequest<List<LeerConversacionModel>>
    {
        public FiltroHilo filtro;
        public LeerHilosFiltro(FiltroHilo filtro)
        {
            this.filtro = filtro;
        }
    }
    public class LeerHilosFiltroHandler : IRequestHandler<LeerHilosFiltro, List<LeerConversacionModel>>
    {
        public LeerHilosFiltroHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            postHilos = db.GetCollection<MongoConversacion>(settings.Collections[3]);
        }

        public IMongoCollection<MongoConversacion> postHilos { get; private set; }

        public async Task<List<LeerConversacionModel>> Handle(LeerHilosFiltro request, CancellationToken cancellationToken)
        {
            List<MongoConversacion> ListaFiltrada;
            var filter = Builders<MongoConversacion>.Filter.Eq(request.filtro.nombreFiltro, request.filtro.valorFiltro);
            var res = await postHilos.FindAsync<MongoConversacion>(filter);
            ListaFiltrada = res.ToList();
            if (request.filtro.estado != 0)
            {
                ListaFiltrada = ListaFiltrada.Where(c => c.estado == request.filtro.estado).ToList();
            }
            var conver = ListaFiltrada.Select(hilo =>
                       new LeerConversacionModel()
                       {
                           idHilo = hilo.idHilo,
                           usuarioPost = hilo.usuarioPost,
                           fechaPost = hilo.fechaPost,
                           estado = hilo.estado,
                           postPrincipal = hilo.postPrincipal,
                           horaRequest = DateTime.Now.ToString("dd/MM/yyyy HH:mm:ss")
                       });
            return conver.ToList();
        }
    }
    public class FiltroHilo
    {
        public string nombreFiltro { get; set; }
        public string valorFiltro { get; set; }
        public int estado { get; set; }
    }

}
