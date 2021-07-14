using ApiZapapp.Domain.Conversaciones;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ApiZapapp.Models
{
    public class MongoConversacion
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id { get; set; }
        public int idHilo { get; set; }
        public int usuarioPost { get; set; }
        public string fechaPost { get; set; }
        public int estado { get; set; }
        public string postPrincipal { get; set; }
        public List<string> imagenPostPrincipal { get; set; }
        public List<Conversaciones> conversaciones { get; set; }

    }

}