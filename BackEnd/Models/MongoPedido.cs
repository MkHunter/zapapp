using ApiZapapp.Domain.Pedidos;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ApiZapapp.Models
{
    public class MongoPedido
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id { get; set; }
        public int usuario { get; set; }
        public int folioPedido { get; set; }
        public string descripcion { get; set; }
        public string fechaPedido { get; set; }
        public string fechaEstimada { get; set; }
        public int cliente { get; set; }
        public int estadoPedido { get; set; }
        public float abonado { get; set; }
        public float monto { get; set; }

        public List<ServiciosModelCrear> servicios;

       
    }
}
