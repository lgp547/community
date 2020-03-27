## 社区项目学习

## 资料
[Spring 文档](https://spring.io/guides)    
[Spring Web](https://spring.io/guides/gs/serving-web-content/)   
[Spring MVC 开发指南](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-handlermapping-interceptor)
[Spring Boot 开发指南](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#boot-features-embedded-database-support)    
[MyBatis Generator 逆向工程](http://mybatis.org/generator/) 
[mybatis-spring-boot-autoconfigure 自动配置](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)
[maven镜像查询]()

[es](https://elasticsearch.cn/explore)    
[Github deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)    
[Bootstrap 前端样式](https://v3.bootcss.com/getting-started/)    
[Github OAuth 授权](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)    
[菜鸟教程](https://www.runoob.com/mysql/mysql-insert-query.html)    
[Thymeleaf jsp网页](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values)    
[Spring Dev Tool 热部署](https://docs.spring.io/spring-boot/docs/2.0.0.RC1/reference/htmlsingle/#using-boot-devtools)  
 
[Markdown 插件](http://editor.md.ipandao.com/)   
[UFfile SDK](https://github.com/ucloud/ufile-sdk-java)  
[Count(*) VS Count(1)](https://mp.weixin.qq.com/s/Rwpke4BHu7Fz7KOpE2d3Lw)  

## 工具
[Git](https://git-scm.com/download)   
[Visual Paradigm uml建模](https://www.visual-paradigm.com)    
[Flyway](https://flywaydb.org/getstarted/firststeps/maven)  
[Lombok](https://www.projectlombok.org)    
[ctotree github插件](https://www.octotree.io/)   
[Table of content sidebar 插件](https://chrome.google.com/webstore/detail/table-of-contents-sidebar/ohohkfheangmbedkgechjkmbepeikkej)    
[One Tab 插件](https://chrome.google.com/webstore/detail/chphlpgkkbolifaimnlloiipkdnihall)    
[Live Reload 插件](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei/related)  
[Postman 测试接口的插件](https://chrome.google.com/webstore/detail/coohjcphdfgbiolnekdpbcijmhambjff)
[ApiPost 替换上一个的](https://www.apipost.cn/)

## 脚本
```sql
CREATE TABLE USER
(
    ID int  AUTO_INCREMENT PRIMARY KEY NOT NULL,
    ACCOUNT_ID varchar(100),
    NAME varchar(50),
    TOKEN varchar(36),
    GMT_CREATE bigint,
    GMT_MODIFIED bigint
);


```bash
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```

## 更新日志
- 2019-7-30 修复 session 过期时间很短问题   
- 2019-8-2 修复因为*和+号产生的搜索异常问题  
- 2019-8-18 添加首页按照最新、最热、零回复排序  
- 2019-8-18 修复搜索输入 ? 号出现异常问题
- 2019-8-22 修复图片大小限制和提问内容为空问题
- 2019-9-1 添加动态导航栏