### XPathParser

#### 1 简介

> 解析mybatis-config.xml和mapper.xml文件的解析器


#### 2 类


##### 2.1 字段

```

// Document对象
private final Document document;
// 是否开启验证
private boolean validation;
// 用于加载本地DTD文件
private EntityResolver entityResolver;
// mybatis-config.xml中<properties>标签定义的键值对集合
private Properties variables;
// XPath对象
private XPath xpath;


```

##### 2.2 构造方法

```

public XPathParser(String xml) {
    commonConstructor(false, null, null);
    this.document = createDocument(new InputSource(new StringReader(xml)));
}


private void commonConstructor(boolean validation, Properties variables, EntityResolver entityResolver) {
    this.validation = validation;
    this.entityResolver = entityResolver;
    this.variables = variables;
    XPathFactory factory = XPathFactory.newInstance();
    this.xpath = factory.newXPath();
}


private Document createDocument(InputSource inputSource) {
    // important: this must only be called AFTER common constructor
    try {
      // 创建DocumentBuilder对象并进行配置
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      factory.setValidating(validation);

      // 对DocumentBuilderFactory对象进行一系列配置(略)
      factory.setNamespaceAware(false);
      factory.setIgnoringComments(true);
      factory.setIgnoringElementContentWhitespace(false);
      factory.setCoalescing(false);
      factory.setExpandEntityReferences(true);

      // 创建DocumentBuilder对象并进行配置
      DocumentBuilder builder = factory.newDocumentBuilder();
      // 设置EntityResolver接口对象
      builder.setEntityResolver(entityResolver);
      builder.setErrorHandler(new ErrorHandler() {
        @Override
        public void error(SAXParseException exception) throws SAXException {
          throw exception;
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
          throw exception;
          // ... 其中实现的ErrorHandler接口的方法都是空实现
        }

        @Override
        public void warning(SAXParseException exception) throws SAXException {
        }
      });
      return builder.parse(inputSource);
    } catch (Exception e) {
      throw new BuilderException("Error creating document instance.  Cause: " + e, e);
    }
}

```



##### 2.3 eval

```

public String evalString(Object root, String expression) {
    String result = (String) evaluate(expression, root, XPathConstants.STRING);
    result = PropertyParser.parse(result, variables);
    return result;
}

```









        
