package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ToolsFragment extends Fragment {
    private TextView toolbar;
    private TextView xuesheng,pai,newyemian,chafen;
    private boolean isVisible = false;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return  inflater.inflate(R.layout.fragment_tools,container,false);
    }

    @Override
    public void onResume() {
        super.onResume();
        isVisible = true;
        if (isVisible) {
            pai=getActivity().findViewById(R.id.pai);
            xuesheng =getActivity().findViewById(R.id.xuesheng);
            toolbar = getActivity().findViewById(R.id.toolbar);
            newyemian=getActivity().findViewById(R.id.newyemian);
            chafen=getActivity().findViewById(R.id.chafen);

            chafen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Chafen.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    startActivity(intent);
                }
            });
            newyemian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Activity5.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    startActivity(intent);
                }
            });
            xuesheng.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), StudentManager.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    startActivity(intent);
                }
            });
            pai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), PhotoList.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    startActivity(intent);
                }
            });
            toolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Activity3.class);
                    intent.setAction(Intent.ACTION_VIEW);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isVisible = false;
    }

}
