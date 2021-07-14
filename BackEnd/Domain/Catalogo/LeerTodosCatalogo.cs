using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using ApiZapapp.DBConfiguration;
using ApiZapapp.Models;
using MediatR;
using MongoDB.Driver;

namespace ApiZapapp.Domain.Catalogo
{
    public class LeerTodosCatalogo : IRequest<List<LeerCatalogoModel>>
    {
        public LeerTodosCatalogo()
        {

        }
    }

    public class LeerTodosCatalogoHandler : IRequestHandler<LeerTodosCatalogo, List<LeerCatalogoModel>>
    {
        private readonly IMongoCollection<MongoRemates> productos;
        public LeerTodosCatalogoHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            productos = db.GetCollection<MongoRemates>(settings.Collections[4]);
        }

        public async Task<List<LeerCatalogoModel>> Handle(LeerTodosCatalogo request, CancellationToken cancellationToken)
        {

            var resultado = await productos.FindAsync<MongoRemates>(pro => true);
            var res = resultado.ToList().Select(pro => 
            new LeerCatalogoModel()
            {
                idProducto = pro.idProducto,
                color = pro.color,
                imagen = pro.imagen,
                marca = pro.marca,
                precio = pro.precio,
                talla = pro.talla    
            });
            return res.ToList();
        }
    }
    public class LeerCatalogoModel
    {
        public int idProducto { get; set; }
        public string marca { get; set; }
        public string color { get; set; }
        public string imagen { get; set; }
        public int precio { get; set; }
        public float talla { get; set; }

    }
}