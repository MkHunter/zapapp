# Zapapp Frontend con React ⚛️

## Dependencias
### Ⓜ[aterial UI](https://material-ui.com/getting-started/usage/) + 📝 [Formik](https://stackworx.github.io/formik-material-ui/docs/guide/getting-started/)
- @material-ui/core @material-ui/icons
- formik-material-ui @material-ui/core
- formik-material-ui-lab @material-ui/lab
- formik-material-ui-pickers @date-io/date-fns@1.x date-fns
  
### ⚛️ [React](https://reactjs.org/docs/getting-started.html) + 🎣 [Hooks](https://reactjs.org/docs/hooks-intro.html) + 🌀 [Redux]([https://link](https://redux.js.org/introduction/getting-started))
- react-router-dom @types/react-router-dom
- redux-thunk react-redux
- redux-persist
  
### [Otras](https://www.npmjs.com/package/axios)
- axios

## Vistas
### Login
![alt text](https://i.ibb.co/LYLwM7n/1login.png "Login")
### Perfil de Usuario
![alt text](https://i.ibb.co/zbWSK1G/6profile.png "Perfil")
### Pedidos
![alt text](https://i.ibb.co/G7R5D3f/2pedido.png "Registrar Pedidos")
### Catálogo de Remate
![alt text](https://i.ibb.co/7vS7CRT/3catalogo.png "Catálogo")
### Conversaciones
![alt text](https://i.ibb.co/Wf4Q0kk/5conversa.png "Responder Conversaciones")
### Reportes
![alt text](https://i.ibb.co/558ZTVT/4reportes.png "Reportes")

## Available Scripts

### `npm start`
### `npm test`
### `npm run build`

## FileSystem
```
FrontEnd
├─ public
└─ src
   ├─ index.css
   ├─ index.tsx
   ├─ theme.ts
   ├─ helpers
   │  └─ AppTypes.d.ts
   ├─ actions
   │  ├─ api.ts
   │  ├─ dCatalogo.ts
   │  ├─ dClientes.ts
   │  ├─ dConversaciones.ts
   │  ├─ dPedidos.ts
   │  ├─ dReportes.ts
   │  ├─ dServicios.ts
   │  ├─ dUsuario.ts
   │  └─ store.ts
   ├─ reducers
   |  ├─ dCatalogo.ts
   │  ├─ dClientes.ts
   |  ├─ dConversaciones.ts
   |  ├─ dPedidos.ts
   |  ├─ dReportes.ts
   |  ├─ dServicios.ts
   |  ├─ dUsuario.ts
   |  └─ index.ts
   └─ components
      ├─ App.css
      ├─ App.tsx
      ├─ shared
      ├─ test 
      └─ layouts
         ├─ Auth
         │  ├─ Index.tsx
         │  ├─ LoginForm.tsx
         │  └─ RegisterForm.tsx
         ├─ Catalogo
         │  ├─ Crear.tsx
         │  ├─ Editar.tsx
         │  └─ index.tsx
         ├─ Clientes
         │  ├─ Crear.tsx
         │  ├─ Editar.tsx
         │  └─ index.tsx
         ├─ Conversaciones
         │  ├─ Editar.tsx
         │  └─ index.tsx
         ├─ Home
         │  ├─ index.tsx
         │  └─ Perfil.tsx
         ├─ Pedidos
         │  ├─ Crear.tsx
         │  ├─ Detalle.tsx
         │  ├─ EditarEstado.tsx
         │  ├─ EnhancedTable.tsx
         │  ├─ InputSlider.tsx
         │  └─ index.tsx
         ├─ Reportes
         │  ├─ CustomBarChart.tsx
         │  ├─ CustomPieChart.tsx
         │  ├─ TopAbonosClientes.tsx
         │  └─ index.tsx
         └─ Servicios
            ├─ Crear.tsx
            └─ index.tsx
```