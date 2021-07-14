# Seccion Back

## Ultimos Cambios

**1. Añadido Dominio/ActualizarHilo - Añadir Conversacion al arreglo**

**2. Actualizado Dominio/PostHilo Generacion idHilo automatico**

**3. Añadida Accion PUT al controlador Conversaciones**

**4. Añadido Accion GetId al controlador conversaciones**

**5. Añadido Accion Get al controlador conversaciones**

**6. Añadido Dominio/LeerHiloFiltro Leer Hilo con filtro dinamico**

**7. Añadido Accion Get con funcion de filtro al controlador conversaciones**

**8. Añadido Dominio/LeerHiloFiltro Leer Hilo con id**

**9. Actualizado Dominio/LeerHiloFiltro Añadida la opcion Leer por estado**


------------

## Pendientes

- [X] **1. Añadir Action GET en Controlador de conversaciones**
- [X] **2. Añadir Action GET con filtro de usuario en Controlador de conversaciones**
- [ ] **3. Modificar Response de Controlador de pedido y conversaciones**
- [ ] **4. Modificar las Claims del JWT en controlador de Login**
- [ ] **5. Modificar Autorizacion por accion con IdentityFramework**

------------



# Directorio
```
+---.config
|       dotnet-tools.json

+---Controllers
|       ClientesController.cs
|       ConversacionesController.cs
|       LoginController.cs
|       PedidosController.cs
|       ServiciosController.cs
|       WeatherForecastController.cs
|
+---DBConfiguration
|       ZapatosDatabaseSettings.cs
|
+---Documentacion
|       README.md
|
+---Domain
|   +---Clientes
|   |       CrearCliente.cs
|   |       LeerTodosClientes.cs
|   |
|   +---Conversaciones
|   |       ActualizarHilo.cs
|   |       PostHilo.cs
|   |
|   +---Credenciales
|   |       Login.cs
|   |       Registro.cs
|   |       ResponseCredenciales.cs
|   |       ResponseRegistro.cs
|   |
|   +---Pedidos
|   |       CrearPedido.cs
|   |       LeerPedidosFiltro.cs
|   |
|   \---Servicios
|           CrearServicio.cs
|           LeerTodosServicios.cs
|
+---Models
|       ClaimModel.cs
|       IResponse.cs
|       MongoCliente.cs
|       MongoConversacion.cs
|       MongoCredenciales.cs
|       MongoPedido.cs
|       MongoServicio.cs
|
\---Properties
        launchSettings.json
```


