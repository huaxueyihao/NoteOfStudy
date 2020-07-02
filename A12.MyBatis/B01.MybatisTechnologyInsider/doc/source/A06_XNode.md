### XNode

#### 1 简介

>  节点解析对象，是对org.w3c.dom.Node对象做了封装和解析。


#### 2 类


##### 2.1 字段

```

// org.w3c.dom.Node对象
private final Node node;
// Node节点名称
private final String name;
// 节点的内容
private final String body;
// 节点属性集合
private final Properties attributes;
// mybtis-config.xml配置文件中<properties>节点下定义的键值对
private final Properties variables;
// XPathParser对象，
private final XPathParser xpathParser;


```

##### 2.2 方法

```

public XNode(XPathParser xpathParser, Node node, Properties variables) {
    this.xpathParser = xpathParser;
    this.node = node;
    this.name = node.getNodeName();
    this.variables = variables;
    this.attributes = parseAttributes(node);
    this.body = parseBody(node);
}

private Properties parseAttributes(Node n) {
    Properties attributes = new Properties();
    // 获取节点的属性集合
    NamedNodeMap attributeNodes = n.getAttributes();
    if (attributeNodes != null) {
      for (int i = 0; i < attributeNodes.getLength(); i++) {
        Node attribute = attributeNodes.item(i);
        // 使用PropertyParser处理每个属性中的占位符
        String value = PropertyParser.parse(attribute.getNodeValue(), variables);
        attributes.put(attribute.getNodeName(), value);
      }
    }
    return attributes;
}



private String parseBody(Node node) {
    String data = getBodyData(node);
    if (data == null) {
      // 当前结点不是文本节点，处理子节点
      NodeList children = node.getChildNodes();
      for (int i = 0; i < children.getLength(); i++) {
        Node child = children.item(i);
        data = getBodyData(child);
        if (data != null) {
          break;
        }
      }
    }
    return data;
}


 private String getBodyData(Node child) {
    if (child.getNodeType() == Node.CDATA_SECTION_NODE
        || child.getNodeType() == Node.TEXT_NODE) {
      String data = ((CharacterData) child).getData();
      data = PropertyParser.parse(data, variables);
      return data;
    }
    return null;
 }

```
