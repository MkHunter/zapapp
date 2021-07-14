using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ApiZapapp.Domain.Pedidos;

using MediatR;
using Microsoft.AspNetCore.Authorization;

using Microsoft.AspNetCore.Mvc;


// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace ApiZapapp.Controllers
{
    
    [Route("api/[controller]/")]
    [ApiController]
    public class PedidosController : ControllerBase
    {
        private readonly IMediator mediator;

        public PedidosController(IMediator mediator)
        {
            this.mediator = mediator;
        }

        // GET: api/<PedidosController>
        [HttpGet]
        public async Task<ActionResult<List<LeerTodosPedidosModel>>> Get()
        {
            var pedidos = await mediator.Send(new LeerTodosPedidos());
            return pedidos;
        }


        // GET api/<PedidosController>/5
        [Authorize(Roles = "2")]
        [HttpGet("{nombreFiltro},{valorFiltro}")]
        public async Task<List<LeerPedidoFiltroModel>> GetFiltro(string nombreFiltro,string valorFiltro)
        {
            
            var pedidos = await mediator.Send(new LeerPedidosFiltro(new Filtro()
            {
                nombreFiltro =nombreFiltro,
                valorFiltro = valorFiltro
            }));
            return pedidos;
        }
        [Authorize(Roles = "1")]
        [HttpGet("clienteFiltro")]
        public async Task<List<LeerPedidoFiltroModel>> GetFiltro()
        {
            var l = User.Claims.ToList();
            var re = User.Claims.First(func => func.Type == "entidadId").Value;
            if(re != null)
            {
                var pedidos = await mediator.Send(new LeerPedidosFiltro(new Filtro()
                {
                    nombreFiltro = "cliente",
                    valorFiltro = User.Claims.First(func => func.Type == "entidadId").Value
                }));
                return pedidos;
            }
            else
            {
                return  new List<LeerPedidoFiltroModel>();
            }
            
        }
        // POST api/<PedidosController>
        [Authorize(Roles = "2")]
        [HttpPost ]
        public async Task<bool> Post([FromBody] CrearPedidosModel pedido)
        {
            pedido.usuario = int.Parse(User.Claims.First(func => func.Type == "id").Value);
            await mediator.Send(new CrearPedido(pedido));
            return true;
        }
        [Authorize(Roles = "1,2")]
        [HttpGet("{id}")]
        public async Task<LeerPedidosIdModel> GetId(int id)
        {
            var pedido = await mediator.Send(new LeerPedidoId(id));
            return pedido;
        }
        [Authorize(Roles = "2")]
        [HttpPut]
        public async Task<bool> Put([FromBody] CambiarEstadoModel model)
        {
            await mediator.Send(new ActualizarStatusPedido(model));
            return true;
        }
        [Authorize]
        [HttpGet("estadoPedido/{id}")]
        public async Task<LeerEstadoModel> GetEstado(int id)
        {
            var res = await mediator.Send(new LeerEstadoPedido(id));
            return res;
        }
    }
}
