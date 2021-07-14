package com.penautsoft.zapapp.ui.sell;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.penautsoft.zapapp.Adapters.SlideAdapter;
import com.penautsoft.zapapp.CreateConversationActivity;
import com.penautsoft.zapapp.R;

public class SellFragment extends Fragment {

    SlideAdapter.SellPagerAdapter sellPagerAdapter;
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell_main,container,false);
        sellPagerAdapter = new SlideAdapter.SellPagerAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.fg_sell_pager);
        viewPager.setAdapter(sellPagerAdapter);

        return view;
    }

}
