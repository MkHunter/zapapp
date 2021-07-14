using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ApiZapapp.Domain;
using ApiZapapp.Domain.Clientes;

using ApiZapapp.Domain.Servicios;
//using ApiZapapp.Domain.Clientes;
using MediatR;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace ApiZapapp.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ClientesController : ControllerBase
    {
        private readonly IMediator mediator;
        public ClientesController(IMediator mediator)
        {
            this.mediator = mediator;
        }

        // GET: api/<ClientesController>
        [Authorize(Roles = "2")]
        [HttpGet]
        public async Task<ActionResult<List<EntidadLeerModel>>> Get()
        {
            var modelo = await mediator.Send(new LeerTodosClientes());
            return modelo;
        }

        // GET api/<ClientesController>/5
        [Authorize(Roles = "2")]
        [HttpGet("{identificador}")]
        public async Task<LeerClienteIDModel> Get(int identificador)
        {
            var res = await mediator.Send(new LeerClienteId(identificador));
            return res;
        }

        //POST api/<ClientesController>
        [Authorize(Roles = "2")]
        [HttpPost]
        public async Task<bool>Post([FromBody] UsuarioCrearModel cliente)
        {
           var val = await mediator.Send(new CrearCliente(cliente));
            return val; 
           
        }
        [Authorize(Roles = "1,2")]
        [HttpPut]
        public async Task<bool> Put(EditarClienteModel editar)
        {
            var val = await mediator.Send(new EditarCliente(editar));
            return val;

        }

    }
    public class Response
    {
        public string mensaje { get; set; }
        public bool status { get; set; }

    }
}
