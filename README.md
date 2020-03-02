> 之前用SpringBoot+Thymeleaf写了[个人博客](https://blog.csdn.net/qq_40866897/article/details/98040475)，这次又来炒冷饭，用新的技术来重构个人博客，并把它变成开放式的，目前水平有限，欢迎大家一起讨论提建议

为啥叫它轻博客，这是临时想的名字，因为目前它的功能没有那么多，很简单的一些功能，为了不太Low就取个比较清新的叫法，之前是想叫VBlog，但是后面发现有大佬已经写了个叫VBlog的了，还是别跟人家重名好了。。。

本次使用的技术有：
**前端**
 - Vue
 - Vuex
 - Vue-Router
 - axios
 - elementUI
 
 Vue是刚学，所以这次就用了Vue+elementui来做前端

**后端**

 - SpringBoot
 - Mybatis
 - SpringSecurity+JWT
 - Redis
 - ElasticSearch
 - MySQL
 - Quartz
 - Nginx

这次跟上次不同的地方有几点，加入了ES做文章搜索，使用JWT来作登录验证，没有使用PageHelper分页插件而是直接用Redis进行分页，Nginx做前后端分离部署和代理

这次还采用了类RestFul的Api接口，为啥是类RestFul，因为我觉得对RestFul的理解不够，导致这次接口写的跟RestFul不太像，哈哈.....

没图说个**

**首页**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200301225644679.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwODY2ODk3,size_16,color_FFFFFF,t_70)

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020030122572324.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwODY2ODk3,size_16,color_FFFFFF,t_70)
**登录**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200301234826959.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwODY2ODk3,size_16,color_FFFFFF,t_70)
**注册**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200301234842828.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwODY2ODk3,size_16,color_FFFFFF,t_70)
**标签**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200301234912606.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwODY2ODk3,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200301234928990.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwODY2ODk3,size_16,color_FFFFFF,t_70)
**详情页**
![**在这里插入图片描述**](https://img-blog.csdnimg.cn/20200301235047541.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwODY2ODk3,size_16,color_FFFFFF,t_70)
后面会写个配套的小程序，慢慢来
**评论区**
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020030123515273.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQwODY2ODk3,size_16,color_FFFFFF,t_70)
后面会详细介绍在写的过程中遇到的坑，毕竟遇到的还不少....

还有管理后台正在写，跟这个配套使用，应该马上就能写完。