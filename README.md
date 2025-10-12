

# 一、实验目标

- 掌握Android中常用布局方式（ConstraintLayout、LinearLayout、TableLayout）的基本用法
- 学会使用Android官方文档进行学习和参考
- 能够根据需求选择合适的布局方式实现界面设计
- 理解不同布局方式的适用场景和特点



# 二、实验步骤

## 1.**实现线性布局**

  基本思路：使用LinearLayout实现指定界面布局，使用嵌套的垂直和水平排列方式

  采用 **垂直方向的 LinearLayout 作为根容器**，**内部嵌套 4 个水平方向的 LinearLayout，**每个水平容器包含 4 个按钮，形成 “4 行 4 列” 的按钮网格。整体背景为黑色，按钮文字为白色，按钮背景引用自定义边框资源 `btn_border`，核心用途是实现一个规整的矩阵。

​    显示器选择411宽度，四个水平容器设置不同的width   同时设置等高的height=50dp 实现错位布局情况

```
android:layout_width="xxdp"
android:layout_height="xxdp"
```





## 2.**实现表格布局**

   基本思路：使用TableLayout实现指定界面布局，根据页面来设计行、列和单元格的布局，使用TableRow的嵌套TextView

   整体布局

​         根布局为 `TableLayout`（表格布局）：

​           尺寸：`layout_width="match_parent"`（宽度占满屏幕）、`layout_height="match_parent"`（高度占满屏幕），适配不同屏幕尺寸；

​           背景：`android:background="@color/black"`（纯黑色背景）；

​           列数：默认固定为 **2 列**（左列：功能名称，右列：对应快捷键），通过 `TableRow`（表格行）承载每一行内容，用 `View` 分割线划分功能区域。

```
<!--  Open行 -->
<TableRow>
    <TextView
        android:text="Open..."
        android:layout_marginLeft="15dp"
        android:padding="5dp"/>

    <TextView
        android:layout_width="wrap_content"
        android:layout_marginLeft="200dp"
        android:padding="5dp"
        android:text="Ctrl-O" />
</TableRow>
```

以open示例 其余逻辑基本一致。

​    `TableRow`，每一行对应一个 “可操作的文件功能”，结构统一为 “左列功能名 ， 右列快捷键”：

左列（功能名称）控件，配置统一：

​      文字：`Open...`（打开）

​      间距：`layout_marginLeft="15dp"`（左侧外边距，让功能名整体右移，形成缩进）、`padding="5dp"`（内边距，优化文字点击区域和视觉留白）；

右列（快捷键）控件，配置统一：

​      文字： `Ctrl-O`...

​       对齐：android:layout_marginLeft="200dp"  设置左边距  使得快捷键往右侧靠拢。



​      

## 3.实现约束布局（一）

   基本思路：使用ConstraintLayout实现指定界面布局，使用约束关系的设置方法

​     3.1标题栏（tvTitle)

​                  TextView（文本标签） 

​                 展示布局标题，标识 “计算器测试” 主题

​      关键配置

​           顶部对齐父布局顶部（`app:layout_constraintTop_toTopOf="parent"`），顶部外边距 `30dp`（`android:layout_marginTop="30dp"`）；

​            左侧对齐父布局左侧（`app:layout_constraintStart_toStartOf="parent"`），确保标题居左显示。





​      3.2  输入提示（tvInput）

​              TextView（文本标签）

​              提示用户 “当前为输入状态”，属于功能性提示文本

​    关键配置

| `layout_width`  | `wrap_content`（宽度自适应 “Input” 文本） |
| --------------- | ----------------------------------------- |
| `layout_height` | `wrap_content`（高度自适应）              |

​       顶部对齐标题栏底部（`app:layout_constraintTop_toBottomOf="@+id/tvTitle"`），顶部外边距 `20dp`（与标题栏保持间距）；

​       左侧对齐父布局左侧（`app:layout_constraintStart_toStartOf="parent"`），与标题栏左对齐，视觉统一。







​     3.3 计算显示区（tvDisplay）

​                 TextView（文本标签，模拟计算器显示屏）

​                 展示用户输入的数字、运算符及计算结果

- 关键配置
  -   顶部对齐输入提示底部（`app:layout_constraintTop_toBottomOf="@+id/tvInput"`），顶部外边距 `44dp`（与提示区保持间距）；
  -   左侧对齐父布局左侧（`app:layout_constraintStart_toStartOf="parent"`）、右侧对齐父布局右侧  （ `app:layout_constraintEnd_toEndOf="parent"`），实现 “全屏宽度” 显示；
  - ` layout_constraintHorizontal_bias="0.0"`（水平偏移为 0，确保左右约束生效时无偏移）。





3. 4  功能按钮矩阵（共 5 行 4 列，16 个按钮）

​       所有按钮均为 `Button` 控件，风格统一（`backgroundTint="@color/weakDark"` 弱深色背景、`textColor="@color/black"` 黑色文本），采用 **`app:layout_constraintWidth_percent="0.25"`** 实现 “4 列等宽”（每列占屏幕宽度 25%）.

​           约束：调整constaintEnd/Start/Top等等来实现布局优化。

​         使用Android Studio的页面展示来手动拉动组件，自动编写代码地实现页面情况

```
<Button
    android:id="@+id/btn7"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_marginTop="76dp"
    android:backgroundTint="@color/weakDark"
    android:text="7"
    android:textColor="@color/black"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@+id/tvDisplay"
    app:layout_constraintWidth_percent="0.25" />      
```









## 4.实现约束布局（二）

  基本思路：使用ConstraintLayout实现指定界面布局，从群文件下载所需图片资源，以及处理图片资源的引用和适配

  前提准备：将图片导入drawable中，配置对应的name

  整体布局：

​        4.1   首行使用线性布局嵌套 将三个图片和文字描述水平布局。在线性布局Image和Text 实现垂直布局。

```
<LinearLayout
    android:layout_width="141dp"
    android:layout_height="wrap_content"
    android:layout_margin="2dp"
    android:gravity="center"
    android:orientation="vertical">

    <ImageView
        android:layout_width="40dp"
        android:layout_height="40dp"
        android:src="@drawable/ic_space_station" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="5dp"
        android:text="Space Stations"
        android:textSize="15sp" />
</LinearLayout>
```

内嵌的线性布局 宝航ImageView和TextView       根据手动拉取组件自动布局



​         4.2  DCA + MARS + 箭头

​      定义两个TextView ，后定义ImageView

​    注意约束  DCA约束到topNav（第一行导航栏）     MARS约束到DCA        

​      ImageView要约束到两个TextView  ：



​        水平方向：左侧对齐 DCA 右侧、右侧对齐 MARS 左侧（`app:layout_constraintStart_toEndOf="@+id/tvDCA"`/`End_toStartOf="@+id/tvMARS"`），确              保在两标签中间；

​       垂直方向：顶部对齐 DCA 顶部、底部对齐 DCA 底部（`app:layout_constraintTop_toTopOf="@+id/tvDCA"`/`Bottom_toBottomOf="@+id/tvDCA"`），确保垂直居中；





​    4.3 其余部分

下面的组件基本一致，完善组件之间的约束，定义好组件的属性（如颜色 文字还是图片等等），通过建立约束，手动拉取组件移动置合适的位置即可。















# 三、实验问题

 3.1 线性布局  没有边框

​       自定义btn_border.xml 

```
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <stroke
        android:width="1dp"
        android:color="@color/white" />
    <solid
        android:color="@color/black" />
    <corners
        android:radius="0dp" />
</shape>
```

​     设置solid 底色  stroke配置边框颜色





3.2text 显示都是大写   

​       在theme中       <item name="android:textAllCaps">false</item> <!-- 全局禁用文本全大写 --> 全局禁用全文本大写



3.3  表格布局 margin padding 的区别

margin：  控制**当前控件与外部元素（父布局、相邻控件）之间的距离**，是 控件之外 的空白区域。

padding:  控制**控件内部内容（文字、子控件、图片）与控件自身边界之间的距离**，是 控件之内的空白区域。









# 四、实验总结

   通过本次Android界面布局实验，我学习了Android中三种常用布局方式：LinearLayout、TableLayout和ConstraintLayout。实验过程中，我们不仅掌握了这些布局的基本使用方法。

- LinearLayout 适合做简单的线性排列（比如按钮网格、列表项），但嵌套多了会卡顿，这次线性布局嵌套了 2 层（垂直根布局 + 水平子布局），运行还比较流畅，要是嵌套更多可能就需要优化了。

- TableLayout 适合做表格类界面（比如功能菜单、数据列表），用`layout_span`跨列很方便，但灵活性不够，比如想让某一行有 3 列就比较麻烦，不如 ConstraintLayout 灵活。

- ConstraintLayout 是最灵活的，通过约束关系能实现复杂界面（比如计算器、太空旅行界面），尤其是`layout_constraintWidth_percent`属性，能让按钮等宽适配不同屏幕，比 LinearLayout 的 weight 更精准。

  

​    在实验中，我们遇到了布局约束设置、多屏幕适配等常见问题，通过查阅官方文档和AI工具，成功解决了这些问题。特别是ConstraintLayout的使用，让我们对  Android界面设计有了更深入的理解。

 



