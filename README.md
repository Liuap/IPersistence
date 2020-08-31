# IPersistence
持久层的简单实现，参考Mybatis,可被插件识别
入口：IPersistence_Test下的Test

##1.流程
### 1. 将配置文件读取为文件输入流 com.pal.io.Resources
### 2. 解析与封装后返回SqlSessionFactory对象 com.pal.sqlSession.SqlSessionFactoryBuilder
### 3. sqlSession定义CURD并调用Executor具体执行 com.pal.sqlSession.SqlSession
```java
//核心逻辑实现：


```


