package com.example.sapperjava.presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.sapperjava.R;
import com.example.sapperjava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String BACKGROUND_URL = "http://95.217.132.144/sapper/background_image.jpg";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadBackground();
    }

    private void loadBackground(){
        try {
            Glide.with(this).load(BACKGROUND_URL).into(new CustomTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    binding.frameLayout.setBackground(resource);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {

                }
            });
        }catch (Exception ignored){
        }
    }
}