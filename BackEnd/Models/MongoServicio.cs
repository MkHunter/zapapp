using ApiZapapp.Domain;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ApiZapapp.Models
{
    public class MongoServicio
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id { get; set; }
        public int folioServicio { get; set; }
        public string nombreServicio { get; set; }
        public float rangoMin { get; set; }
        public float rangoMax { get; set; }
    }
}
