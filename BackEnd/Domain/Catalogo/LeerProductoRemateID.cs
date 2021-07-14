using ApiZapapp.DBConfiguration;
using ApiZapapp.Models;
using MediatR;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace ApiZapapp.Domain.Catalogo
{
    public class LeerProductoRemateID : IRequest<LeerCatalogoIDModel>
    {
        public int _id;

        public LeerProductoRemateID(int id)
        {
            this._id = id; 
        }
    }
    public class LeerProductoRemateIDHandler : IRequestHandler<LeerProductoRemateID, LeerCatalogoIDModel>
    {
        private readonly IMongoCollection<MongoRemates> productos;

        public LeerProductoRemateIDHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            productos = db.GetCollection<MongoRemates>(settings.Collections[4]);
        }

        public async Task<LeerCatalogoIDModel> Handle(LeerProductoRemateID request, CancellationToken cancellationToken)
        {
            var filter = Builders<MongoRemates>.Filter.Eq("idProducto", request._id);
            var res = (await productos.FindAsync<MongoRemates>(filter)).FirstOrDefault();


            return new LeerCatalogoIDModel()
            {
                idProducto = res.idProducto,
                marca = res.marca,
                color = res.color,
                imagen = res.imagen,
                precio = res.precio,
                talla = res.talla

            };
        }
    }

    public class LeerCatalogoIDModel
    {
        public int idProducto { get; set; }
        public string marca { get; set; }
        public string color { get; set; }
        public string imagen { get; set; }
        public int precio { get; set; }
        public float talla { get; set; }
    }
}
