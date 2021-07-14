using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ApiZapapp.Domain.Conversaciones;
using MediatR;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace ApiZapapp.Controllers
{

    [Route("api/[controller]")]
    [ApiController]
    public class ConversacionesController : ControllerBase
    {
        private readonly IMediator mediator;

        public ConversacionesController(IMediator mediator)
        {
            this.mediator = mediator;
        }
        [Authorize(Roles = "2")]
        [HttpGet]
        public async Task<List<LeerConversacionModel>> Get()
        {
            var hilos = await mediator.Send(new LeerTodasConversaciones());
            return hilos;
        }
        [Authorize]
        [HttpGet("{id}")]
        public async Task<DetalleConversacionModel> GetId(int id)
        {
            var hilo = await mediator.Send(new LeerHiloId(id));
            return hilo;
        }

        [Authorize]
        [HttpGet("estado")]
        public async Task<List<LeerConversacionModel>> GetFiltro(int estado)
        {
            var hilos = await mediator.Send(new LeerHilosFiltro(new FiltroHilo()
            {
                nombreFiltro = "usuarioPost",
                //valorFiltro = User.FindFirst("id").Value,
                valorFiltro = User.Claims.First(func => func.Type == "id").Value,
                estado = estado
            }));
            return hilos;
        }
        [Authorize(Roles = "1")]
        [HttpPost]
        public async Task<CrearConversacionModel> Post(CrearConversacionModel hilo)
        {
            hilo.usuarioPost = int.Parse(User.Claims.First(func => func.Type == "id").Value);
            if (hilo.imagenPostPrincipal is null)
            {
                BadRequest();
            }
            var decode = await mediator.Send(new PostHilo(hilo));
            return decode;
        }

        [Authorize]
        [HttpPut]
        public async Task<EditarConversacionModel> Put(EditarConversacionModel Hilo)
        {
           Hilo.usuario = int.Parse(User.Claims.First(func => func.Type == "id").Value);
           var con = await mediator.Send(new ActualizarHilo(Hilo));
           
            return con;
        }
        [Authorize]
        [HttpPut("actualizarEstado")]
        public async Task<bool> Put(EditarEstadoModel editarEstadoModel)
        {
            var resp = await mediator.Send(new ActualizarEstadoHilo(editarEstadoModel));
            return resp;
        }
    }
}
