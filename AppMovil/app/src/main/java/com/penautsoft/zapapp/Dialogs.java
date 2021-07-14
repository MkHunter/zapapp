package com.penautsoft.zapapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.penautsoft.zapapp.Entity.MyException;
import com.penautsoft.zapapp.Entity.Order;
import com.penautsoft.zapapp.Entity.Shoe;
import com.penautsoft.zapapp.Networking.HandlerException;
import com.penautsoft.zapapp.Networking.RequestHandler;
import com.penautsoft.zapapp.ui.order.OrderFragment;
import com.penautsoft.zapapp.util.Util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Dialogs {

    private Context context;

    public Dialogs(Context context){
        this.context = context;
    }

    public LoadingDialog getLoadingDialog(){
        return new LoadingDialog();
    }


    public class LoadingDialog{

        private Dialog dialog;

        public void dismissDialog(){
            dialog.dismiss();
        }

        public void simpleLoading(boolean cancelable){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setView(LayoutInflater.from(context).inflate(R.layout.dialog_loading,null));
            builder.setCancelable(cancelable);

            dialog = builder.create();
//            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();
        }



    }
    public static class LoadingResponseDialog extends DialogFragment {

        interface ResponseCallBack{
            void onTouch(boolean sucess);
        }

        private static Dialog dialog;
        public static final String TAG = "example_dialog";
        private ResponseCallBack callBack;

        public void dismissDialog(){
            dialog.dismiss();
        }

        /*public void simpleLoading(boolean cancelable){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setView(LayoutInflater.from(context).inflate(R.layout.dialog_loading,null));
            builder.setCancelable(cancelable);

            dialog = builder.create();
//            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();
        }*/

        public static LoadingResponseDialog display(FragmentManager fragmentManager) {
            Bundle bundle = new Bundle();
            LoadingResponseDialog exampleDialog = new LoadingResponseDialog();
            exampleDialog.setArguments(bundle);
            exampleDialog.show(fragmentManager, TAG);
            return exampleDialog;
        }

        public static LoadingResponseDialog display(FragmentManager fragmentManager,ResponseCallBack response) {
            Bundle bundle = new Bundle();
            LoadingResponseDialog exampleDialog = new LoadingResponseDialog();
            exampleDialog.setResponseListener(response);
            exampleDialog.setArguments(bundle);
            exampleDialog.show(fragmentManager, TAG);
            return exampleDialog;
        }

        private void setResponseListener(ResponseCallBack callback){
            this.callBack = callback;
        }

        public void showErrorResponse(){
            dialog.setContentView(R.layout.dialog_response);
            Button btn = dialog.findViewById(R.id.dialog_response_btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.onTouch(false);
                }
            });
        }

        public void showResponse(final boolean succRegister){
            dialog.setContentView(R.layout.dialog_response);

            TextView txvMess = dialog.findViewById(R.id.dialog_response_message);
            ImageView icon = dialog.findViewById(R.id.dialog_response_icon);

            Button btn = dialog.findViewById(R.id.dialog_response_btn);

            String btnText = "", messText = "";
            int iconRes = 0;

            if( succRegister ) {
                btnText = "Verificar cuenta";
                messText = "Se registro la cuenta exitosamente, por favor ingrese el codigo enviado";
                iconRes = R.drawable.dialog_top_icon_success;
            }else{
                btnText = "Reintenar";
                messText = "El numero telefonico ya esta registrado";
                iconRes = R.drawable.dialog_top_icon_error;
            }


            btn.setText(btnText);
            txvMess.setText(messText);
            icon.setImageResource(iconRes);


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.onTouch(succRegister);
                }
            });
        }

        public void showResponse(final boolean succRegister,Object[] responseBundle){
            dialog.setContentView(R.layout.dialog_response);

            TextView txvMess = dialog.findViewById(R.id.dialog_response_message);
            ImageView icon = dialog.findViewById(R.id.dialog_response_icon);

            Button btn = dialog.findViewById(R.id.dialog_response_btn);

            String btnText = "", messText = "";
            int iconRes = 0;

            if( succRegister ) {
                btnText = (String) responseBundle[0];
                messText = (String) responseBundle[1];
                iconRes = (int) responseBundle[2];
            }else{
                btnText = (String) responseBundle[0];
                messText = (String) responseBundle[1];
                iconRes = (int) responseBundle[2];
            }


            btn.setText(btnText);
            txvMess.setText(messText);
            icon.setImageResource(iconRes);


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.onTouch(succRegister);
                }
            });
        }

        public void showResponse(final boolean succRegister, String responseMessage){
            dialog.setContentView(R.layout.dialog_response);

            TextView txvMess = dialog.findViewById(R.id.dialog_response_message);
            ImageView icon = dialog.findViewById(R.id.dialog_response_icon);

            Button btn = dialog.findViewById(R.id.dialog_response_btn);

            String btnText = "", messText = "";
            int iconRes = 0;

            /*if( succRegister ) {
                btnText = "Ir al login";
                messText = "Se creo el usuario, por favor inicie sesion";
                iconRes = R.drawable.dialog_top_icon_success;
            }else{*/
                btnText = "Cerrar";
                messText = responseMessage;
                iconRes = R.drawable.dialog_top_icon_error;
            //}


            btn.setText(btnText);
            txvMess.setText(messText);
            icon.setImageResource(iconRes);


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.onTouch(succRegister);
                }
            });
        }

        @Override
        public void onStart() {
            super.onStart();
            dialog = getDialog();
            int width = 900;//ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            if (dialog != null) {
                Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            }
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_loading,container,false);
        }
    }

    public static class FullDialog extends DialogFragment implements SwipeRefreshLayout.OnRefreshListener {

        public static final String TAG = "example_dialog";

        private Toolbar toolbar;
        private View rootView,loadingView,statusView,responseView;
        private LinearLayout stepBar;
        private TextView txvToolbarSubtitle,txvStatusName,txvStatusMessage,txvStatusTime;
        private ImageView imgIcon;
        private View[] stepViews = new View[7],stepViewsBck = new View[7];
        private Order order;
        private SwipeRefreshLayout swipeLayout;

        public static FullDialog display(FragmentManager fragmentManager,Order order) {
            Bundle bundle = new Bundle();
            FullDialog exampleDialog = new FullDialog();
            bundle.putSerializable("order",order);
            exampleDialog.setArguments(bundle);
            exampleDialog.show(fragmentManager, TAG);
            return exampleDialog;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
        }

        @Override
        public void onStart() {
            super.onStart();
            Dialog dialog = getDialog();
            if (dialog != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setLayout(width, height);
                dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
            }
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.dialog_order_status, container, false);



            initComponents();

            if( getArguments() != null ){
                order = (Order) getArguments().getSerializable("order");

                String subtitle = "#" + String.valueOf(order.getFolioPedido()) + " * " + order.getFechaPedido().split(" ")[0];

                txvToolbarSubtitle.setText(subtitle);

                loadStatus(false);
            }

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Objects.requireNonNull(getDialog()).dismiss();
                }
            });

            return rootView;
        }

        private void initComponents(){
            toolbar = rootView.findViewById(R.id.dialog_ord_sta_tool);
            txvStatusMessage = rootView.findViewById(R.id.dialog_ord_sta_message);
            txvStatusName = rootView.findViewById(R.id.dialog_ord_sta_name);
            txvStatusTime = rootView.findViewById(R.id.dialog_ord_sta_time);
            txvToolbarSubtitle = rootView.findViewById(R.id.dialog_ord_sta_subtitle);
            imgIcon = rootView.findViewById(R.id.dialog_ord_sta_icon);
            stepBar = rootView.findViewById(R.id.dialog_ord_sta_step_bar);
            loadingView = rootView.findViewById(R.id.dialog_ord_sta_loading) ;
            statusView = rootView.findViewById(R.id.dialog_ord_sta_status) ;
            responseView = rootView.findViewById(R.id.dialog_ord_sta_response) ;
            swipeLayout = rootView.findViewById(R.id.dialog_ord_sta_swipe);

            fillStepView(true);

            stepViewsBck = Arrays.copyOf(stepViews,stepViews.length);
        }

        private void fillStepView(boolean fillContainer){
            for (int i = 0; i < stepViews.length; i++) {
                if( (i+1) % 2 != 0){
                    ImageView imageView = new ImageView(getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            (int)Util.ScreenManagement.convertDpToPx(getContext(),26),
                            (int)Util.ScreenManagement.convertDpToPx(getContext(),26)
                    );
                    imageView.setLayoutParams(layoutParams);
                    imageView.setImageResource(R.drawable.ic_radio_button_unchecked_24);
                    ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(Color.BLACK));

                    stepViews[i] = imageView;
//                    stepViewsBck[i] = imageView;
                }else{
                    View view = new View(getContext());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            (int)Util.ScreenManagement.convertDpToPx(getContext(),20),
                            (int)Util.ScreenManagement.convertDpToPx(getContext(),1)
                    );

                    view.setLayoutParams(layoutParams);
                    view.setBackgroundColor(Color.BLACK);
                    stepViews[i] = view;
//                    stepViewsBck[i] = view;
                }
                if( fillContainer )
                    stepBar.addView(stepViews[i]);
            }
        }

        private void stepManagement(int status){
            ImageView statusIcon;

            stepBar.removeAllViews();
            fillStepView(false);

            for (int i = 0; i < stepViews.length; i++) {

                if( status == 1){
                    ImageViewCompat.setImageTintList((ImageView) stepViews[i], ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    break;
                }else if( status == 2){

                    if( (i+1) % 2 != 0)
                        ImageViewCompat.setImageTintList((ImageView) stepViews[i], ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    else
                        stepViews[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));

                    if( i == 2){
                        ((ImageView) stepViews[0]).setImageResource(R.drawable.ic_check_circle_24);
                        break;
                    }
                }else if( status == 3){

                    if( (i+1) % 2 != 0)
                        ImageViewCompat.setImageTintList((ImageView) stepViews[i], ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    else
                        stepViews[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));

                    if( i == 4) {
                        ((ImageView) stepViews[0]).setImageResource(R.drawable.ic_check_circle_24);
                        ((ImageView) stepViews[2]).setImageResource(R.drawable.ic_check_circle_24);
                        break;
                    }
                }else{
                    if( (i+1) % 2 != 0)
                        ImageViewCompat.setImageTintList((ImageView) stepViews[i], ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    else
                        stepViews[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));

                    if( i == 6){
                        ((ImageView) stepViews[0]).setImageResource(R.drawable.ic_check_circle_24);
                        ((ImageView) stepViews[2]).setImageResource(R.drawable.ic_check_circle_24);
                        ((ImageView) stepViews[4]).setImageResource(R.drawable.ic_check_circle_24);
                    }
                }

            }

            for(View view : stepViews){
                stepBar.addView(view);
            }
        }

        private void checkStatus(){
            try{
                RequestHandler requestHandler = new RequestHandler(getContext());
                RequestHandler.GET getHandler = requestHandler.get();

                String url = getContext().getResources().getString(R.string.host)
                        + "api/Pedidos/estadoPedido/".concat(String.valueOf(order.getFolioPedido()));

                SharedPreferences sha = getContext().getSharedPreferences(getResources().getString(R.string.SharedPrefKey), Context.MODE_PRIVATE);
                Map<String,String> header = new HashMap<>();
                header.put("Authorization","Bearer "+sha.getString("token",null));
                getHandler.getOrderStatus(url, header, new RequestHandler.callBack() {
                    @Override
                    public void respuesta(Object response) {
                        Map<String,Object> data = (Map<String, Object>) response;

                        if( !data.isEmpty() ){
                            int icon = R.drawable.ic_accessibility_24,
                                    status = (int)data.get("orderStatus");
                            String staName = "",staMessa = "",staTime,
                                    currentDate = (String) data.get("requestTime");

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            Date date1,date2;
                            Spannable spannable = null;
                            Spannable spannable1;

                            switch (status){
                                case 1:
                                    icon = R.drawable.ic_order_check;
                                    staName = "EN REVISIÓN";
                                    staMessa = "Por favor espere, su calzado esta siendo revisado por nuestro equipo, en breve empezaremos a tratarlo";
                                    spannable = new SpannableString(staMessa);

                                    try {
                                        date1 = sdf.parse(currentDate != null ? currentDate : "00/00/0000");
                                        date2 = sdf.parse(order.getFechaEstimada());
                                    }catch (Exception e){
                                        date1 = GregorianCalendar.getInstance().getTime();
                                        date2 = GregorianCalendar.getInstance().getTime();
                                    }

//                                    Log.d("BUSCANDO errores",);
                                    staTime = "Termina en " + Util.DateManagement.getRemainTime(date1,date2);

                                    spannable1 = new SpannableString(staTime);
                                    spannable1.setSpan(new ForegroundColorSpan(Color.BLACK),11 ,13,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    spannable1.setSpan(new StyleSpan(Typeface.BOLD),11 ,13,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    txvStatusTime.setText(spannable1,TextView.BufferType.SPANNABLE);
                                    break;
                                case 2:
                                    icon = R.drawable.ic_reapir_progress;
                                    staName = "EN REPARACION";
                                    staMessa = "Su pedido esta siendo tratado, espere pacientemente";
                                    spannable = new SpannableString(staMessa);

                                    try {
                                        date1 = sdf.parse(currentDate != null ? currentDate : "00/00/0000");
                                        date2 = sdf.parse(order.getFechaEstimada());
                                    }catch (Exception e){
                                        date1 = GregorianCalendar.getInstance().getTime();
                                        date2 = GregorianCalendar.getInstance().getTime();
                                    }

                                    staTime = "Termina en " + Util.DateManagement.getRemainTime(date1,date2);// + " dias";
                                    spannable1 = new SpannableString(staTime);
                                    spannable1.setSpan(new ForegroundColorSpan(Color.BLACK),11 ,13,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    spannable1.setSpan(new StyleSpan(Typeface.BOLD),11 ,13,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    txvStatusTime.setText(spannable1,TextView.BufferType.SPANNABLE);
                                    break;
                                case 3:
                                    icon = R.drawable.ic_delay;
                                    staName = "ATRASADO";
                                    String newDate = order.getFechaEstimada().split(" ")[0];
                                    staMessa = "Parece que el trabajo es más dificl de lo que parecia, la nueva fecha de entrega es : "+newDate;

                                    spannable = new SpannableString(staMessa);

                                    spannable.setSpan(new ForegroundColorSpan(Color.BLACK),staMessa.length()-newDate.length() ,staMessa.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                    try {
                                        date1 = sdf.parse(currentDate != null ? currentDate : "00/00/0000");
                                        date2 = sdf.parse(order.getFechaEstimada());
                                    }catch (Exception e){
                                        date1 = GregorianCalendar.getInstance().getTime();
                                        date2 = GregorianCalendar.getInstance().getTime();
                                    }

                                    staTime = "Termina en " + Util.DateManagement.getRemainTime(date1,date2);// + " dias";
                                    spannable1 = new SpannableString(staTime);
                                    spannable1.setSpan(new ForegroundColorSpan(Color.BLACK),11 ,13,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    spannable1.setSpan(new StyleSpan(Typeface.BOLD),11 ,13,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    txvStatusTime.setText(spannable1,TextView.BufferType.SPANNABLE);
                                    break;
                                case 4:
                                    icon = R.drawable.ic_clean_shoes;
                                    staName = "TERMINADO";
                                    staMessa = "Su calzado ha sido reparado puede recorgerlo cuando guste, tiene hasta un mes para hacerlo";
                                    spannable = new SpannableString(staMessa);
                                    break;
                            }

                            imgIcon.setImageResource(icon);
                            txvStatusName.setText(staName);

                            if( status < 4) {
                                txvStatusMessage.setVisibility(View.VISIBLE);
                                txvStatusMessage.setText(spannable, TextView.BufferType.SPANNABLE);
                            }else{
                                boolean isVisible = txvStatusMessage.getVisibility() == View.VISIBLE;
                                if( isVisible)
                                    txvStatusMessage.setVisibility(View.GONE);
                            }

                            stepManagement(status);


                            statusView.setVisibility(View.VISIBLE);
                        }else{
                            TextView txvMessage = rootView.findViewById(R.id.mdl_error_message);
                            ImageView imgIcon  = rootView.findViewById(R.id.mdl_error_icon);
                            //Buton
                            rootView.findViewById(R.id.mdl_error_btn).setVisibility(View.GONE);

                            imgIcon.setImageResource(R.drawable.ic_accessibility_24);
                            txvMessage.setText("No hay pedidos activos");
                            responseView.setVisibility(View.VISIBLE);
                        }
                        loadingView.setVisibility(View.GONE);
                        swipeLayout.setOnRefreshListener(FullDialog.this);
                        swipeLayout.setRefreshing(false);

                    }

                    @Override
                    public void error(VolleyError msg) {
                        Toast.makeText(getContext(),msg.toString(),Toast.LENGTH_SHORT).show();
                        try {
                            HandlerException handlerException = new HandlerException();
                            MyException exception = handlerException.getException(msg);
                            TextView txvMessage = rootView.findViewById(R.id.mdl_error_message);
                            ImageView imgIcon  = rootView.findViewById(R.id.mdl_error_icon);
                            //Buton
                            rootView.findViewById(R.id.mdl_error_btn).setVisibility(View.GONE);

                            imgIcon.setImageResource(exception.getIcon());
                            txvMessage.setText(exception.getMessage());
                            loadingView.setVisibility(View.GONE);
                            responseView.setVisibility(View.VISIBLE);
                            /*swipeLayout.setOnRefreshListener(OrderFragment.this);
                            swipeLayout.setRefreshing(false);*/
                            swipeLayout.setOnRefreshListener(FullDialog.this);
                            swipeLayout.setRefreshing(false);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        private void loadStatus(boolean isRefreshing){
            if( isRefreshing ){
                loadingView.setVisibility(View.VISIBLE);
                responseView.setVisibility(View.GONE);
                statusView.setVisibility(View.GONE);
            }

            checkStatus();
        }

        @Override
        public void onRefresh() {
            loadStatus(true);
        }
    }

    public static class ShoeDetailFullDialog extends DialogFragment{

        public static final String TAG = "example_dialog";

        private Toolbar toolbar;
        private View rootView,detailView;
        private TextView txvBrand,txvColor,txvSize,txvPrice;
        private ImageView imgIcon;
        private Shoe shoe;
        private boolean isShown = false;


        public static ShoeDetailFullDialog display(FragmentManager fragmentManager, Shoe shoe) {
            Bundle bundle = new Bundle();
            ShoeDetailFullDialog exampleDialog = new ShoeDetailFullDialog();
            bundle.putSerializable("shoe",shoe);
            exampleDialog.setArguments(bundle);
            exampleDialog.show(fragmentManager, TAG);
            return exampleDialog;
        }



        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

//            setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
        }

        @Override
        public void onStart() {
            super.onStart();
            Dialog dialog = getDialog();
            if (dialog != null) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setLayout(width, height);
                dialog.getWindow().setWindowAnimations(R.style.AppTheme_Full);
            }
        }



        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.dialog_firesale_detail, container, false);

            initComponents();

//
            if( getArguments() != null ){
                shoe = (Shoe) getArguments().getSerializable("shoe");

                try{
                    Glide.with(getContext()).
//                            load(getContext().getResources().getString(R.string.image_test))
        load(shoe.getImagen())
                            .placeholder(R.drawable.ic_usuario)
                            .error(R.drawable.ic_baseline_vpn_key_24)
                            .fallback(R.drawable.ic_baseline_lock)
                            .into(imgIcon);
                }catch(NullPointerException e){
                    e.printStackTrace();
                }

                txvBrand.setText(shoe.getMarca());
                txvColor.setText(shoe.getColor());
                txvPrice.setText("$ ".concat(String.valueOf(shoe.getPrecio())));
                txvSize.setText(String.valueOf(shoe.getTalla()));
                toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if( !isShown )
                            slideUp();
                        else
                            slideDown();

                        isShown = !isShown;

                        return false;
                    }
                });
            }

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Objects.requireNonNull(getDialog()).dismiss();
                }
            });

            return rootView;

        }

        public void slideUp(){
            detailView.setVisibility(View.VISIBLE);
            TranslateAnimation animate = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,                 // toXDelta
                    detailView.getHeight(),  // fromYDelta
                    0);                // toYDelta
            ScaleAnimation scale = new ScaleAnimation(
                    0,
                    1,
                    0,
                    1
            );
            animate.setDuration(250);
            animate.setFillAfter(true);
            detailView.startAnimation(animate);
        }

        public void slideDown(){
            TranslateAnimation animate = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,                 // toXDelta
                    0,                 // fromYDelta
                    detailView.getHeight()); // toYDelta
            animate.setDuration(250);
            animate.setFillAfter(true);
            detailView.startAnimation(animate);
        }

        private void initComponents(){
            toolbar = rootView.findViewById(R.id.dialog_firesale_det_tool);
            imgIcon = rootView.findViewById(R.id.dialog_firesale_det_photo);
            detailView = rootView.findViewById(R.id.dialog_firesale_det_layout);
            txvBrand = rootView.findViewById(R.id.dialog_firesale_det_brand);
            txvColor = rootView.findViewById(R.id.dialog_firesale_det_color);
            txvSize = rootView.findViewById(R.id.dialog_firesale_det_size);
            txvPrice = rootView.findViewById(R.id.dialog_firesale_det_price);
        }
    }
}
