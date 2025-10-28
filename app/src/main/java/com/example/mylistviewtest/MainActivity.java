package com.example.mylistviewtest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
//         ListView listView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_arrayadapter);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.TestTwoListView), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        listView=(ListView) findViewById(R.id.TestTwoListView);
//
//        String[] strings=new String[]{"语文","数学","英语","体育"};
//        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings);
//        //context 上下文    @LayoutRes int resource 单个列表项的ui设计 可以自定义或者使用系统内置的布局设置
//
//        listView.setAdapter(arrayAdapter);
//
//
//    }
    //1.创建base视图（设立整体的架构） 和item视图（基本的列情况）
    //2.准备数据 数据一一对应 使用SimpleAdapter适配器来处理ListView处理并显示数据
    ListView listView;
    LinearLayout linearLayout;
    String[] names=new String[]{"Lion","Tiger","Monkey","Dog","Cat"};
    int[] ImageArr={R.drawable.lion,R.drawable.tiger,R.drawable.monkey,R.drawable.dog,R.drawable.cat};
    //数据
    List<Map<String,Object>> list=new ArrayList<>();

    // 通知相关：渠道ID、渠道名称、NotificationManager
    private static final String CHANNEL_ID = "list_notification_channel";
    private static final String CHANNEL_NAME = "列表项通知";
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_base_simple);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ListViewSimple), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // 初始化 NotificationManager
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Android 8.0+ 需创建 NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //版本判断
            createNotificationChannel();
        }





        //连接ListView
        listView=(ListView) findViewById(R.id.ListViewSimple);//主布局
        //加载数据为List<Map<>> getData()
        //创建SimpleAdapter

        list= (List<Map<String, Object>>) getData();
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,list,R.layout.activity_simpleadapter_item
        ,new String[]{"description","image"},new int[]{R.id.ListViewDesc,R.id.ListViewimage});

        listView.setAdapter(simpleAdapter);
        //3.选中切换颜色
        //设置单选模式
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //设置监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void  onItemClick(AdapterView<?> parent, View view, int position, long id){
               listView.setItemChecked(position,true);
               //选中的信息
                String name=(String)list.get(position).get("description");
                int img =(Integer) list.get(position).get("image");

                // 1. 加载Toast的自定义布局（必须用LayoutInflater从xml加载）
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View toastView = inflater.inflate(R.layout.layouttoast, null); // 加载独立布局
               //2.从加载的toastView中查找控件
                TextView textView = toastView.findViewById(R.id.ToastText);
                ImageView imageView = toastView.findViewById(R.id.ToastImage);
                textView.setText("选中了 "+name);
                imageView.setImageResource(img);
                //3.
                Toast toast = new Toast(MainActivity.this);
               toast.setView(toastView);
               toast.setDuration(Toast.LENGTH_SHORT);
               toast.show();

               // toast.makeText(MainActivity.this, "选中：" + name, Toast.LENGTH_SHORT).show();
              //这里this指的是内部类对象

                //实现点击发送通知
                sendNotification(list.get(position), position);//postion作为通知的标识


            }
        });



    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
        );//默认重要性
        notificationManager.createNotificationChannel(channel);
    }

    // 构建并发送通知
    private void sendNotification(Map<String,Object> map, int notificationId) {
        String desc = map.get("description").toString();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher) // 应用图标作为通知图标
                .setContentTitle(desc) // 通知标题为选中的列表项名称
                .setContentText("选中了列表项：" + desc); // 通知内容
        Notification notification = builder.build();
        notificationManager.notify(notificationId, notification); // 13+ 发送通知需要permission
    }

    public List<? extends Map<String,Object>> getData(){
          List<HashMap<String,Object>> list=new ArrayList<>();
          HashMap<String,Object> map;

          for(int i=0;i<ImageArr.length;i++){
              map=new HashMap<>();
              map.put("description",names[i]);
              map.put("image", ImageArr[i]);
              list.add(map);
          }

      return list;

    }



}