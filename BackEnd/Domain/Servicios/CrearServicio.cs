using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using ApiZapapp.DBConfiguration;
using ApiZapapp.Domain.Servicios;
using ApiZapapp.Models;
using MediatR;
using Microsoft.AspNetCore.Mvc;
using MongoDB.Bson;
using MongoDB.Driver;


namespace ApiZapapp.Domain
{
    public class CrearServicio : IRequest<bool>
    {
        public CrearServicio(CrearServicioModel servicio)
        {
            this.servicio = servicio;
        }
        public CrearServicioModel servicio { get; set; }
    }

    public class CrearServicioHandler : IRequestHandler<CrearServicio, bool>
    {
        private readonly IMongoCollection<MongoServicio> servicios;

        public CrearServicioHandler(IZapatosDatabaseSettings settings)
        {

            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            servicios = db.GetCollection<MongoServicio>(settings.Collections[1]);
        }

        public async Task<bool> Handle(CrearServicio request, CancellationToken cancellationToken)
        {
            var folioId = 0;
            var sort = Builders<MongoServicio>.Sort.Descending("folioServicio");

            var resultado = await servicios.Find(FilterDefinition<MongoServicio>.Empty).Sort(sort).FirstOrDefaultAsync();

            if (resultado is null)
            {
                folioId = 1;
            }
            else
            {
                folioId = resultado.folioServicio + 1;
            }

            await servicios.InsertOneAsync(new MongoServicio()
            {
                folioServicio = folioId,
                nombreServicio = request.servicio.nombreServicio,
                rangoMax = request.servicio.rangoMax,
                rangoMin = request.servicio.rangoMin
            });
            return true;
        }
    }

    public class CrearServicioModel
    {
        public string nombreServicio { get; set; }
        public float rangoMin { get; set; }
        public float rangoMax { get; set; }
    }

}
