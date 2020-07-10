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
