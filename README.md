一、实验目标
  在Android Studio 上运行第一个程序 hello world !,并上传到自己的GitHub账号上。

二、实验步骤
  1 配置git 上传到GitHub
     1.1.创建git文件并修改git文件中的config
​      git init
     config添加  [user] 下面是创建连接 和选择分支（后面命令运行自动添加）
     github的用户名和邮箱情况

<img width="907" height="113" alt="image" src="https://github.com/user-attachments/assets/52fb47d6-d528-4bc8-8727-16cce39c7d77" />




1.2. 链接远端仓库
  git remote add origin [http地址】

<img width="1132" height="599" alt="image" src="https://github.com/user-attachments/assets/0a775444-6b1c-48f9-876a-f98259ab3e15" />
<img width="1240" height="86" alt="image" src="https://github.com/user-attachments/assets/ef7c5c35-97e1-46a7-9ace-ca2472330773" />


这里的名字竟然是`master`，我们还记得一开始我们创建的仓库里的唯一的分支名就是`main`，所以我们应该把文件提交到主分支上。或者需要更换分支（push到不存在的分支会自动创建分支的）



   1.3.更换分支
     git branch -a     查看分支情况
     git fetch   拉取远端仓库的分支        寻找new brand
     git checkout brandName      切换分支
      这里往往出现无法连接GitHub 的情况 ，即ping github.com失败的情况。 可以在系统host中添加GitHub 的ip地址，直接解析域名。



​    1.4.上传到本地仓库

​      git add .  将文件夹内的所有文件添加到缓冲区       还是 `git add + 此文件夹下的具体文件名` 只加入指定文件。
​      git status     出现new file表示上传成功

​     提交到本地仓库
​     git commit -m "这是你的提交说明"           



​    1.5.push到远端仓库
​    git push -u origin brandName(自己看要push到那个分支)



2.第一个Android程序

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:background="@color/white">

    <!-- 文本控件，显示Hello World -->
  <TextView

           android:id="@+id/helloText"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@string/hw"
           android:textSize="24sp"
           android:textColor="@color/black"/>

 </LinearLayout>
```
<img width="652" height="1295" alt="image" src="https://github.com/user-attachments/assets/a44ac9f3-dad1-4f3f-b47c-3700f97eeec4" />




3.根据1的步骤在项目文件 中操作，开始上传到experiment_1分支





三、实验问题

1. Android Studio 手动配置 Gradle

在 Android Studio 中，可根据项目需求手动配置 Gradle。手动下载gradle8.13 在设置上配置，避免错误

​     <img width="1222" height="908" alt="image" src="https://github.com/user-attachments/assets/47b7f1fe-3846-43e4-ac86-48cc69050a04" />


 2.解决 AppCompat 组件兼容性错误

 问题原因

​    Activity 未使用 `Theme.AppCompat` 或其后代主题，导致 AppCompat 组件出现兼容性错误。

 解决：

​    在项目的 `values/theme.xml` 文件中，将主题的 `parent` 属性修改为 `AppCompat` 相关主题，示例如下：

```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <style name="Theme.MyApplication" parent="Theme.AppCompat" />
</resource
```



3.无法 ping 通 GitHub 相关域名，可能是由于 DNS 解析问题导致。

### 解决方法

1. 进入路径：`C:\Windows\System32\drivers\etc`（注意：需以管理员权限操作）

2. 打开 `hosts` 文件（可用记事本或其他文本编辑器）--管理员模式

3. 添加以下内容并保存：

   ```plaintext
   140.82.114.4 github.com
   173.194.201.91 dl-ssl.google.com
   64.233.177.101 groups.google.com
   199.232.69.194 github.global.ssl.fastly.net
   172.217.0.10 ajax.googleapis.com
   ```

   

4. 保存后，重新尝试访问 GitHub 即可。



四、实验总结

本次实验不仅是 Android 开发与 Git 的入门，更重要的是建立了 “探索-试错” 的解决问题的方法。通过解决实际问题（如 Gradle 配置、GitHub 连接），学习GitHub的上传流程和基础Android开发的基本情况。







