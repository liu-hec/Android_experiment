package com.example.mylistviewtest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AlertDialogTestActivity extends AppCompatActivity {

    private AlertDialog alertDialog; // 对话框实例

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化并显示对话框
        initLoginDialog();
    }

    // 初始化登录对话框
    private void initLoginDialog() {
        //1.获取LayoutInflater，加载自定义布局
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.activity_login, null); // 加载dialog_login.xml


        //2.创建AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView); // 关键：将自定义布局设置给对话框
        builder.setCancelable(false); // 点击外部不关闭对话框

//        builder.setPositiveButton("确认", (dialog, which) -> {
//            // 点击确认后执行的逻辑（如删除数据、提交操作等）
//            Toast.makeText(this, "已确认", Toast.LENGTH_SHORT).show();
//            dialog.dismiss(); // 关闭对话框（可选，点击按钮后默认会关闭）
//        });
        //3 构建对话框实例
        alertDialog = builder.create();




        //4显示对话框
        alertDialog.show();
    }
}
