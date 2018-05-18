部署备份一定要记得拷贝C盘下的访问量统计文件!!!
部署修改数据库的密码

获取最新数据（15条，可进入Algorithm配置`int e = 15;`，有重复过滤，系统已添加定时任务暂时未使用）
_http://localhost:8080/xmind/set/getNews_

重新获取所有数据（空表测试使用）为官网的默认1k条数据
_localhost:8080/xmind/news/getContent_

获取现有本地idname（由web scraper插件抓取，保存在1.xlsx）的所有远程数据（带重复过滤）
_localhost:8080/xmind/set/getNewsByIdName_

所有本地数据列表
_localhost:8080/xmind/news/content_

注:
①idea启动项目需要配置加载mapper的xml文件, 具体配置在pom包的build标签下
eclipse启动则不需要
②编译工具idea/eclipse跑项目需使用管理员身份启动,因为网站访问量目录在C盘下需要管理员身份

2018/3/1
修改新的抓取规则, 代码优化

2017/12/21
需要重新部署, 加入了一个异常捕获

![](https://github.com/jwwam/xmind/blob/master/src/main/webapp/source/images/shareData.png)

#联系QQ:824247231
