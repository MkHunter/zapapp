using ApiZapapp.DBConfiguration;
using ApiZapapp.Models;
using MediatR;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace ApiZapapp.Domain.Credenciales
{
    public class CambioContraseña : IRequest<bool>
    {
        public CambioContraseñaModel contraseña { get; set; }

        public CambioContraseña(CambioContraseñaModel contraseña)
        {
            this.contraseña = contraseña;
        }
    }
    public class CambioContraseñaHandler : IRequestHandler<CambioContraseña, bool>
    {
        private readonly IMongoCollection<MongoUsuario> Usuarios;

        public CambioContraseñaHandler(IZapatosDatabaseSettings settings, IMediator mediator)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            Usuarios = db.GetCollection<MongoUsuario>(settings.Collections[0]);
        }

        public async Task<bool> Handle(CambioContraseña request, CancellationToken cancellationToken)
        {
            
            var req = request.contraseña;
            var filter3 = Builders<MongoUsuario>.Filter.Eq("nombreUsuario", req.nombreUsuario);
            var usuarioTask = (await Usuarios.FindAsync<MongoUsuario>(filter3)).FirstOrDefault();
            if(usuarioTask != null)
            {
                return false;
            }
            else
            {
                usuarioTask.contraseña = request.contraseña.contraseña;
                await Usuarios.ReplaceOneAsync(filter3, usuarioTask);
                return true;
            }
        }
    }
    public class CambioContraseñaModel
    {
     public string nombreUsuario{ get; set; }
     public string contraseña { get; set; } 
    }
}
