package com.penautsoft.zapapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.penautsoft.zapapp.R;

public class PlacerHolder extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_palce_holder, container, false);

        return root;
    }
}
