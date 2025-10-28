package com.example.mylistviewtest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    private TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_test_text);
        tvTest = findViewById(R.id.tv_test);
    }
    // 加载菜单XML文件
    // 加载菜单资源
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //处理点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId(); // 获取菜单项ID

        // 字体大小逻辑
        if (itemId == R.id.font_small) {
            tvTest.setTextSize(10);
        } else if (itemId == R.id.font_medium) {
            tvTest.setTextSize(16);
        } else if (itemId == R.id.font_large) {
            tvTest.setTextSize(20);
        }
        // 普通菜单项逻辑
        else if (itemId == R.id.action_common) {
            Toast.makeText(this, "点击了普通菜单项", Toast.LENGTH_SHORT).show();
        }
        // 字体颜色逻辑
        else if (itemId == R.id.color_black) {
            tvTest.setTextColor(Color.BLACK);
        } else if (itemId == R.id.color_red) {
            tvTest.setTextColor(Color.RED);
        }

        return true;
    }



}
