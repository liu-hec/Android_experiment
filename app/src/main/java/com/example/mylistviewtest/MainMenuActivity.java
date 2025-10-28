package com.example.mylistviewtest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实现点击跳转到Menu页面
        Button btnGoMenu = findViewById(R.id.btn_go_menu);
        btnGoMenu.setOnClickListener(v -> {
            // 点击按钮跳转到菜单页面（MenuActivity）
            startActivity(new Intent(MainMenuActivity.this, MenuActivity.class));
        });
    }
}
