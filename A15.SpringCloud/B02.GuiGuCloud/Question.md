## 问题

> Q1:dependencyMannagement和dependencies的区别 ?

```

dependencyMannagement
用于父pom工程，能让子项目中用一个依赖不显示版本号，统一管理坐标版本号

dependencies
真正的引入依赖标签



```

> Q2.RunDashboard配置

```
在当前项目的.idea的文件中workspacex.xml中的<component name="RunDashboard">内添加如下内容

<option name="configurationTypes">
  <set>
    <option value="SpringBootApplicationConfigurationType"/>
  </set>
</option>


```

> Q3.健康检查的路径

```
http://localhost:8001/actuator/health

```

> Q4.zookeeper jar包冲突

```


排除
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
        </exclusion>
    </exclusions>
</dependency>

添加

<dependency>
    <groupId>org.apache.zookeeper</groupId>
    <artifactId>zookeeper</artifactId>
    <version>3.4.13</version>
</dependency>


```


> Q5: config-center 刷新问题

```

curl -X POST "http://localhost:3344/actuator/bus-refresh"

```

> Q6: rabbitmq启动

```

// 切换到MQ目录,注意你的安装版本可能不是3.7.4
cd /usr/local/Cellar/rabbitmq/3.7.4/
// 启用rabbitmq management插件
sudo sbin/rabbitmq-plugins enable rabbitmq_management


sudo vi /etc/profile
//加入以下两行
export RABBIT_HOME=/usr/local/Cellar/rabbitmq/3.7.4
export PATH=$PATH:$RABBIT_HOME/sbin
// 立即生效
source /etc/profile
 

// 后台启动
rabbitmq-server -detached  
// 查看状态
rabbitmqctl status 
// 访问可视化监控插件的界面
// 浏览器内输入 http://localhost:15672,默认的用户名密码都是guest,登录后可以在Admin那一列菜单内添加自己的用户
rabbitmqctl stop 关闭


```
