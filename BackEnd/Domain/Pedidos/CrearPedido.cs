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
    public class CrearPedido : IRequest<bool>
    {
        public CrearPedidosModel pedido;
        public CrearPedido(CrearPedidosModel pedido)
        {
            this.pedido = pedido;
        }
    }
    public class CrearPedidoHandler : IRequestHandler<CrearPedido, bool>
    {
        private readonly IMongoCollection<MongoPedido> pedidos;

        public CrearPedidoHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            pedidos = db.GetCollection<MongoPedido>(settings.Collections[2]);
        } 
        public async Task<bool> Handle(CrearPedido request, CancellationToken cancellationToken)
        {
            var folioId = 0;
            var sort = Builders<MongoPedido>.Sort.Descending("folioPedido");
            var resultado = await pedidos.Find(FilterDefinition<MongoPedido>.Empty).Sort(sort).FirstOrDefaultAsync();
            if (resultado is null)
            {
                folioId = 1;
            }
            else
            {
                folioId = resultado.folioPedido + 1;
            }
            if(request.pedido.servicios is null || request.pedido.servicios.Count < 1)
            {
                request.pedido.servicios = new List<ServiciosModelCrear>();
            }
            await pedidos.InsertOneAsync(new MongoPedido()
            {
                folioPedido = folioId,
                cliente = request.pedido.cliente,
                monto= request.pedido.monto,
                abonado = request.pedido.abonado,
                usuario = request.pedido.usuario,
                descripcion = request.pedido.descripcion,
                estadoPedido = 1,
                fechaEstimada = request.pedido.fechaEstimada,
                fechaPedido = DateTime.Now.ToString("dd/MM/yyyy HH:mm:ss"),
                servicios = request.pedido.servicios
            });
            return true;
        }
    }
    public class CrearPedidosModel
    {
        public int usuario { get; set; }
        public string descripcion { get; set; }
        public string fechaEstimada { get; set; }
        public int cliente { get; set; }
        public float abonado { get; set; }
        public float monto { get; set; }

        public List<ServiciosModelCrear> servicios { get; set; }     
        
    }
    public class ServiciosModelCrear
    {
        public int folio { get; set; }
        public string nombre { get; set; }
        public float costo { get; set; }
    }
}
