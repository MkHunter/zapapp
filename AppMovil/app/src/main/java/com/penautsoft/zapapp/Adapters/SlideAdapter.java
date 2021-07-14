package com.penautsoft.zapapp.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import com.penautsoft.zapapp.R;
import com.penautsoft.zapapp.ui.PlacerHolder;
import com.penautsoft.zapapp.ui.sell.ConversationFragment;

public class SlideAdapter{

    //private static Context context;
    private static final int images[] = {R.drawable.shoe_shop,R.drawable.fila_persona,R.drawable.ahorrar};
    private static final String titles[] = {"Zappap","Sin Filas","Ahorra Tiempo"};
    private static final String descriptions[] ={
            "Tu compañero ideal",
            "Librate del martirio de ir hasta la sucursal para poder hacer un encargo",
            "Consulta con nuestro equipo para darte una idea de como resolver tu problema"
    };



    public static class ViewPager extends PagerAdapter{

        LayoutInflater inflater;
        private Context context;

        public ViewPager(Context context){
            this.context = context;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == (LinearLayout) object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int idx) {
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_slide,container,false);

            TextView slideTitle = view.findViewById(R.id.slide_title),
                    slideDesc = view.findViewById(R.id.slide_desc);
            ImageView slideImage = view.findViewById(R.id.slide_img);

            slideTitle.setText(titles[idx]);
            slideDesc.setText(descriptions[idx]);
            slideImage.setImageResource(images[idx]);

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    public class ViewPager2 extends RecyclerView.Adapter{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int idx) {

        }

        @Override
        public int getItemCount() {
            return images.length;
        }
    }

    public static class SellPagerAdapter extends FragmentStatePagerAdapter {

        final String[] titles = {"Activos","Cerrados"};

        public SellPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            Bundle b = new Bundle();
            if( position == 0){
                b.putBoolean("activePub",true);
                fragment = new ConversationFragment();
            }else{
                b.putBoolean("activePub",false);
                fragment = new ConversationFragment();
            }
            fragment.setArguments(b);

            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int idx) {
            return titles[idx];
        }
    }
}
