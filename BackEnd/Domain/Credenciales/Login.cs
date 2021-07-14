using ApiZapapp.DBConfiguration;
using ApiZapapp.Models;
using ApiZapapp.Responses;
using MediatR;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace ApiZapapp.Domain.Credenciales
{
    public class Login : IRequest<ResponseCredenciales>
    {
        
        public CredencialesModel credenciales;
        public Login(CredencialesModel credencial)
        {
        
            this.credenciales = credencial;
        }
    }
    public class LoginHandler : IRequestHandler<Login, ResponseCredenciales>
    {
        private readonly IMongoCollection<MongoUsuario> usuario;

        public LoginHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            usuario = db.GetCollection<MongoUsuario>(settings.Collections[0]);
        }

        public async Task<ResponseCredenciales> Handle(Login request, CancellationToken cancellationToken)
        {
        
            var val = await usuario.FindAsync<MongoUsuario>(cred => cred.nombreUsuario == request.credenciales.usuario);
        
            var credencial = val.FirstOrDefault();

            if (!(credencial is null))
            {
                if (credencial.contraseña == request.credenciales.contraseña)
                {
                    if(credencial.estado != 0)
                    {
                        return new ResponseCredenciales()
                        {

                            mensaje = "Acceso correcto",
                            usuario = new UsuarioLeercred()
                            {
                                idUsuario = credencial.idUsuario,
                                nombreUsuario = credencial.nombreUsuario,
                                rol = credencial.rol,
                                entidad = credencial.entidad
                            },
                            status = true
                        };
                    }
                    else
                    {
                        return new ResponseCredenciales()
                        {
                            mensaje = "El usuario no esta verificado",
                            disponible = 0,
                            usuario = null,                    
                            status = false
                        };
                    }
                }
                else
                {
                    return new ResponseCredenciales()
                    {
                        usuario = null,
                        mensaje = "Acceso Denegado",                   
                        status = false
                    };
                }
            }
            else
            {
                return new ResponseCredenciales()
                {
                    mensaje = "Acceso Denegado",
                    status = false
                };
            }
            
        }
    }
    public class CredencialesModel
    {
        public string usuario { get; set; }
        public string contraseña { get; set; }
        
    }
    public class UsuarioLeercred
    {
        public int idUsuario { get; set; }
        public string nombreUsuario { get; set; }
        public int rol { get; set; }
        public Entidad entidad { get; set; }
    }

}
