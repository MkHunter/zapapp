using ApiZapapp.Domain.Credenciales;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ApiZapapp.Responses
{
    public class ResponseCredenciales
    {
        
        public UsuarioLeercred usuario { get; set; }
        public string mensaje { get; set; }
        public bool status { get; set; }
        public string token { get; set; }
        public int disponible { get; set; }
    }
}