## 社区交流平台项目

## 知识点
### 进行GitHub授权登陆
- 先创建一个OAuth应用，用来做客户端的id
- 授权OAuth应用
    - OAUTH协议为用户资源的授权提供了一个安全的、开放而又简易的标准。与以往的授权方式不同之处是OAUTH的授权不会使第三方触及到用户的帐号信息（如用户名与密码），即第三方无需使用用户的用户名与密码就可以申请获得该用户资源的授权，因此OAUTH是安全的。
- GitHub授权OAuth应用原理：
    1. 重定向用户以请求其GitHub身份(需要填写相应的信息进行请求，返回code和state)
    2. GitHub将用户重定向回您的站点(将第一步返回加上client_id和client_secret再次向GitHub请求，以次返回一个access_token)
    3. 您的应用使用用户的访问令牌访问API(利用access_token得到登陆用户的信息)

- 实现需要用到的工具：
    - OkHttp的get url 和post service [okhttp官网](https://square.github.io/okhttp/)
    - 还有把对象转json和json转为对象用到(Fastjson，需添加mvn)

### 使用cookies进行登陆验证
- http请求时无状态的，要验证是否登陆过，可以使用cookies。
    1. session是存储在浏览器的，是个Map，存储各种数据
    2. cookies是存储在浏览器的，浏览器进行请求就会携带者cookies进行访问。
    3. 要怎么保持登陆，就是使用Session验证cookies是否一样

### 使用MyBatis进行数据库操作
- 参考文档：[mybatis-spring-boot-autoconfigure 自动配置](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)
- 步骤：
    - 添加依赖
    - MyBatis将会自动检查现有的数据库连接池
    - Spring有默认提供数据库连接池，参考[SpringBoot功能里的数据库](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/html/spring-boot-features.html#boot-features-sql)
        - 需要添加依赖实现连接池
        - 并且也要声明以下配置绑定数据库
```html
spring.datasource.url=jdbc:mysql://localhost/test
spring.datasource.username=dbuser
spring.datasource.password=dbpass
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```
- 再接编写Mapper类

### 集成Flyway migration
- 官网：[Flyway](https://flywaydb.org/getstarted/firststeps/maven)
- 步骤：
    - 添加maven依赖
    - 按照格式创建sql语句
    - 执行mvn flyway:migrate
    - 所有的数据库操作都需经过flyway进行操作

### 逆向工程（MyBatis generator）
* 官网参考：[MyBatis Generator 逆向工程](http://mybatis.org/generator/) 
* 特点：不用自己来写sql语句，只需传递正确即可。
* 步骤：添加依赖
```xml
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.4.0</version>
    <dependencies>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>1.4.200</version>
        </dependency>
    </dependencies>
</plugin>
```
* 新创建generatorConfig.xml
    * 主要是这句 <table tableName="user" domainObjectName="User" ></table>
                <table tableName="question" domainObjectName="Question" ></table>
* 运行脚本语句
    * mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
    
### 异常页面处理（通过Spring Boot开发文档搜Whitelabel）
实现步骤：
- 先是定义控制类，在advice包中
```java
@ControllerAdvice
public class CustomizeExceptionHandler {
    //这个是指定捕抓那些异常
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model) {
        //instanceof是判断e是否属于CustomizeException类
        if (e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        } else {
            model.addAttribute("message","服务冒烟了，要不等会再试试");
        }
        return new ModelAndView("error");
    }
}
```
- 因要自定义异常类CustomizeException，所以有了exception包下的三个类
    * 先枚举定义好了，通过接口给CustomizeException调用。
    * 使用的时候就可以直接：throw new CustomizeException(CustomizeErrorCode.QUESTIOM_NOT_FOUND);

- 如果还需要对其他4XX、5XX这些错误页面进行捕捉，见CustomizeErrorController类

### 事务
- @Transactional事务注解，当服务器出错了，会自动回滚，不写入数据库
- 事务是恢复和并发控制的基本单位。
- 事务应该具有4个属性：原子性、一致性、隔离性、持久性。这四个属性通常称为ACID特性。
    - 原子性（atomicity）。一个事务是一个不可分割的工作单位，事务中包括的操作要么都做，要么都不做。
    - 一致性（consistency）。事务必须是使数据库从一个一致性状态变到另一个一致性状态。一致性与原子性是密切相关的。
    - 隔离性（isolation）。一个事务的执行不能被其他事务干扰。即一个事务内部的操作及使用的数据对并发的其他事务是隔离的，并发执行的各个事务之间不能互相干扰。
    - 持久性（durability）。持久性也称永久性（permanence），指一个事务一旦提交，它对数据库中数据的改变就应该是永久性的。接下来的其他操作或故障不应该对其有任何影响。
- 事务并发问题
    - 脏读：事务A读取了事务B更新的数据，然后B回滚操作，那么A读取到的数据是脏数据。（假数据）
    - 不可重复读：(主要针对某行数据和修改操作)当事务A第一次读取事务后,事务B对事务A读取的数据进行修改,事务 A 中再次读取的数据和之前读取的数据不一致
    - 幻读：(主要针对新增和删除)事务A按照特定条件查询出结果,事务B新增了一条符合条件的数据.事务 A 中查询的数据和数据库中的数据不一致的,事务 A 好像出现了幻觉,这种情况称为幻读.
    - 总结：解决不可重复读的问题只需锁住满足条件的行，解决幻读需要锁表
- MySQL事务隔离级别

事务隔离级别 | 脏读 | 不可重复读 | 幻读
-|-|-|-
读未提交(read-uncommitted)  | √ | √ | √ 
读已提交(read-commit)       | × | √ | √
可重复读(repeatable-read)   | × | × | √
串行化(serializable)        | × | × | ×
1. 读未：As450 Bu-50 As400 Brollback Au-50 As400 出现脏读
2. 读已：As450 Bu-50 As450 Bcommit As400 出现不可重复读
3. 可重：As450 Bu-50 Bcommit As450 Au-50 As350 (说明查询不会去事务B读，增删改会对事务B进行同步)  Binter As(没有查询到，出现幻读)
4. 串行化：打开事务A进行查询，此时事务B不能插入记录。(并发性极低，少用)
- 补充：
    - 事务隔离级别为读提交时，写数据只会锁住相应的行
    - 事务隔离级别为串行化时，读写数据都会锁住整张表
    - 隔离级别越高，越能保证数据的完整性和一致性，但是对并发性能的影响也越大。
    
### 添加富文本
- 插件网站：[markdown](http://editor.md.ipandao.com/)
- 实现步骤：下载并添加进来，接着编写前端页面即可。


## 用到的资料和工具网站
### 开发的官方资料
- [Spring 官网](https://spring.io/guides)    
- [Spring 入门指南](https://spring.io/guides/gs/serving-web-content/)   
- [Spring 框架参考文档](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/index.html)
- [Spring MVC 开发指南](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc)
- [Mybatis 官网](https://mybatis.org/mybatis-3/)
- [Spring Boot 参考文档](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/reference/html/)   
- [mybatis-spring-boot-autoconfigure 自动配置](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)
- [MyBatis Generator 逆向工程](http://mybatis.org/generator/) 

### 查询网站
- [菜鸟教程](https://www.runoob.com/mysql/mysql-insert-query.html)  
- [es 中文社区](https://elasticsearch.cn/explore) 
- [maven镜像查询](https://mvnrepository.com) 
- [Bootstrap 前端样式](https://v3.bootcss.com/getting-started/)    
- [Thymeleaf jsp网页](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values)    
- [Github OAuth 授权](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)    
- [Github deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)

### 开发工具
- [Git](https://git-scm.com/download) 
- [Flyway](https://flywaydb.org/getstarted/firststeps/maven)  
- [Lombok](https://www.projectlombok.org)  
- [Markdown 插件](http://editor.md.ipandao.com/)   
 
### 浏览器插件
- [ctotree github](https://www.octotree.io/)   
- [Table of content sidebar](https://chrome.google.com/webstore/detail/table-of-contents-sidebar/ohohkfheangmbedkgechjkmbepeikkej)    
- [One Tab ](https://chrome.google.com/webstore/detail/chphlpgkkbolifaimnlloiipkdnihall)    
- [Live Reload](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei/related)  
- [Postman 测试接口的插件](https://chrome.google.com/webstore/detail/coohjcphdfgbiolnekdpbcijmhambjff)
- [ApiPost 测试接口的插件](https://www.apipost.cn/)



