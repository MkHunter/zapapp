using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ApiZapapp.Domain;
using ApiZapapp.Domain.Servicios;
using ApiZapapp.Models;
using MediatR;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using MongoDB.Driver;

namespace ApiZapapp.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ServiciosController : ControllerBase
    {
        public IMediator mediator;
        
        public ServiciosController(IMediator mediator)
        {
            this.mediator = mediator;
        }

        // GET: api/<ServiciosController>
        [HttpGet]
        public async Task<ActionResult<List<servicioLeerModel>>> Get()
        {
            var modelo = await mediator.Send(new LeerTodosServicios());

            return modelo;
        }

        // GET api/<ServiciosController>/5

        [HttpGet("{id}")]
        public string Get(int id)
        {
            return "value";
        }

        // POST api/<ServiciosController>
        [HttpPost]
        public async Task<bool> Post(CrearServicioModel servicio)
        {
            await mediator.Send(new CrearServicio(servicio));
            return true;
        }
    }
}
