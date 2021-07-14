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

namespace ApiZapapp.Domain.Reportes
{
    public class LeerServicios : IRequest<List<responseReporteServicios>>
    {
        public LeerServicios()
        {

        }
    }
    public class LeerServiciosHandler : IRequestHandler<LeerServicios, List<responseReporteServicios>>
    {
        private readonly IMongoCollection<MongoPedido> pedidos;
        public LeerServiciosHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            pedidos = db.GetCollection<MongoPedido>(settings.Collections[2]);
        }
        List<List<ServiciosModelCrear>> ls = new List<List<ServiciosModelCrear>>();
        public async Task<List<responseReporteServicios>> Handle(LeerServicios request, CancellationToken cancellationToken)
        {
            var res = await pedidos.FindAsync<MongoPedido>(con => true);
            var conver = res.ToList().Select(ped =>
                       new ServiciosModel()
                       {
                           serviciosProcesado = ped.servicios
                       });

           var algo = procesarLista(conver.ToList());
           procesarListaProcesada(algo);
           var reporte = sacarReporte(ls);

            return reporte;
        }
        public List<ServiciosModelCrear> procesarLista(List<ServiciosModel> lista)
        {
            List<ServiciosModelCrear> l = new List<ServiciosModelCrear>();
            foreach (var item in lista)
            {
                foreach (var servicio in item.serviciosProcesado)
                {
                    l.Add(servicio);
                }
            }
            
            return l;
        }
        public void procesarListaProcesada(List<ServiciosModelCrear> lista)
        {
            var l = new List<ServiciosModelCrear>();
            var ids = new List<int>();
            foreach (var item in lista)
            {
               if(!ids.Contains(item.folio))
                {
                    ids.Add(item.folio);
                }
            }
            for (int i = 0; i < ids.Count; i++)
            {
                
                foreach(var item in lista)
                {
                    ServiciosModelCrear model = item;
                    if(model.folio == ids[i])
                    { l.Add(model);}

                }
                ls.Add(new List<ServiciosModelCrear>(l));
                l.Clear();
            }
            
        }
        public List<responseReporteServicios> sacarReporte(List<List<ServiciosModelCrear>> ls)
        {
            var list = new List<responseReporteServicios>();
            float costo = 0;
            int cantidad = 0;
            string nombre = "";
            int folio = 0;
            foreach (var item in ls)
            {
                cantidad = item.Count;
                foreach (var item2 in item)
                {
                    costo = costo + item2.costo;
                    folio = item2.folio;
                    nombre = item2.nombre;
                }
                list.Add(new responseReporteServicios()
                {
                    cantidad = cantidad,
                    costo = costo,
                    folio = folio,
                    nombre = nombre

                });

             costo = 0;
            }
            return list;
        }
        
    }

    public class ServiciosModel
    {
        public List<ServiciosModelCrear> serviciosProcesado{ get; set; }
    }
   
    public class responseReporteServicios
    {
        public int folio { get; set; }
        public float costo { get; set; }
        public int cantidad { get; set; }
        public string nombre { get; set; }
    }
}
