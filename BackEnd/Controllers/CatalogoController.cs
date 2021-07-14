using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ApiZapapp.Domain;
using ApiZapapp.Models;
//using ApiZapapp.Domain.Clientes;
using MediatR;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using ApiZapapp.Domain.Catalogo;
// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace ApiZapapp.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CatalogoController : ControllerBase
    {
        private readonly IMediator _mediator;
        public CatalogoController(IMediator mediator)
        {
            this._mediator = mediator;
        }
        [Authorize(Roles = "1,2")]
        [HttpGet]
        public Task<List<LeerCatalogoModel>> Get()
        {
            var catalogo = _mediator.Send(new LeerTodosCatalogo());
            return catalogo;
        }
        [Authorize(Roles = "1,2")]
        [HttpGet("id")]
        public async Task<LeerCatalogoIDModel> Get(int id)
        {
            var productoRemate = await _mediator.Send(new LeerProductoRemateID(id));
            return productoRemate;
        }
        [Authorize(Roles = "2")]
        [HttpPost]
        public async Task<bool> Post([FromBody] CrearProductoRemateModel pro)
        {
            await _mediator.Send(new CrearProductoRemate(pro));
            return true;
        }
    }
       // GET: api/<ClientesController>
}
