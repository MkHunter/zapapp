interface PedidosType{
    usuario: number;
    folioPedido: number;
    descripcion: string;
    fechaPedido: string;
    fechaEstimada: string;
    cliente: number;
    estadoPedido: number;
    abonado: number;
    monto: number;
}

interface CreatePedidosType {
    usuario: number;
    descripcion: string;
    fechaEstimada: Date;
    cliente: number;
    abonado: number;
    monto: number;
    servicios: ServicioType[];
}

interface ServicioType {
    folio: number;
    nombre: string;
    costo: number;
}

interface ClientesType {
    identificador: number;
    nombre: string;
    telefono: string;
    apellido: string;
    correo: string;
}

interface ServiciosType{
    folioServicio: number;
    nombreServicio: string;
    rangoMin: number;
    rangoMax: number;
}

interface CatalogoType{
    idProducto: number,
    marca: string,
    color: string,
    imagen: string,
    precio: number,
    talla: number
}

interface ConversacionesType {
    idHilo: number;
    usuarioPost: number;
    fechaPost: string;
    estado: number;
    postPrincipal: string;
    imagenPostPrincipal: string;
    horaRequest: string;
    conversaciones: Conversacion[];
  }
  
interface Conversacion {
    idConversacion: number;
    mensaje: string;
    usuario: number;
    fechaConversacion: string;
  }

interface UserDataType{ 
    idUsuario: number;
    nombreUsuario: string;
    rol: number;
    entidad: EntidadType;
  }

interface EntidadType{
    apellido: string;
    correo: string;
    disponible: number;
    identificador: number;
    nombre: string;
    telefono: string;
  }

interface ReporteType {
  folio: number;
  costo: number;
  cantidad: number;
  nombre: string;
}
 
export { PedidosType, 
         CreatePedidosType, 
         ServicioType, 
         ServiciosType, 
         ClientesType, 
         CatalogoType, 
         ConversacionesType,
         Conversacion,
         UserDataType, 
         EntidadType,
         ReporteType};