### TokenHandler

#### 1 简介

>  占位符解析处理器


#### 2 类(Interface)

##### 2.1 类结构

```

TokenHandler
    ParameterMappingTokenHandler in SqlSourceBuilder (org.apache.ibatis.builder)
    VariableTokenHandler in PropertyParser (org.apache.ibatis.parsing)
    DynamicCheckerTokenParser in TextSqlNode (org.apache.ibatis.scripting.xmltags)
    BindingTokenParser in TextSqlNode (org.apache.ibatis.scripting.xmltags)

```

> 四个实现类，类似策略模式
