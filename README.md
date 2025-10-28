​              

# 1.SimpleAdapter实验

![image-20251021152122362](../../AppData/Roaming/Typora/typora-user-images/image-20251021152122362.png)

使用SimpleAdapter实现上述效果。

## 1.SimpleAdapter

实现页面布局，通过SimpleAdapter适配器来处理ListView显示的情况（ArrayAdapter等也可）。

 ListView 需要两个布局容器，主容器和子容器,根据页面情况，主容器采用垂直线性布局，子容器采用水平线性布局。

 主布局：

```
<ListView
    android:id="@+id/ListViewSimple"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

</ListView>
```



子布局：

```
 <TextView
        android:id="@+id/ListViewDesc"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_vertical"
        android:layout_weight="1"
        android:textSize="20sp"
        android:layout_marginLeft="10dp"
        android:text="@string/cat">

    </TextView>
<!--    既然无法使得图片靠右weigt=1 那么使得文字占比为1 再设计右间距-->

    <ImageView
        android:id="@+id/ListViewimage"
        android:layout_width="50dp"
        android:layout_height="50dp"
        android:layout_gravity="right|center_vertical"
        android:layout_marginRight="20dp"
        android:src="@drawable/cat">

    </ImageView>
```

 这里涉及子布局的布局问题，图片总是无法靠右，而是靠近文字（因为文字的容器TextView不大）；设置marginLeft也没用,marginRight难以控制距离大小。反其道而行，将文字布局填满再调整margin使得图片右靠齐，文字左靠齐。





## 2. data

SimpleAdapter 处理ListView

准备数据：

```
String[] names=new String[]{"Lion","Tiger","Monkey","Dog","Cat"};
int[] ImageArr={R.drawable.lion,R.drawable.tiger,R.drawable.monkey,R.drawable.dog,R.drawable.cat};
//数据
//将数据封装到List<Map>中
List<Map<String,Object>> list=new ArrayList<>();

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

```

在onCreate方法内：加入适配器 

![image-20251021153426998](../../AppData/Roaming/Typora/typora-user-images/image-20251021153426998.png)

```
//连接ListView
listView=(ListView) findViewById(R.id.ListViewSimple);//主布局
//加载数据为List<Map<>> getData()
//创建SimpleAdapter

list= (List<Map<String, Object>>) getData();
SimpleAdapter simpleAdapter=new SimpleAdapter(this,list,R.layout.activity_simpleadapter_item
,new String[]{"description","image"},new int[]{R.id.ListViewDesc,R.id.ListViewimage});

listView.setAdapter(simpleAdapter);
```



## 3. Toast

创建点击事件，配置Toast

设置监听事件，同时在drawble中配置selected的列的颜色情况。

list_item_selected.xml 

```
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- 选中状态：背景红色 -->
    <item android:state_activated="true" android:drawable="@color/red" />

    <!-- 默认状态 -->
    <item android:drawable="@android:color/white" />
</selector>
//同时将item的background设置为android:background="@drawable/list_item_selected"
```

这样我们点击列的时候，state_activated/checked根据点击情况来调整颜色的情况。



Toast

```
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
 //3.Toast属性信息
 Toast toast = new Toast(MainActivity.this);
toast.setView(toastView);
toast.setDuration(Toast.LENGTH_SHORT);
toast.show();
```

关于Toast的提示信息view,采用线性布局即可，"image+text“的形式 类比item即可。

## 4. Notifaction

配置通知，点击发送通知信息

 4.1 配置基础变量

```
// 通知相关：渠道ID、渠道名称、NotificationManager
private static final String CHANNEL_ID = "list_notification_channel";
private static final String CHANNEL_NAME = "列表项通知";
private NotificationManager notificationManager;
```

 4.2 channel

对于Android 8+   通知需要创建Channel

```
@RequiresApi(api = Build.VERSION_CODES.O) //设定特定版本才运行 8+
private void createNotificationChannel() {
    NotificationChannel channel = new NotificationChannel(
            CHANNEL_ID, //渠道id
            CHANNEL_NAME, //渠道name
            NotificationManager.IMPORTANCE_DEFAULT
    );//渠道重要性 默认重要性
    notificationManager.createNotificationChannel(channel);//系统通知服务注册渠道到系统中，完成渠道初始化
}
```

   4.3 发送通知

在点击的监听中，sendNotification(list.get(position), position); 方法用来发送通知，，参数分别为 list<map<>>中选中的数据项，position为选中的位置，作为通知的唯一标识

```
private void sendNotification(Map<String,Object> map, int notificationId) {
    String desc = map.get("description").toString();//获取数据
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher) // 应用图标作为通知图标
            .setContentTitle(desc) // 通知标题为选中的列表项名称
            .setContentText("选中了列表项：" + desc); // 通知内容
    Notification notification = builder.build();
    notificationManager.notify(notificationId, notification); // 13+ 发送通知需要permission
}
```

 4.4 permission

Android 13+发送通知需要许可

在AndroidMainfest.xml中配置许可：

```
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```



## 5. run  MainActivity

运行测试

<img src="../../AppData/Roaming/Typora/typora-user-images/image-20251021162007988.png" alt="image-20251021162007988" style="zoom:50%;" />

查看通知：

![image-20251021162045174](../../AppData/Roaming/Typora/typora-user-images/image-20251021162045174.png)



# 2.AlertDialog实验

##   1.AlertDialog

   基础的对话框，创建**AlertDialog.Builder**对象 ，通过set系列方法来设置对话框的布局情况，最后调用**create()**方法创建这个对象，再调用**show()**方法将对话框  显示出来。



 对于自定义的对话框，*我们可以调用setView()将我们的布局加载到 AlertDialog上*，来实现自定义的对话框布局。



alertdialog没有可以直接调用的实例方法（protected）来创建对象，通过Builder来创建实例对象

```
AlertDialog.Builder builder = new AlertDialog.Builder(this);
```

 可以通过setIcon/setTitle等方法来设置对话框的基本属性。

  alertdialog有三个按钮，通过setPositiveButton/setNegativeButton/setNeutralButton（中立按钮）来设置



对于自定义布局 通过setView来设置 如输入框/复选框



## 2.自定义布局

 对于实验的要求，我们要将登录页面设置在AlertDialog中，因此先创建login 页面

基础的情况 使用线性布局 布局标题，两个输入框和按钮行，按钮行继续采用线性布局-水平布局的情况布置按钮的分布。

![image-20251022163537938](../../AppData/Roaming/Typora/typora-user-images/image-20251022163537938.png)



## 3.setView

 加载自定义布局

```
//1.获取LayoutInflater，加载自定义布局
LayoutInflater inflater = LayoutInflater.from(this);
View dialogView = inflater.inflate(R.layout.activity_login, null); // 加载dialog_login.xml
```

创建AlertDialog对象 加入自定义布局

```
//2.创建AlertDialog.Builder
AlertDialog.Builder builder = new AlertDialog.Builder(this);
builder.setView(dialogView); // 关键：将自定义布局设置给对话框
builder.setCancelable(false); // 点击外部不关闭对话框
```

获取实例并显示

`Builder`的`create()`方法生成`AlertDialog`实例，再调用`show()`显示

```
//3 构建对话框实例
alertDialog = builder.create();
//4显示对话框
alertDialog.show();
```

## 4.run  AlertDialogTestActivity

运行结果

<img src="../../AppData/Roaming/Typora/typora-user-images/image-20251022163915297.png" alt="image-20251022163915297" style="zoom:50%;" />









# 3.菜单

## 1.menu

对于菜单 可分为选项菜单，上下文菜单和弹出菜单。

对于Options Menu,触发方式可以是：点击 ToolBar（或 ActionBar）右侧的「溢出按钮（⋮）」或者调用方法`openOptionsMenu()` 手动触发

对于选项菜单最重要的是两个方法：

- public boolean **onCreateOptionsMenu**(Menu menu)：调用OptionMenu，在这里完成菜单初始化
- public boolean **onOptionsItemSelected**(MenuItem item)：菜单项被选中时触发，这里完成事件处理



## 2.创建menu 的xml文件 

对于菜单的添加方法有：

  i 直接通过编写菜单XML文件，然后调用： **getMenuInflater().inflate(R.menu.menu_main, menu);**//R.menu.XXX 自己创建的menu文件

  ii 加载菜单 或者通过代码动态添加，onCreateOptionsMenu方法内add等来添加菜单项

```
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <!-- 字体大小（子菜单） -->
    <item
        android:id="@+id/action_font_size"
        android:title="字体大小">
        <menu>
            <item android:id="@+id/font_small" android:title="小" />
            <item android:id="@+id/font_medium" android:title="中" />
            <item android:id="@+id/font_large" android:title="大" />
        </menu>
    </item>

    <!-- 普通菜单项 -->
    <item
        android:id="@+id/action_common"
        android:title="普通菜单项" />

    <!-- 字体颜色（子菜单） -->
    <item
        android:id="@+id/action_font_color"
        android:title="字体颜色">
        <menu>
            <item android:id="@+id/color_black" android:title="黑色" />
            <item android:id="@+id/color_red" android:title="红色" />
        </menu>
    </item>

</menu>
```

对于xml文件，根标签<menu> 下有item子菜单项，同时子菜单内可以嵌套子菜单 <menu>

每一个item配置对应的id 作为唯一的菜单项标签。

预览效果：

<img src="../../AppData/Roaming/Typora/typora-user-images/image-20251028142436262.png" alt="image-20251028142436262" style="zoom:50%;" />



## 3.创建MenuActivity和事件触发处理

   测试文本在xml中TextView标签创建即可 配置对应的id(事件触发要先获取textview)和基础属性如内容/字体大小等等。

   

```
@Override
public boolean onOptionsItemSelected(@NonNull MenuItem item) {//非空
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
```

  根据选中的id的情况 选中对应的菜单项 对菜单项的内容进行事件处理。



## 4.测试  MenuActivity

​	![image-20251028143253507](../../AppData/Roaming/Typora/typora-user-images/image-20251028143253507.png)















# 4.上下文菜单

上下文菜单 使得我们长按某个View而可以出现菜单 ，进而对view进行”上下文操作“，如删除，复制等。

- ：重写onCreateContextMenu()方法  
- ：为view组件注册上下文菜单，使用registerForContextMenu()方法,参数是View
- ：重写onContextItemSelected()方法为菜单项指定事件监听器



## 1创建项目和布局文件

列表主布局

```
<ListView
    android:id="@+id/lv_list"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_marginTop="100dp"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintHorizontal_bias="0.0"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintVertical_bias="1.0" />
```



列表项布局

```
<!-- 图标（可替换为实际资源） -->
<ImageView
    android:id="@+id/iv_icon"
    android:layout_width="40dp"
    android:layout_height="40dp"
    android:src="@drawable/ic_android_icon" />

<!-- 文字内容 -->
<TextView
    android:id="@+id/context_text"
    android:layout_width="wrap_content"
    android:layout_height="match_parent"
    android:layout_marginStart="20dp"
    android:gravity="center_vertical"
    android:text="nihaode "
    android:textColor="@color/black"
    android:textSize="18sp" />
```

显示基本的页面情况



菜单布局

显示delete菜单

```
<item
    android:id="@+id/action_delete"
    android:icon="@drawable/ic_delete"
    android:title="删除"
    app:showAsAction="ifRoom" />
```



## 2.ContextMenuActivity



2.1 代码生成列表布局

这里使用SimpleAdapter

- 用 `initData()` 生成 `List<Map>` 格式的数据源（包含文字描述与图标）。
- 通过 `SimpleAdapter` 将数据源与列表项布局绑定，设置到 `ListView`。



2.2 ActionMode 监听列表项事件

- **长按事件**：启动 `ActionMode`，切换选中状态并更新操作栏标题为 " selected "
- **点击事件**：若已进入 `ActionMode`，切换选中状态；无选中项时关闭 `ActionMode`。



2.3ActionMode回调

ActionMode提供四种回调方法，定义了ActionMode的生命周期和交互逻辑。

`onCreateActionMode`：创建操作模式时调用，用于加载菜单资源（如 “删除” 按钮）。

`onPrepareActionMode`：菜单显示前调用，可动态更新菜单项状态（此处未使用，返回 `false`）。

onActionItemClicked`：点击操作栏中的菜单项时调用（如点击 “删除” 按钮，执行删除选中项逻辑）。

`onDestroyActionMode`：操作模式结束时调用，用于清理选中状态（如清空选中记录）。



2.4 批量删除与视图刷新

- 从后往前遍历 `SparseBooleanArray` 中的选中项，避免删除后索引移位（这里selectedItems 是自动升序的 因此从后往前删除即可）
- 重新创建 `SimpleAdapter` 并设置到 `ListView`（`SimpleAdapter` 无 `notifyDataSetChanged()`，需重新绑定）。



2.5 SparseBooleanArray

`SparseBooleanArray` 是 Android 提供的一个 **高效数据结构**，用于存储 “整数键（int）” 与 “布尔值（boolean）” 的映射关系，类似于 `HashMap<Integer, Boolean>`。这里我们存放的是position和boolean，即Listview的索引和选中情况。通常和ActionMode配合使用来完成列表项的批量操作功能。



## 3.测试 ContextMenuActivity

![image-20251028151802085](../../AppData/Roaming/Typora/typora-user-images/image-20251028151802085.png)



