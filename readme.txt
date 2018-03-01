部署备份一定要记得拷贝C盘下的访问量统计文件!!!

获取最新数据（15条，可进代码配置）
http://localhost:8080/xmind/set/getNews

重新获取所有数据（慎用）为官网的1k条
localhost:8080/xmind/news/getContent

获取现有本地idname（由web scraper插件抓取，保存在1.xlsx）的所有远程数据（带重复过滤）
localhost:8080/xmind/set/getNewsByIdName

所有本地数据列表
localhost:8080/xmind/news/content

注:
①idea启动项目需要配置加载mapper的xml文件, 具体配置在pom包的build标签下
eclipse启动则不需要
②编译工具idea/eclipse跑项目需使用管理员身份启动,因为网站访问量目录在C盘下需要管理员身份

2017/12/21
需要重新部署, 加入了一个异常捕获