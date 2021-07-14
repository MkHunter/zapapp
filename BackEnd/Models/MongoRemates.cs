using ApiZapapp.Domain;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ApiZapapp.Models
{
    public class MongoRemates
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id { get; set; }
         public int idProducto { get; set; }
        public string marca { get; set; }
        public string color { get; set; }
        public string imagen { get; set; }
        public int precio { get; set; }
        public float talla { get; set; }
    }
}
