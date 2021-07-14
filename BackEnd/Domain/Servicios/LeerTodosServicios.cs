using ApiZapapp.DBConfiguration;
using ApiZapapp.Models;
using MediatR;
using MongoDB.Driver;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace ApiZapapp.Domain.Servicios
{
    public class LeerTodosServicios : IRequest<List<servicioLeerModel>>
    {

    }
    public class LeerTodosServiciosHandler : IRequestHandler<LeerTodosServicios, List<servicioLeerModel>>
    {
        private readonly IMongoCollection<MongoServicio> servicios;

        public LeerTodosServiciosHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);

            servicios = db.GetCollection<MongoServicio>(settings.Collections[1]);
        }

        public async Task<List<servicioLeerModel>> Handle(LeerTodosServicios request, CancellationToken cancellationToken)
        {

            var resultado = await servicios.FindAsync<MongoServicio>(serv => true);
            var res = resultado.ToList().Select(serv =>
                new servicioLeerModel()
                {
                    folioServicio = serv.folioServicio,
                    nombreServicio = serv.nombreServicio,
                    rangoMax = serv.rangoMax,
                    rangoMin = serv.rangoMin
                });

            return res.ToList();
        }
    }
    public class servicioLeerModel
    {
        public int folioServicio { get; set; }
        public string nombreServicio { get; set; }
        public float rangoMin { get; set; }
        public float rangoMax { get; set; }
    }
}
