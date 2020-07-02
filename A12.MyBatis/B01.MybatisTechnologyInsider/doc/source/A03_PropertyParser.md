### PropertyParser

#### 1 简介

> 属性解析器，解析<properties>标签


#### 2 类



```

public class PropertyParser {

  // 
  private static final String KEY_PREFIX = "org.apache.ibatis.parsing.PropertyParser.";
  /**
   * The special property key that indicate whether enable a default value on placeholder.
   * <p>
   *   The default value is {@code false} (indicate disable a default value on placeholder)
   *   If you specify the {@code true}, you can specify key and default value on placeholder (e.g. {@code ${db.username:postgres}}).
   * </p>
   * @since 3.4.2
   */
  // 在mybatis-config.xml中<properties>节点下配置是否开启默认值功能的对应配置项
  public static final String KEY_ENABLE_DEFAULT_VALUE = KEY_PREFIX + "enable-default-value";

  /**
   * The special property key that specify a separator for key and default value on placeholder.
   * <p>
   *   The default separator is {@code ":"}.
   * </p>
   * @since 3.4.2
   */
  // 默认分隔符是冒号
  public static final String KEY_DEFAULT_VALUE_SEPARATOR = KEY_PREFIX + "default-value-separator";

  // 默认情况下，关闭默认值的功能  
  private static final String ENABLE_DEFAULT_VALUE = "false";
  // 默认分隔符是冒号
  private static final String DEFAULT_VALUE_SEPARATOR = ":";

  private PropertyParser() {
    // Prevent Instantiation
    
  }

  public static String parse(String string, Properties variables) {
    VariableTokenHandler handler = new VariableTokenHandler(variables);
    // 委托给GenericTokenParser解析器，通用的占位符解析器
    GenericTokenParser parser = new GenericTokenParser("${", "}", handler);
    return parser.parse(string);
  }

  private static class VariableTokenHandler implements TokenHandler {
    // <properties>节点下定义的键对，用于替换占位符
    private final Properties variables;
    // 是否支持占位符中使用默认值的功能
    private final boolean enableDefaultValue;
    // 指定占位符和默认值之间的分隔符
    private final String defaultValueSeparator;

    private VariableTokenHandler(Properties variables) {
      this.variables = variables;
      this.enableDefaultValue = Boolean.parseBoolean(getPropertyValue(KEY_ENABLE_DEFAULT_VALUE, ENABLE_DEFAULT_VALUE));
      this.defaultValueSeparator = getPropertyValue(KEY_DEFAULT_VALUE_SEPARATOR, DEFAULT_VALUE_SEPARATOR);
    }

    private String getPropertyValue(String key, String defaultValue) {
      return (variables == null) ? defaultValue : variables.getProperty(key, defaultValue);
    }

    @Override
    public String handleToken(String content) {
      // 检查variables集合是否为空
      if (variables != null) {
        String key = content;
        if (enableDefaultValue) {
          // 检测是否支持占位符中使用默认值的功能
          final int separatorIndex = content.indexOf(defaultValueSeparator);
          String defaultValue = null;
          if (separatorIndex >= 0) {
            // 查找分隔符
            key = content.substring(0, separatorIndex);
            // 获取默认值
            defaultValue = content.substring(separatorIndex + defaultValueSeparator.length());
          }
          if (defaultValue != null) {
            // 在variables集合中查找指定的占位符
            return variables.getProperty(key, defaultValue);
          }
        }
        // 不支持默认值的功能，则直接查找variables集合
        if (variables.containsKey(key)) {
          return variables.getProperty(key);
        }
      }
      return "${" + content + "}";
    }
  }

}






```





