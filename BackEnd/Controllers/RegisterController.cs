using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ApiZapapp.Domain.Credenciales;
using MediatR;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace ApiZapapp.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class RegisterController : ControllerBase
    {
        private readonly IMediator mediator;

        public RegisterController(IMediator mediator)
        {
            this.mediator = mediator;
        }

        [HttpPost]
        public async Task<ResponseRegistro> post(UsuarioSistemaCrearModel registroForm)
        {
            var response = await mediator.Send(new Registro(registroForm));
            return response;
        }

        [HttpPut]
        public async Task<ResponseValidacion> put(ValidacionModel registroForm)
        {
            var response = await mediator.Send(new Validacion(registroForm));
            return response;
        }
    }
}
