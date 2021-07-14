using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ApiZapapp.Models
{
    public class MongoCliente
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id { get; set; }
        public int numeroCliente { get; set; }
        public string nombre { get; set; }
        public string numeroCelular { get; set; }
        public string apellidos { get; set; }
        public string correo { get; set; }
    }
}
