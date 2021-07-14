using ApiZapapp.DBConfiguration;
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
    public class LeerTodosPedidos : IRequest<List<LeerTodosPedidosModel>>
    {
        
        public LeerTodosPedidos()
        {
            
        }
    }
    public class LeerTodosPedidosHandler : IRequestHandler<LeerTodosPedidos, List<LeerTodosPedidosModel>>
    {
        public LeerTodosPedidosHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            postHilos = db.GetCollection<MongoPedido>(settings.Collections[2]);
        }

        public IMongoCollection<MongoPedido> postHilos { get; private set; }

        public async Task<List<LeerTodosPedidosModel>> Handle(LeerTodosPedidos request, CancellationToken cancellationToken)
        {
            var res = await postHilos.FindAsync<MongoPedido>(con => true);
            var conver = res.ToList().Select(ped =>
                       new LeerTodosPedidosModel()
                       {
                            usuario = ped.usuario,
                            folioPedido = ped.folioPedido,
                            descripcion = ped.descripcion,
                            estadoPedido = ped.estadoPedido,
                            abonado = ped.abonado,
                            cliente = ped.cliente,
                            fechaEstimada = ped.fechaEstimada,
                            fechaPedido = ped.fechaPedido,
                            monto = ped.monto,
                           horaRequest = DateTime.Now.ToString("dd/MM/yyyy HH:mm:ss")

                       });
            return conver.ToList();
        }

    }
    public class LeerTodosPedidosModel
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