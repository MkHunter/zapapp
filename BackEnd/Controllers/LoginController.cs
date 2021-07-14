using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Security.Cryptography.Xml;
using System.Text;
using System.Threading.Tasks;
using ApiZapapp.Domain.Credenciales;
using ApiZapapp.Models;
using ApiZapapp.Responses;
using MediatR;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using RestSharp;
using RestSharp.Authenticators;
using Twilio;
using Twilio.Rest.Api.V2010.Account;

namespace ApiZapapp.Controllers
{

    [Route("api/[controller]")]
    [ApiController]
    public class LoginController : ControllerBase
    {
        private readonly IMediator mediator;
        private readonly IConfiguration configuration;
        public ResponseCredenciales _response;

        public LoginController(IConfiguration configuration, IMediator mediator)
        {

            this.mediator = mediator;
            this.configuration = configuration;
        }
        [HttpPost]
        public async Task<ActionResult<ResponseCredenciales>> Authentication(CredencialesModel credenciales)
        {
            var random = new Random();
            var codigoValidacion = Convert.ToString(random.Next(10000, 99999));
            if (await isValidUserAsync(credenciales))
            {

                _response.token = GenerateToken();
                return _response;
            }
            else
            {
                return _response;
            }
        }

        private async Task<bool> isValidUserAsync(CredencialesModel credenciales)
        {
            var response = await mediator.Send(new Login(credenciales));
            _response = response;
            return response.status;
        }
        private string GenerateToken()
        {
            //HEADERS
            var symetricSecurityKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(this.configuration["Authentication:secretKey"]));
            var signingCredentials = new SigningCredentials(symetricSecurityKey,SecurityAlgorithms.HmacSha256);
            var header = new JwtHeader(signingCredentials);

            //CLAIMS
            var claims = new[]
            {
                new Claim("id",_response.usuario.idUsuario.ToString()),
                new Claim("entidadId",_response.usuario.entidad.identificador.ToString()),
                new Claim("Nombre",_response.usuario.entidad.nombre),
                new Claim("Correo", _response.usuario.entidad.correo),
                new Claim(ClaimTypes.Role , _response.usuario.rol.ToString())
            };

            //PAYLOAD
            var payload = new JwtPayload
            (
                this.configuration["Authentication:Issuer"],
                this.configuration["Authentication:Audience"],
                claims,
                DateTime.Now,
                DateTime.UtcNow.AddDays(1)
            );
            var token = new JwtSecurityToken(header, payload);
            return new JwtSecurityTokenHandler().WriteToken(token);
        }
        [HttpPut("contraseña")]
        public async Task<bool> CambiarContraseña(CambioContraseñaModel contraseña)
        {
            var res = await mediator.Send(new CambioContraseña(contraseña));
            return res;
        }
    }
}
