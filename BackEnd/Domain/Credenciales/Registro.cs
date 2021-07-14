using ApiZapapp.DBConfiguration;
using ApiZapapp.Models;
using MediatR;
using MongoDB.Driver;
using RestSharp;
using RestSharp.Authenticators;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Twilio;
using Twilio.Rest.Api.V2010.Account;

namespace ApiZapapp.Domain.Credenciales
{
    public class Registro : IRequest<ResponseRegistro>
    {
        public UsuarioSistemaCrearModel usuario { get; set; }

        public Registro(UsuarioSistemaCrearModel registro)
        {
            this.usuario = registro;
        }
    }
    public class RegistroHandler : IRequestHandler<Registro, ResponseRegistro>
    {
        private readonly IMongoCollection<MongoUsuario> Usuarios;
        public RegistroHandler(IZapatosDatabaseSettings settings, IMediator mediator)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            Usuarios = db.GetCollection<MongoUsuario>(settings.Collections[0]);
        }
        public async Task<ResponseRegistro> Handle(Registro request, CancellationToken cancellationToken)
        {
            int id = 0;
            var random = new Random();
            var codigoValidacion = Convert.ToString(random.Next(10000, 99999));
            var req = request.usuario;
            var sort = Builders<MongoUsuario>.Sort.Descending("idUsuario");
            var filter = Builders<MongoUsuario>.Filter.Eq("entidad.telefono", request.usuario.entidad.telefono);
            var filter2 = Builders<MongoUsuario>.Filter.Eq("nombreUsuario", request.usuario.nombreUsuario);
            var filter3 = Builders<MongoUsuario>.Filter.Eq("nombreUsuario", request.usuario.entidad.telefono);
            var usuarioTask = await Usuarios.FindAsync<MongoUsuario>(filter | filter2 | filter3);
            var usuariosLista = usuarioTask.ToList();
            var res = await Usuarios.Find(FilterDefinition<MongoUsuario>.Empty).Sort(sort).FirstOrDefaultAsync();
            if (usuariosLista.Count() > 0)
            {
                return new ResponseRegistro()
                {
                    mensaje ="Error al crear usuario, ya existe un usuario con ese nombre o telefono",
                    status = false
                };
            }
            else
            {
                var dip = 0;
                if (req.rol == 1)
                {
                    req.nombreUsuario = req.entidad.telefono;
                    req.rol = 1;
                    dip = 0;
                    var resp = await mandarMensaje(req.entidad.telefono, codigoValidacion);
                    if (!resp) { return new ResponseRegistro() { 
                    mensaje ="No se pudo enviar el mensaje de confirmacion",
                    status = false
                    };}
                }
                else { dip = 1; codigoValidacion = " "; }
                if (request.usuario.entidad.correo is null)
                {
                    req.entidad.correo =" ";
                }
                if (res is null) {id = 1;}
                else
                {
                    id = res.idUsuario + 1;
                }
                if (req.contraseña is null){req.contraseña = "hola";}
                await Usuarios.InsertOneAsync(new MongoUsuario()
                {
                    idUsuario = id,
                    nombreUsuario = req.nombreUsuario,
                    contraseña = req.contraseña,
                    rol = req.rol,
                    estado = dip,
                    codigoValidacion = codigoValidacion,
                    entidad = new Entidad()
                    {
                        nombre = req.entidad.nombre,
                        apellido = req.entidad.apellido,
                        identificador = id,
                        disponible = 1,
                        correo = req.entidad.correo,
                        telefono = req.entidad.telefono
                    }
                });

                return new ResponseRegistro()
                {
                    mensaje = "Usuario Creado con exito",
                    status = true,
                    idUsuario = id
                };
            }
        }
        public async Task<bool> mandarMensaje(string telefono,string codigoValidacion)
        {
            string accountSid = "AC8007dedc34a90b41f5f15bece7fb932e";
            string authToken = "d577a5d53bc4325fe0dcb8474d3554f8";

            TwilioClient.Init(accountSid, authToken);

            var message =  MessageResource.Create(
                body: "Tu codigo de seguridad es "+codigoValidacion,
                from: new Twilio.Types.PhoneNumber("+16083363376"),
                to: new Twilio.Types.PhoneNumber("+52" + telefono)
            );
            if (message.ErrorCode == null)
            {
                return true;
            }
            else { return false; }  
        }
    }
    public class ResponseRegistro
    {   
        public string mensaje { get; set; }
        public int idUsuario { get; set; }
        public bool status { get; set; }
    }
    public class UsuarioSistemaCrearModel
    {
        public string nombreUsuario { get; set; }
        public int rol { get; set; }
        public string contraseña { get; set; }
        public EntidadCrearModel entidad { get; set; }

    }
}
