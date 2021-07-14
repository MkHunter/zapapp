package com.penautsoft.zapapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.penautsoft.zapapp.Entity.Conversation;
import com.penautsoft.zapapp.Entity.ImageItem;
import com.penautsoft.zapapp.Entity.Order;
import com.penautsoft.zapapp.Entity.Shoe;
import com.penautsoft.zapapp.ImageClickListener;
import com.penautsoft.zapapp.PublishmentDetailActivity;
import com.penautsoft.zapapp.R;
import com.penautsoft.zapapp.RecyclerItemClickListener;
import com.penautsoft.zapapp.util.Util;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyListAdapter {

    private Context context;

    public MyListAdapter(Context ctx){
        context = ctx;
    }
    public MyListAdapter(){}


    public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {


        private ArrayList<Conversation> items;
        private boolean isTabActive;
        private final static int TEXTLIMIT = 250;
        SharedPreferences sharedPreferences;

        public CommentsAdapter(ArrayList<Conversation> items,boolean tab){
            this.items = items;
            isTabActive = tab;
            sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.SharedPrefKey),Context.MODE_PRIVATE);
        }

        @NonNull
        @Override
        public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_publishing,parent,false);
            return new CommentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentViewHolder holder, int idx) {

            holder.idx = idx;
            Conversation conv = items.get(idx);

            holder.conversation = conv;

            holder.txvDescription.setText(conv.getPostPrincipal());

            holder.txvUser.setText(sharedPreferences.getString("cliName", null) + " " + sharedPreferences.getString("cliLastName", null));


            String text = holder.txvDescription.getText().toString();

            if( text.length() > TEXTLIMIT )
                holder.txvDescription.setText(text.substring(0,TEXTLIMIT) + "...Ver mas");


            try{
                String[] limit = text.split("\n");
                int bound = 5;
                if(limit.length > bound){
                    String aux = "";
                    for (int i = 0; i < bound; i++) {
                        aux += limit[i];
                        if( i != 4)
                            aux += "\n";
                    }
                    aux += "...Ver mas";
                    holder.txvDescription.setText(aux);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            try {
                holder.txvDate.setText(Util.DateManagement.dateView(sdf.parse(conv.getFechaPost()),sdf.parse(conv.getHoraRequest()),5));
            } catch (Exception e) {
                e.printStackTrace();
                holder.txvDate.setText(conv.getFechaPost());
            }

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        private class CommentViewHolder extends RecyclerView.ViewHolder{

            public View imgIcon;
            public ImageView imgPhoto;
            public TextView txvUser,txvDate,txvDescription;
            public int idx;
            public Conversation conversation;


            public CommentViewHolder(@NonNull View itmvw) {
                super(itmvw);
                LinearLayout linearLayout = itmvw.findViewById(R.id.ml_publish_root);

                imgIcon = itmvw.findViewById(R.id.mdl_publish_icon);
                txvDate = itmvw.findViewById(R.id.mdl_publish_date);
                txvDescription = itmvw.findViewById(R.id.mdl_publish_description);
                txvUser = itmvw.findViewById(R.id.mdl_publish_user);


                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, PublishmentDetailActivity.class);

                        intent.putExtra("obj",conversation);
                        intent.putExtra("active",isTabActive);

                        Log.w("PADSSS",conversation.toString());

                        context.startActivity(intent);
                    }
                });
            }
        }
    }

    public class DetailPhotosAdapter extends RecyclerView.Adapter<DetailPhotosAdapter.PhotoViewHolder>{

        private ArrayList<String> urls;
        private ArrayList<Bitmap> bitmaps;
        private int width,height;

        public DetailPhotosAdapter(ArrayList<String> urls,int width,int height){
            this.urls = urls;
            this.width = width;
            this.height = height;
        }

        public DetailPhotosAdapter(ArrayList<Bitmap> bits){
            this.bitmaps = bits;
            this.width = width;
            this.height = height;
        }

        @NonNull
        @Override
        public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.model_photos,parent,false);
            return new PhotoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoViewHolder holder, int idx) {
            int size = getItemCount();
            LinearLayout.LayoutParams layoutParams;
            if( urls != null ){
                int newWidth = (int) (width * ( size == 1 ? 1 : 0.5/*size == 2 ? 0.5 : 0.333*/ ) );
                layoutParams = new LinearLayout.LayoutParams(newWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                holder.photo.setLayoutParams(layoutParams);
                try{
                    Glide.with(context).
                            load(urls.get(idx))
                            .placeholder(R.drawable.ic_usuario)
                            .error(R.drawable.ic_baseline_vpn_key_24)
                            .fallback(R.drawable.ic_baseline_lock)
                            .into(holder.photo);
                }catch(NullPointerException e){
                    e.printStackTrace();
                }
            }

        }

        @Override
        public int getItemCount() {
            return urls != null ? urls.size() : bitmaps.size();
        }

        private class PhotoViewHolder extends RecyclerView.ViewHolder{

            public ImageView photo;

            public PhotoViewHolder(@NonNull View itemView) {
                super(itemView);
                photo = itemView.findViewById(R.id.mdl_photo_image);
            }
        }
    }

    public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>{

        private Context context;
        private RecyclerItemClickListener recyclerItemClickListener;
        private ArrayList<Order> orders;
        SimpleDateFormat sdf;

        public OrdersAdapter(Context context, RecyclerItemClickListener recyclerItemClickListener, ArrayList<Order> orders) {
            this.context = context;
            this.recyclerItemClickListener = recyclerItemClickListener;
            this.orders = orders;
            sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        }

        @NonNull
        @Override
        public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.model_order,parent,false);
            return new OrdersViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull OrdersViewHolder holder, int idx) {
            Order order = orders.get(idx);

            holder.txvTitle.setText(order.getDescripcion());

            
            try{
                holder.txvDate.setText(Util.DateManagement.dateView(sdf.parse(order.getFechaPedido()),sdf.parse(order.getHoraRequest()),5));
            }catch(Exception e){
                e.printStackTrace();
                holder.txvDate.setText(order.getFechaPedido().split(" ")[0]);
            }

            holder.order = order;
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        public class OrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView txvDate,txvTitle;
            private Button btnDetail,btnStatus;
            Order order;

            public OrdersViewHolder(@NonNull View view) {
                super(view);
                txvDate = view.findViewById(R.id.mdl_order_date);
                txvTitle = view.findViewById(R.id.mdl_order_title);
                btnDetail = view.findViewById(R.id.mdl_order_detail);
                btnStatus = view.findViewById(R.id.mdl_order_status);

                btnDetail.setOnClickListener(this);
                btnStatus.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                recyclerItemClickListener.onItemClick(order,view.getId(),null);
            }
        }
    }

    public class FiresaleAdapter extends RecyclerView.Adapter<FiresaleAdapter.ShoesViewHolder>{

        private Context context;
        private RecyclerItemClickListener recyclerItemClickListener;
        private ArrayList<Shoe> shoes;

        public FiresaleAdapter(Context context, RecyclerItemClickListener recyclerItemClickListener, ArrayList<Shoe> shoes) {
            this.context = context;
            this.recyclerItemClickListener = recyclerItemClickListener;
            this.shoes = shoes;
        }

        @NonNull
        @Override
        public ShoesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.model_firesale_shoe,parent,false);
            return new ShoesViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ShoesViewHolder holder, int idx) {
            Shoe shoe = holder.shoe = shoes.get(idx);

            try{
                Glide.with(context).
                        load(shoe.getImagen())
                        .placeholder(R.drawable.ic_usuario)
                        .error(R.drawable.ic_baseline_vpn_key_24)
                        .fallback(R.drawable.ic_baseline_lock)
                        .into(holder.photo);
            }catch(NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return shoes.size();
        }

        public class ShoesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView txvDate;
            ImageView photo;
            private View root;
            Shoe shoe;

            public ShoesViewHolder(@NonNull View view) {
                super(view);
                photo = view.findViewById(R.id.mdl_firesale_photo);
                root = view.findViewById(R.id.mdl_firesale_root);

                root.setOnClickListener(this);

            }

            @Override
            public void onClick(View view) {
                recyclerItemClickListener.onItemClick(shoe,view.getId(),photo);
            }
        }
    }

    public class CreatePostAdapter extends ArrayAdapter {

        private ArrayList<Bitmap> bitmaps;
        private LayoutInflater inflater;

        public CreatePostAdapter(@NonNull Context context, int resource, ArrayList<Bitmap> objects) {
            super(context, resource);
            this.bitmaps = objects;
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            if( bitmaps != null )
                return bitmaps.size();

            return 0;
        }

        @NonNull
        @Override
        public View getView(int idx, @Nullable View otherView, @NonNull ViewGroup parent) {

            View root = inflater.inflate(R.layout.model_photos,parent,false);
            ImageView photo = root.findViewById(R.id.mdl_photo_image);


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            photo.setLayoutParams(layoutParams);
            photo.setImageBitmap(bitmaps.get(idx));

            return root;
        }
    }

    public class GridAdapter extends ArrayAdapter<ImageItem>{

        private final LayoutInflater layoutInflater;
        private ImageClickListener imageClickListener;
        private List<ImageItem> items;
        private int visibility = 0;

        public GridAdapter(Context context, List<ImageItem> items,ImageClickListener listener) {
            super(context, 0, items);
            layoutInflater = LayoutInflater.from(context);
            this.imageClickListener = listener;
            this.items = items;
        }

        public GridAdapter(Context context) {
            super(context, 0);
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ImageItem item = getItem(position);
            final View v = layoutInflater.inflate( R.layout.model_photos, parent, false);
            ImageView photo = v.findViewById(R.id.mdl_photo_image);
            final LinearLayout actionLayout = v.findViewById(R.id.mdl_photo_over_menu);
            Button btnWatch = v.findViewById(R.id.mdl_photo_btn_view), btnDel = v.findViewById(R.id.mdl_photo_btn_delete);



            photo.setImageBitmap(item.getBitmap());
            photo.setAdjustViewBounds(true);

            if( items.size() > 3){
                btnDel.setText("");
                btnDel.getLayoutParams().width = (int) Util.ScreenManagement.convertDpToPx(getContext(),45);
                btnWatch.setText("");
                btnWatch.getLayoutParams().width = (int) Util.ScreenManagement.convertDpToPx(getContext(),45);
            }


            photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    visibility = actionLayout.getVisibility() == View.GONE ? View.VISIBLE : View.GONE;
                    actionLayout.setVisibility(visibility);
                }
            });

            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageClickListener.onImageClick(position,item.getBitmap(),view.getId());
                }
            });

            btnWatch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageClickListener.onImageClick(position,item.getBitmap(),view.getId());
                }
            });

            photo.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    imageClickListener.onLogImageLick(position,item.getBitmap());
                    return true;
                }
            });

            return v;
        }

        @Override public int getViewTypeCount() {
            return 2;
        }

        @Override public int getItemViewType(int position) {
            return position % 2 == 0 ? 1 : 0;
        }

        public void appendItems(List<ImageItem> newItems) {
            addAll(newItems);
            notifyDataSetChanged();
        }

        public void setItems(List<ImageItem> moreItems) {
            clear();
            appendItems(moreItems);
        }
    }
}
