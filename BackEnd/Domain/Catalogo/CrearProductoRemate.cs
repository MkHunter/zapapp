using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using ApiZapapp.DBConfiguration;
using ApiZapapp.Models;
using Firebase.Auth;
using Firebase.Storage;
using MediatR;
using MongoDB.Driver;

namespace ApiZapapp.Domain.Catalogo
{
    public class CrearProductoRemate : IRequest<bool>
    {
        public CrearProductoRemateModel _producto;

        public CrearProductoRemate(CrearProductoRemateModel producto)
        {
            this._producto = producto;
        }
    }

    public class CrearProductoRemateHandler : IRequestHandler<CrearProductoRemate, bool>
    {
        private readonly IMongoCollection<MongoRemates> productos;
        public CrearProductoRemateHandler(IZapatosDatabaseSettings settings)
        {
            var cliente = new MongoClient(settings.ConnectionString);
            var db = cliente.GetDatabase(settings.DatabaseName);
            productos = db.GetCollection<MongoRemates>(settings.Collections[4]);
        }

        public async Task<bool> Handle(CrearProductoRemate request, CancellationToken cancellationToken)
        {
            var p = request._producto;
            var folioId = 0;
            var sort = Builders<MongoRemates>.Sort.Descending("idProducto");
            var resultado = await productos.Find(FilterDefinition<MongoRemates>.Empty).Sort(sort).FirstOrDefaultAsync();
        

            
            if (resultado is null)
            {
                folioId = 1;
            }
            else
            {
                folioId = resultado.idProducto + 1;
            }

            var nombre = folioId + "_" + p.marca + "_" + DateTime.Now.TimeOfDay;
            var decode = DecodeImage64(p.imagen);
            var UrImage = await guardarFire(decode, nombre);


            await productos.InsertOneAsync( new MongoRemates()
            {
                idProducto = folioId,
                color = p.color,
                imagen = UrImage,
                marca = p.marca,
                precio = p.precio,
                talla = p.talla
            });
            return true;
        }

        public byte[] DecodeImage64(string base64Ima)
        {
            var bytess = Convert.FromBase64String(base64Ima);
            return bytess;
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
                .Child("remate")
                .Child(nombre)
                .PutAsync(stream, cancelationToken.Token);
            stream.Flush();
            var downloadUrl = await upload;
            return downloadUrl;
        }
    }
    public class CrearProductoRemateModel
    {
        public string marca { get; set; }
        public string color { get; set; }
        public string imagen { get; set; }
        public int precio { get; set; }
        public float talla { get; set; }

    }
}