package com.penautsoft.zapapp.Networking;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.penautsoft.zapapp.Entity.User;
import com.penautsoft.zapapp.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler {

    private Context context;
    private RequestQueue requestQueue;
    public RequestHandler(Context ctx){
        context = ctx;
        requestQueue = Volley.newRequestQueue(context);
    }

    public interface callBack{
        void respuesta(Object response);
        void error(VolleyError msg);
    }

    public GET get(){
        return new GET();
    }

    public POST post(){
        return new POST();
    }

    public PUT put(){
        return new PUT();
    }

    public void getString(String url, final Map<String,String> header, final Map<String,String> body, final callBack call) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.w("AQUI VIENE EL YEISON",response);
                call.respuesta(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String msg = "";
                try {
                    msg = "" + error.networkResponse.statusCode;
                    msg += " " + error.getCause();
                }catch (Exception e){ e.printStackTrace(); }

                msg += " " + error.toString();
                Log.d("PRIMERO GET",""+msg);
                call.error(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return header != null ? header : super.getHeaders();
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        requestQueue.add(request);
    }

    public class GET{

        private final int GET_METHOD = Request.Method.GET;

        public void getCommentsById(String url,final Map<String,String> header, final Map<String,String> body, final callBack call){
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    call.respuesta(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    call.error(error);
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return header != null ? header : super.getHeaders();
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            requestQueue.add(request);
        }

        public void getOrders(String url,final Map<String,String> header, final Map<String,String> body, final callBack call){
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest request = new StringRequest(GET_METHOD, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    call.respuesta(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    call.error(error);
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return header != null ? header : super.getHeaders();
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            requestQueue.add(request);
        }

        public void getServicesById(String url,final Map<String,String> header, final Map<String,String> body, final callBack call){
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest request = new StringRequest(GET_METHOD, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    call.respuesta(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    call.error(error);
                }
            }){

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return header != null ? header : super.getHeaders();
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            requestQueue.add(request);
        }

        public void getPhotos(String url,final Map<String,String> header, final callBack call){
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest request = new StringRequest(GET_METHOD, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    call.respuesta(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    call.error(error);
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return header != null ? header : super.getHeaders();
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            requestQueue.add(request);
        }

        public void getOrderStatus(String url,final Map<String,String> header, final callBack call){

            JsonObjectRequest request = new JsonObjectRequest(GET_METHOD, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(RequestHandler.class.getCanonicalName(),response.toString());
                    Map<String,Object> out = new HashMap<>();

                    try{
                        out.put("orderId",response.getInt("folioPedido"));
                        out.put("orderDate",response.getString("fechaPedido"));
                        out.put("orderStatus",response.getInt("estadoPedido"));
                        out.put("requestTime",response.getString("horaRequest"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    call.respuesta(out);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    call.error(error);
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return header != null ? header : super.getHeaders();
                }
            };

            requestQueue.add(request);
        }
    }

    public class POST{

        private final int POST_METHOD = Request.Method.POST;

        public void createPublisment(String url, final Map<String,String> header,final Map<String,Object> body, final callBack callBack){
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    callBack.respuesta(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callBack.error(error);
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

              /*  @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    return body != null ? body : super.getParams();
                }*/

                @Override
                public byte[] getBody() {
                    JSONObject jsonObject = new JSONObject(body);
                    Log.w("YEISON",jsonObject.toString());
//                    Log.w("EL OTRO YEISON",json);

                    return jsonObject.toString().getBytes();
                    //return jsonObject.toString().getBytes();
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return header != null? header : super.getHeaders();
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            requestQueue.add(request);
        }

        public void createClient(String url, final Map<String,Object> body, final callBack callBack){

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Map<String,Object> data = new HashMap<>();
                    try{

                        String msg = response.getString("mensaje");
                        int id = response.getInt("idUsuario");
                        boolean status = response.getBoolean("status");

                        data.put("message",msg);
                        data.put("userId",id);
                        data.put("status",status);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    callBack.respuesta(data);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callBack.error(error);
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody(){
                    JSONObject jsonObject = new JSONObject(body);
                    Log.w("YEISON",jsonObject.toString());

                    return jsonObject.toString().getBytes();
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return super.getHeaders();
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            requestQueue.add(request);
        }

        public void loginManagement(String url, final Map<String,Object> body, final Map<String,String> header,final callBack callBack){

            JsonObjectRequest request = new JsonObjectRequest(POST_METHOD, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    String token;
                    boolean succesful = false;

                    try{
                        succesful = response.getBoolean("status");
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    Map<String,Object> newResponse = new HashMap<>();
                    newResponse.put("successful",succesful);
                    if( succesful ){
                        try {
                            Gson gson = new Gson();
                            JSONObject userJSON = response.getJSONObject("usuario");
                            User user = gson.fromJson(userJSON.toString(),User.class);
                            Log.d("EJEMPLO",user.toString());
                            SharedPreferences sharedPreferences = context.getSharedPreferences(
                                    context.getResources().getString(R.string.SharedPrefKey),
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            token = (String) response.get("token");
                            editor.putString("token",token);
                            editor.putInt("idUser",user.getIdUsuario());
                            editor.putString("name",user.getNombreUsuario());
                            editor.putInt("role",user.getRol());
                            editor.putString("cliName",user.getEntidad().getNombre());
                            editor.putString("cliLastName",user.getEntidad().getApellido());
                            editor.putString("cliPhone",user.getEntidad().getTelefono());
                            editor.putString("cliEmail",user.getEntidad().getCorreo());
                            editor.putInt("cliAvaible",user.getEntidad().getDisponible());
                            editor.apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    callBack.respuesta(newResponse);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callBack.error(error);
                }
            }){

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return header != null ? header : super.getHeaders();
                }

                @Override
                public byte[] getBody() {
                    if( body != null ){
                        JSONObject yeison = new JSONObject(body);
                        String json = yeison.toString();
                        Log.w("LOGIN YEISON ==> ",json);
                        return json.getBytes();
                    }else
                        return "{}".getBytes();
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            requestQueue.add(request);
        }
    }

    public class PUT{
        public void putCommentsById(String url,final Map<String,String> header, final Map<String,Object> body, final callBack call){
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            StringRequest request = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    call.respuesta(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    call.error(error);
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody(){
                    JSONObject jsonObject = new JSONObject(body);
                    Log.w("YEISON",jsonObject.toString());

                    return jsonObject.toString().getBytes();
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return header != null ? header : super.getHeaders();
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            requestQueue.add(request);
        }

        public void verifyAccount(String url, final Map<String,Object> body, final callBack callBack){

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Map<String,Object> data = new HashMap<>();
                    try{

                        String msg = response.getString("mensaje");
                        boolean status = response.getBoolean("estatus");

                        data.put("message",msg);
                        data.put("status",status);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    callBack.respuesta(data);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    callBack.error(error);
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody(){
                    JSONObject jsonObject = new JSONObject(body);
                    Log.w("YEISON",jsonObject.toString());

                    return jsonObject.toString().getBytes();
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            requestQueue.add(request);
        }
    }
}
