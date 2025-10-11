一、实验目标
  在Android Studio 上运行第一个程序 hello world !,并上传到自己的GitHub账号上。

二、实验步骤
  1 配置git 上传到GitHub
     
​      git init
     config添加  [user] 下面是创建连接 和选择分支（后面命令运行自动添加）

     
     github的用户名和邮箱情况
     
     
  <img width="1161" height="125" alt="image" src="https://github.com/user-attachments/assets/2831e86f-7df5-4143-997d-4d93fc88432b" />

    1.2. 链接远端仓库
      git remote add origin [http地址】
      
<img width="1009" height="697" alt="image" src="https://github.com/user-attachments/assets/b7f2b8be-aa6e-44e2-b066-a7e924943048" />

<img width="1225" height="69" alt="image" src="https://github.com/user-attachments/assets/6e2e4606-1a32-4d60-94d0-5c18f173f93a" />

    这里的名字竟然是`master`，我们还记得一开始我们创建的仓库里的唯一的分支名就是`main`，所以我们应该把文件提交到主分支上。或者需要更换分支（push到不存在的分支会自动创建分支的）

   1.3.更换分支
     git branch -a     查看分支情况
     git fetch   拉取远端仓库的分支        寻找new brand
     git checkout brandName      切换分支
      这里往往出现无法连接GitHub 的情况 ，即ping github.com失败的情况。 可以在系统host中添加GitHub 的ip地址，直接解析域名。

    1.4.上传到本地仓库
      git add .  将文件夹内的所有文件添加到缓冲区       还是 `git add + 此文件夹下的具体文件名` 只加入指定文件。
      git status     出现new file表示上传成功

      提交到本地仓库
      git commit -m "这是你的提交说明"           

     1.5.push到远端仓库
      git push -u origin brandName(自己看要push到那个分支)


三、实验问题



四、实验总结









1.Android Studio-----配置gradle 自己配置
2.核心原因是**Activity 未使用 Theme.AppCompat 或其后代主题**，导致 AppCompat 组件兼容性错误。在value/theme.xml中配置parent改为 AppCompat
3.github 无法ping 通 进入system32/driver/ect/host添加：
140.82.114.4    github.com
173.194.201.91  dl-ssl.google.com
64.233.177.101  groups.google.com
199.232.69.194  github.global.ssl.fastly.net
172.217.0.10    ajax.googleapis.com


