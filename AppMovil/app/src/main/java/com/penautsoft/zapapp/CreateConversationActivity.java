package com.penautsoft.zapapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.VolleyError;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;
import com.google.gson.Gson;
import com.penautsoft.zapapp.Adapters.MyListAdapter;
import com.penautsoft.zapapp.Entity.Conversation;
import com.penautsoft.zapapp.Entity.ImageItem;
import com.penautsoft.zapapp.Networking.RequestHandler;
import com.penautsoft.zapapp.util.Util;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateConversationActivity extends AppCompatActivity implements ImageClickListener{

    private static final int CAMERA_PERM = 101;
    private static final int PICK_PHOTO = 10;
    private static final int TAKE_PHOTO = 11;
    private TextView txvDesc;
    private ArrayList<String> links;
    private ArrayList<ImageView> photos;
    private RequestHandler.POST postHandler;
    private boolean testing = true;
    private MyListAdapter.GridAdapter gridAdapter;
    private ArrayList<ImageItem> imageItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_conversation);
        setSupportActionBar((Toolbar) findViewById(R.id.act_conv_tool));

        init();
    }

    private void init(){
        txvDesc = findViewById(R.id.act_conv_message);
        links = new ArrayList<>();
        photos = new ArrayList<>();

        RequestHandler handler = new RequestHandler(this);
        postHandler = handler.post();

        MyListAdapter mainAdapter = new MyListAdapter(this);
        AsymmetricGridView collageImageView = findViewById(R.id.listView);
        collageImageView.setAllowReordering(true);
        imageItems = new ArrayList<>();
        gridAdapter = mainAdapter.new GridAdapter(this, imageItems,this);
        collageImageView.setAdapter(new AsymmetricGridViewAdapter(this, collageImageView, gridAdapter));
        collageImageView.setRequestedColumnCount(3);
    }

    private void dameLosbytes(){

        for( ImageView img: photos){
            Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] imageInByte = baos.toByteArray();
            links.add(Base64.encodeToString(imageInByte,Base64.NO_WRAP));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_conversation_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if( id == R.id.mn_conv_save){
            final String resp = txvDesc.getText().toString();

            final Dialogs dialogs = new Dialogs(this);
            final Dialogs.LoadingDialog loadingDialog = dialogs.getLoadingDialog();


            if( !resp.equals("") ){
                loadingDialog.simpleLoading(true);
                String host = getResources().getString(R.string.host),
                        fullUrl = host+"api/Conversaciones";

                final String date = Util.DateManagement.getDeviceFormattedDate("dd/MM/yyyy");
                dameLosbytes();

                Map<String,Object> body = new HashMap<>();
                body.put("usuarioPost",1);
                body.put("fechaPost",date);
                body.put("estado",1);
                body.put("postPrincipal",resp);
                body.put("imagenPostPrincipal", links);

                SharedPreferences sha = getSharedPreferences(getResources().getString(R.string.SharedPrefKey), Context.MODE_PRIVATE);
                Map<String,String> header = new HashMap<>();
                header.put("Authorization","Bearer "+sha.getString("token",null));

                postHandler.createPublisment(fullUrl,header, body,new RequestHandler.callBack() {
                    @Override
                    public void respuesta(Object response) {
                        Log.w(CreateConversationActivity.class.getSimpleName(), response.toString());
                        Gson gson = new Gson();

                        Conversation conv = null;

                        try{
                            conv = gson.fromJson( (String)response, Conversation.class);
                            conv.setHoraRequest(conv.getFechaPost());
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        Intent intent = new Intent();
                        loadingDialog.dismissDialog();


                        intent.putExtra("newConv",conv);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void error(VolleyError msg) {
                        loadingDialog.dismissDialog();
                        Toast.makeText(CreateConversationActivity.this,msg.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(this,"NO puede crear una conversacion vacia, escriba algo por favor",Toast.LENGTH_SHORT).show();
            }

            return true;
        }else if( id == android.R.id.home){
            finish();
            return true;
        }else
            return super.onOptionsItemSelected(item);
    }


    public void pickImage(View v){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent,PICK_PHOTO);
    }

    public void takeImage(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    public void askCameraPermission(View v){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, CAMERA_PERM);
        else
            takeImage();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if( requestCode == CAMERA_PERM ){
            if( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED )
                takeImage();
            else
                Toast.makeText(this,"Se requiere permisos de la camara",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView picture = new ImageView(this);
        if( requestCode == PICK_PHOTO && resultCode== RESULT_OK ){
            if ( data == null )
                return;

            if( testing){

                ClipData clipData = data.getClipData();
                ImageItem imgItem;
                Uri imageURI;

                if( clipData != null ){
                    Log.d(CreateConversationActivity.class.getCanonicalName(),String.valueOf(clipData.getItemCount()));
                    int size = clipData.getItemCount();
                    for (int i = 0; i < size; i++) {
                        imageURI = clipData.getItemAt(i).getUri();

                        try{
                            InputStream is = getContentResolver().openInputStream(imageURI);
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            picture.setImageBitmap(bitmap);
                            photos.add(picture);

                            if(imageItems.size() == 0)
                                imgItem = new ImageItem(3,2,imageItems.size(),bitmap);
                            else if( imageItems.size() == 1){
                                imageItems.get(0).changeSpan(3,1);
                                imgItem = new ImageItem(3,1,imageItems.size(),bitmap);
                            }else if( imageItems.size() == 2){
                                imageItems.get(0).changeSpan(2,1);
                                imageItems.get(1).changeSpan(2,1);
                                imgItem = new ImageItem(1,2,imageItems.size(),bitmap);
                            }else {
                                imageItems.get(1).changeSpan(1,1);
                                imageItems.get(2).changeSpan(1,1);
                                imgItem = new ImageItem(1,1,imageItems.size(),bitmap);
                            }
                            imageItems.add(imgItem);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    try {
                        imageURI = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageURI);
                        picture.setImageBitmap(bitmap);
                        photos.add(picture);

                        if(imageItems.size() == 0)
                            imgItem = new ImageItem(3,2,imageItems.size(),bitmap);
                        else if( imageItems.size() == 1){
                            imageItems.get(0).changeSpan(3,1);
                            imgItem = new ImageItem(3,1,imageItems.size(),bitmap);
                        }else if( imageItems.size() == 2){
                            imageItems.get(0).changeSpan(2,1);
                            imageItems.get(1).changeSpan(2,1);
                            imgItem = new ImageItem(1,2,imageItems.size(),bitmap);
                        }else {
                            imageItems.get(1).changeSpan(1,1);
                            imageItems.get(2).changeSpan(1,1);
                            imgItem = new ImageItem(1,1,imageItems.size(),bitmap);
                        }


                        imageItems.add(imgItem);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                gridAdapter.notifyDataSetChanged();
            }
        }else if( requestCode == TAKE_PHOTO && resultCode == RESULT_OK ){
            Bitmap bitmap = null;
            if (data != null) {
                bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            }

            picture.setImageBitmap(bitmap);
            if( testing ){
                ImageItem imgItem;
                if(imageItems.size() == 0)
                    imgItem = new ImageItem(3,2,imageItems.size(),bitmap);
                else if( imageItems.size() == 1){
                    imageItems.get(0).changeSpan(3,1);
                    imgItem = new ImageItem(3,1,imageItems.size(),bitmap);
                }else if( imageItems.size() == 2){
                    imageItems.get(0).changeSpan(2,1);
                    imageItems.get(1).changeSpan(2,1);
                    imgItem = new ImageItem(1,2,imageItems.size(),bitmap);
                }else {
                    imageItems.get(1).changeSpan(1,1);
                    imageItems.get(2).changeSpan(1,1);
                    imgItem = new ImageItem(1,1,imageItems.size(),bitmap);
                }
                /*int h = 1080;
                int w = 1080;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w,h);
                picture.setLayoutParams(layoutParams);*/
                photos.add(picture);
                imageItems.add(imgItem);
                gridAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateImageContent(){
        ImageItem imgIt;
        int size = imageItems.size();
        for (int i = 0; i < size; i++) {
            imgIt = imageItems.get(i);
            imgIt.setPosition(i);

            if( size == 1){
                imgIt.changeSpan(3,2);
            }else if( size == 2){
                imgIt.changeSpan(3,1);
            }else if( size == 3){
                if( i < 2)
                    imgIt.changeSpan(2,1);
                else
                    imgIt.changeSpan(1,2);
            }else if( size == 4){
                if( i != 0)
                    imgIt.changeSpan(1,1);
                else
                    imgIt.changeSpan(2,1);
            }else{
                imgIt.changeSpan(1,1);
            }
        }
    }



    @Override
    public void onImageClick(int idx, Bitmap image,int viewId) {
        if( viewId == R.id.mdl_photo_btn_delete ){
            imageItems.remove(idx);
            updateImageContent();
            gridAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLogImageLick(int idx, Bitmap image) {
        imageItems.remove(idx);
        updateImageContent();
        gridAdapter.notifyDataSetChanged();
    }
}