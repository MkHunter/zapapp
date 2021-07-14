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

namespace ApiZapapp.Domain.Pedidos
{
    public class LeerEstadoPedido : IRequest<LeerEstadoModel>
    {
        public int id;
        public LeerEstadoPedido(int id)
        {
            this.id = id;
        }
    }
    public class LeerEstadoPedidoHandler : IRequestHandler<LeerEstadoPedido, LeerEstadoModel>
    {
        public LeerEstadoPedidoHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            postHilos = db.GetCollection<MongoPedido>(settings.Collections[2]);
        }

        public IMongoCollection<MongoPedido> postHilos { get; private set; }

        public async Task<LeerEstadoModel> Handle(LeerEstadoPedido request, CancellationToken cancellationToken)
        {
            var filter = Builders<MongoPedido>.Filter.Eq("folioPedido", request.id);
            var res = (await postHilos.FindAsync<MongoPedido>(filter)).FirstOrDefault();


            return new LeerEstadoModel()
            {
                estadoPedido = res.estadoPedido,
                folioPedido = res.folioPedido,
                fechaPedido = res.fechaPedido,
                horaRequest = DateTime.Now.ToString("dd/MM/yyyy HH:mm")

            };
        }

    }
    public class LeerEstadoModel
    {
        public int folioPedido { get; set; }
        public string fechaPedido { get; set; }
        public int estadoPedido { get; set; }
        public string horaRequest { get; set; }

    }
}