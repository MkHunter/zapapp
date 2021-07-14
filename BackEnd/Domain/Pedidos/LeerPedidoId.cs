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
    public class LeerPedidoId : IRequest<LeerPedidosIdModel>
    {
        public int id;
        public LeerPedidoId(int id)
        {
            this.id = id;
        }
    }
    public class LeerPedidoIdHandler : IRequestHandler<LeerPedidoId, LeerPedidosIdModel>
    {
        public LeerPedidoIdHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            postHilos = db.GetCollection<MongoPedido>(settings.Collections[2]);
        }

        public IMongoCollection<MongoPedido> postHilos { get; private set; }

        public async Task<LeerPedidosIdModel> Handle(LeerPedidoId request, CancellationToken cancellationToken)
        {
            var filter = Builders<MongoPedido>.Filter.Eq("folioPedido", request.id);
            var res = (await postHilos.FindAsync<MongoPedido>(filter)).FirstOrDefault();


            return new LeerPedidosIdModel()
            {
                usuario = res.usuario,
                folioPedido = res.folioPedido,
                descripcion = res.descripcion,
                estadoPedido = res.estadoPedido,
                abonado = res.abonado,
                cliente = res.cliente,
                fechaEstimada = res.fechaEstimada,
                fechaPedido = res.fechaPedido,
                monto = res.monto,
                servicios = res.servicios,
                horaRequest = DateTime.Now.ToString("dd/MM/yyyy HH:mm")

            };
        }
        
    }
    public class LeerPedidosIdModel
    {
        public int usuario { get; set; }
        public int folioPedido { get; set; }
        public string descripcion { get; set; }
        public string fechaPedido { get; set; }
        public string fechaEstimada { get; set; }
        public int cliente { get; set; }
        public int estadoPedido { get; set; }
        public float abonado { get; set; }
        public float monto { get; set; }
        public string horaRequest{ get; set; }
        public List<ServiciosModelCrear> servicios { get; set; }
    }
}