using ApiZapapp.DBConfiguration;
using ApiZapapp.Models;
using MediatR;
using MediatR.Pipeline;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
namespace ApiZapapp.Domain.Pedidos
{
    public class LeerPedidosFiltro : IRequest<List<LeerPedidoFiltroModel>>
    {
        public Filtro filtro;
        public LeerPedidosFiltro(Filtro filtro)
        {
            this.filtro = filtro;
        }
    }
    public class LeerPedidosFiltroHandler : IRequestHandler<LeerPedidosFiltro, List<LeerPedidoFiltroModel>>
    {
        private readonly IMongoCollection<MongoPedido> pedidos;

        public LeerPedidosFiltroHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            pedidos = db.GetCollection<MongoPedido>(settings.Collections[2]);
        }
        public async Task<List<LeerPedidoFiltroModel>> Handle(LeerPedidosFiltro request, CancellationToken cancellationToken)
        {
            
            var filter = Builders<MongoPedido>.Filter.Eq(request.filtro.nombreFiltro, request.filtro.valorFiltro);
            var datos = await pedidos.FindAsync<MongoPedido>(filter);
            var res = datos.ToList().Select(pedido =>
                        new LeerPedidoFiltroModel()
                        {
                            folioPedido = pedido.folioPedido,
                            cliente = pedido.cliente,
                            monto = pedido.monto,
                            abonado = pedido.abonado,
                            usuario = pedido.usuario,
                            descripcion = pedido.descripcion,
                            estadoPedido = pedido.estadoPedido,
                            fechaEstimada = pedido.fechaEstimada,
                            fechaPedido = pedido.fechaPedido,
                            horaRequest = DateTime.Now.ToString("dd/MM/yyyy HH:mm:ss")
                        }
 
                 );
            return res.ToList();
        }
    }
 
    public class Filtro
    {
        public string nombreFiltro{ get; set; }
        public string valorFiltro { get; set; }
    }
    public class LeerPedidoFiltroModel
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
        public string horaRequest { get; set; }

    }
}
