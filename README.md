1.Android Studio-----配置gradle 自己配置
2.核心原因是**Activity 未使用 Theme.AppCompat 或其后代主题**，导致 AppCompat 组件兼容性错误。在value/theme.xml中配置parent改为 AppCompat
3.github 无法ping 通 进入system32/driver/ect/host添加：
140.82.114.4    github.com
173.194.201.91  dl-ssl.google.com
64.233.177.101  groups.google.com
199.232.69.194  github.global.ssl.fastly.net
172.217.0.10    ajax.googleapis.com
<img width="527" height="401" alt="image" src="https://github.com/user-attachments/assets/2e5fce2b-d617-43f4-81ed-2e3a3cf7ebb2" />

