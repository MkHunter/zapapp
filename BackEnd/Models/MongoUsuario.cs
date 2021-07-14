using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ApiZapapp.Models
{
    public class MongoUsuario
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id { get; set; }
        public int idUsuario { get; set; }
        public string nombreUsuario { get; set; }
        public int rol { get; set; }
        public int estado { get; set; }
        public string contraseña{ get; set; }
        public string codigoValidacion { get; set; }
        public Entidad entidad{ get; set; }
        
    }
    public class Entidad
    {
        public int identificador { get; set; }
        public string nombre { get; set; }
        public string apellido { get; set; }
        public string telefono { get; set; }
        public string correo { get; set; }
        public int disponible { get; set; }
    }



}
