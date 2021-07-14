using ApiZapapp.DBConfiguration;
using ApiZapapp.Models;
using MediatR;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using MongoDB.Driver;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Firebase.Auth;
using Firebase.Storage;

namespace ApiZapapp.Domain.Conversaciones
{
    public class PostHilo : IRequest<CrearConversacionModel>
    {
        public PostHilo(CrearConversacionModel conversacion)
        {
            this.corvesacionModel = conversacion;
            this.corvesacionModel.fechaPost = DateTime.Now.ToString("dd/MM/yyyy HH:mm:ss");

        }

        public CrearConversacionModel corvesacionModel { get; set; }
    }
    public class PostHiloHandler : IRequestHandler<PostHilo, CrearConversacionModel>
    {
        private IMongoCollection<MongoConversacion> postHilos;
       
        public PostHiloHandler(IZapatosDatabaseSettings settings)
        {
            
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            postHilos = db.GetCollection<MongoConversacion>(settings.Collections[3]);
        }

        public async Task<CrearConversacionModel> Handle(PostHilo request, CancellationToken cancellationToken)
        {
            //parametros para la consulta de la base de datos en mongo
            var sort = Builders<MongoConversacion>.Sort.Descending("idHilo");
            var res = await postHilos.Find(FilterDefinition<MongoConversacion>.Empty).Sort(sort).FirstOrDefaultAsync();
            var idHilo = 0;
            List<string> listaUr = new List<string>();
            //Parametros para guardar imagenes en firebase
            //if (request.corvesacionModel.imagenPostPrincipal.Count  < 1)
            //{
            var nombre = "_" + request.corvesacionModel.usuarioPost.ToString() + "_" + DateTime.Now.TimeOfDay;
            var decode = DecodeImage64(request.corvesacionModel.imagenPostPrincipal[0]);
            var UrImage = await guardarFire(decode, nombre);

            listaUr.Add(UrImage);
            //}
            //else
            //{
            //listaUr.Add("sin Foto");
            //}
            if (res is null)
            {
                idHilo = 1;
            }
            else
            {
                idHilo = res.idHilo + 1;
            }

            //sentencia de insercion en base de datos de mongo
            var con = new MongoConversacion()
            {
                idHilo = idHilo,
                estado = request.corvesacionModel.estado,
                fechaPost = DateTime.Now.ToString("dd/MM/yyyy HH:mm:ss"),//request.corvesacionModel.fechaPost,
                postPrincipal = request.corvesacionModel.postPrincipal,
                imagenPostPrincipal = listaUr,
                usuarioPost = request.corvesacionModel.usuarioPost,
                conversaciones = new List<Conversaciones>()
            };

            await postHilos.InsertOneAsync(con);

            var re = request.corvesacionModel;
            re.imagenPostPrincipal = null;
            re.idHilo = idHilo;

            return re;

        }
        public byte[] DecodeImage64(string base64Ima)
        {
            var bytess = Convert.FromBase64String(base64Ima);
            return bytess;
        }
        public void obtenerImagenes(string idCarpeta)
        {

        }
        public async Task<string> guardarFire(byte[] array, string nombre)
        {
            string apiKey = "AIzaSyDTQEsU4rDLSdXtnMcSe_4NBaW2Fc66H24";
            string Bucket = "zappap-dba32.appspot.com";
            string AuthEmail = "betilloloera@gmail.com";
            string AuthPass = "Betito17@";

            Stream stream = new MemoryStream(array);

            //Firebase uploading stuffs
            var auth = new FirebaseAuthProvider(new FirebaseConfig(apiKey));
            var a = await auth.SignInWithEmailAndPasswordAsync(AuthEmail, AuthPass);

            //cancelation Token
            var cancelationToken = new CancellationTokenSource();

            var upload = new FirebaseStorage(
                Bucket, new FirebaseStorageOptions
                {
                    AuthTokenAsyncFactory = () => Task.FromResult(a.FirebaseToken),
                    ThrowOnCancel = true
                }
                ).Child("assets")
                .Child("images")
                .Child(nombre)
                .PutAsync(stream, cancelationToken.Token);
            stream.Flush();
            var downloadUrl = await upload;
            return downloadUrl;

        }
    }
    public class CrearConversacionModel
    {
        public int idHilo { get; set; }
        public int usuarioPost { get; set; }
        public string fechaPost { get; set; }
        public int estado { get; set; }
        public List<string> imagenPostPrincipal { get; set; }
        public string postPrincipal { get; set; }
    }
    public class Conversaciones
    {
        public int idConversacion { get; set; }
        public string mensaje { get; set; }
        public int usuario { get; set; }
        public string fechaConversacion { get; set; }
    }
}