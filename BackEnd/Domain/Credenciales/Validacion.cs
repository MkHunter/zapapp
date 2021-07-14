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
    public class Validacion : IRequest<ResponseValidacion>
    {
        public ValidacionModel _validacion { get; set; }
        public Validacion(ValidacionModel validacion)
        {
            this._validacion = validacion;
        }
    }
    public class ValidacionHandler : IRequestHandler<Validacion, ResponseValidacion>
    {
        private readonly IMongoCollection<MongoUsuario> usuario;
        public ValidacionHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            usuario = db.GetCollection<MongoUsuario>(settings.Collections[0]);
        }

        public async Task<ResponseValidacion> Handle(Validacion request, CancellationToken cancellationToken)
        {
            var val = await usuario.FindAsync<MongoUsuario>(val => val.idUsuario == request._validacion.idUsuario);
            var credencial = val.FirstOrDefault();
            if(credencial != null)
            {
               if(credencial.codigoValidacion == request._validacion.codigo)
                {
                    credencial.estado = 1;
                    credencial.codigoValidacion = " ";
                    await usuario.ReplaceOneAsync(val => val.idUsuario == request._validacion.idUsuario, credencial);
                    return new ResponseValidacion()
                    {
                        mensaje = "Verifiacion realizada correctamente",
                        estatus = true
                    };
                }
               else
                {
                    return new ResponseValidacion()
                    {
                        mensaje = "El codigo de verificacion es incorrecto",
                        estatus = false
                    };
                }
            }
            throw new NotImplementedException();
        }
    }
    public class ValidacionModel
    {
        public string codigo{ get; set; }
        public int idUsuario { get; set; }
    }
    public class ResponseValidacion
    {
        public string mensaje { get; set; }
        public bool estatus{ get; set; }
    }
}
