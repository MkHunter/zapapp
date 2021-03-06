# Zapapp Frontend con React βοΈ

## Dependencias
### β[aterial UI](https://material-ui.com/getting-started/usage/) + π [Formik](https://stackworx.github.io/formik-material-ui/docs/guide/getting-started/)
- @material-ui/core @material-ui/icons
- formik-material-ui @material-ui/core
- formik-material-ui-lab @material-ui/lab
- formik-material-ui-pickers @date-io/date-fns@1.x date-fns
  
### βοΈ [React](https://reactjs.org/docs/getting-started.html) + π£ [Hooks](https://reactjs.org/docs/hooks-intro.html) + π [Redux]([https://link](https://redux.js.org/introduction/getting-started))
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
### CatΓ‘logo de Remate
![alt text](https://i.ibb.co/7vS7CRT/3catalogo.png "CatΓ‘logo")
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
ββ public
ββ src
   ββ index.css
   ββ index.tsx
   ββ theme.ts
   ββ helpers
   β  ββ AppTypes.d.ts
   ββ actions
   β  ββ api.ts
   β  ββ dCatalogo.ts
   β  ββ dClientes.ts
   β  ββ dConversaciones.ts
   β  ββ dPedidos.ts
   β  ββ dReportes.ts
   β  ββ dServicios.ts
   β  ββ dUsuario.ts
   β  ββ store.ts
   ββ reducers
   |  ββ dCatalogo.ts
   β  ββ dClientes.ts
   |  ββ dConversaciones.ts
   |  ββ dPedidos.ts
   |  ββ dReportes.ts
   |  ββ dServicios.ts
   |  ββ dUsuario.ts
   |  ββ index.ts
   ββ components
      ββ App.css
      ββ App.tsx
      ββ shared
      ββ test 
      ββ layouts
         ββ Auth
         β  ββ Index.tsx
         β  ββ LoginForm.tsx
         β  ββ RegisterForm.tsx
         ββ Catalogo
         β  ββ Crear.tsx
         β  ββ Editar.tsx
         β  ββ index.tsx
         ββ Clientes
         β  ββ Crear.tsx
         β  ββ Editar.tsx
         β  ββ index.tsx
         ββ Conversaciones
         β  ββ Editar.tsx
         β  ββ index.tsx
         ββ Home
         β  ββ index.tsx
         β  ββ Perfil.tsx
         ββ Pedidos
         β  ββ Crear.tsx
         β  ββ Detalle.tsx
         β  ββ EditarEstado.tsx
         β  ββ EnhancedTable.tsx
         β  ββ InputSlider.tsx
         β  ββ index.tsx
         ββ Reportes
         β  ββ CustomBarChart.tsx
         β  ββ CustomPieChart.tsx
         β  ββ TopAbonosClientes.tsx
         β  ββ index.tsx
         ββ Servicios
            ββ Crear.tsx
            ββ index.tsx
```