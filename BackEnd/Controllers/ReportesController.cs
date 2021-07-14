using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ApiZapapp.Domain.Reportes;
using MediatR;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace ApiZapapp.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ReportesController : ControllerBase
    {
        // GET: api/<ReportesController>
        private readonly IMediator mediator;
        
        public ReportesController(IMediator mediator)
        {
            this.mediator = mediator;
        }

        [HttpGet]
        public async Task<List<responseReporteServicios>> Get()
        {
            var resp =await  mediator.Send(new LeerServicios());
            return resp;
        }

        // GET api/<ReportesController>/5
        [HttpGet("{id}")]
        public string Get(int id)
        {
            return "value";
        }

        // POST api/<ReportesController>
        [HttpPost]
        public void Post([FromBody] string value)
        {
        }

        // PUT api/<ReportesController>/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] string value)
        {
        }

        // DELETE api/<ReportesController>/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
