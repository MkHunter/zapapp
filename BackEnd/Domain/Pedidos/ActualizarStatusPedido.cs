using ApiZapapp.DBConfiguration;
using ApiZapapp.Models;
using MediatR;
using MongoDB.Driver;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace ApiZapapp.Domain.Pedidos
{
    public class ActualizarStatusPedido : IRequest<bool>
    {
        public CambiarEstadoModel _act;
        public ActualizarStatusPedido(CambiarEstadoModel act)
        {
            this._act = act;
        }
    }
    public class ActualizarStatusPedidoHandler : IRequestHandler<ActualizarStatusPedido, bool>
    {
        private readonly IMongoCollection<MongoPedido> pedidos;
        public ActualizarStatusPedidoHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            pedidos = db.GetCollection<MongoPedido>(settings.Collections[2]);
        }

        public async Task<bool> Handle(ActualizarStatusPedido request, CancellationToken cancellationToken)
        {
            var filter = Builders<MongoPedido>.Filter.Eq("folioPedido", request._act.folioPedido);
            var res = (await pedidos.FindAsync<MongoPedido>(filter)).FirstOrDefault();

            var restante = res.monto - res.abonado;

            res.estadoPedido = request._act.estado;
            if(request._act.estado == 4)
            {
                res.abonado = res.abonado + restante;
            }
            pedidos.ReplaceOne(filter, res);
            await NotifyAsync1();
            return true;

        }
        public async Task<bool> NotifyAsync1()
        {
            try
            {
                var title = "Este es un titulo";
                var body = "Este es el mensaje";
                // Get the server key from FCM console
                var serverKey = string.Format("key={0}", "AAAAJ7P_ZGY:APA91bGZ_Fi9GPZmiTbK6-vAnL6SgwMQMn3_WXW8MUvfBVphytPwJJhSd_Sj9qgUYEK2pEGNKnPa5hDAOacMM4f0OgUNo11fjNhOEkTivZThSfduV2wFoUdMsnoV-cRzIzQgiODgzQJC");

                // Get the sender id from FCM console
                var senderId = string.Format("id={0}", "170523583590");

                var data = new
                {
                    to = "fnkETUMlQXODqNfgmDJeVc:APA91bGrUSfN36VzWzARVTyoUHJp3wLTYf-_Jr4wA9TJIB50sPLUt2ML1Dutn1szwpGCEsLv9QWCQ7IluP0Y6P4-lHE32Be2bsKu3cMgJ0dfIfsPuLpnm89GgVdK-oCGn00GpX4KHrzU", // Recipient device token
                    notification = new { title, body }
                };

                // Using Newtonsoft.Json
                var jsonBody = JsonConvert.SerializeObject(data);

                using (var httpRequest = new HttpRequestMessage(HttpMethod.Post, "https://fcm.googleapis.com/fcm/send"))
                {
                    httpRequest.Headers.TryAddWithoutValidation("Authorization", serverKey);
                    httpRequest.Headers.TryAddWithoutValidation("Sender", senderId);
                    httpRequest.Content = new StringContent(jsonBody, Encoding.UTF8, "application/json");

                    using (var httpClient = new HttpClient())
                    {
                        var result = await httpClient.SendAsync(httpRequest);

                        if (result.IsSuccessStatusCode)
                        {
                            return true;
                        }
                        else
                        {
                            // Use result.StatusCode to handle failure
                            // Your custom error handler here

                        }
                    }
                }
            }
            catch (Exception ex)
            {


            }

            return false;
        }
    }
    public class CambiarEstadoModel
    {
        public int folioPedido { get; set; }
        public int estado { get; set; }

    }
}
