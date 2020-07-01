### PropertyParser

#### 1 简介

>  通用占位符解析器



### 2 类

```

public class GenericTokenParser {

  // 占位符的开始标记
  private final String openToken;
  // 占位符的结束标记
  private final String closeToken;
  // TokenHandler的实现类，会按照一定逻辑解析占位符
  private final TokenHandler handler;

  public GenericTokenParser(String openToken, String closeToken, TokenHandler handler) {
    this.openToken = openToken;
    this.closeToken = closeToken;
    this.handler = handler;
  }

  public String parse(String text) {
    // 检测是否为空
    if (text == null || text.isEmpty()) {
      return "";
    }
    // search open token
    // 查找开始标记
    int start = text.indexOf(openToken);
    if (start == -1) {
      return text;
    }
    char[] src = text.toCharArray();
    int offset = 0;
    // 用来记录解析后的字符串
    final StringBuilder builder = new StringBuilder();
    // 用来记录一个占位符的字面值
    StringBuilder expression = null;
    while (start > -1) {
      if (start > 0 && src[start - 1] == '\\') {
        // this open token is escaped. remove the backslash and continue.
        // 遇到转义的开始标记，则直接将前面的字符串以及开始标记追加到builder
        builder.append(src, offset, start - offset - 1).append(openToken);
        offset = start + openToken.length();
      } else {
        // found open token. let's search close token.
        // 查找到开始标记，且未转义
        if (expression == null) {
          expression = new StringBuilder();
        } else {
          expression.setLength(0);
        }
        builder.append(src, offset, start - offset);
        // 修改offset的位置
        offset = start + openToken.length();
        // 从offset向后继续查找结束标记
        int end = text.indexOf(closeToken, offset);
        while (end > -1) {
          if (end > offset && src[end - 1] == '\\') {
            // this close token is escaped. remove the backslash and continue.
            // 处理转义的结束标记
            expression.append(src, offset, end - offset - 1).append(closeToken);
            offset = end + closeToken.length();
            end = text.indexOf(closeToken, offset);
          } else {
            // 将开始标记和结束标记之间的字符串追加到expression中保存
            expression.append(src, offset, end - offset);
            break;
          }
        }
        if (end == -1) {
          // close token was not found.
          // 未找到结束标记
          builder.append(src, start, src.length - start);
          offset = src.length;
        } else {
          // 将占位符的字面值交给TokenHandler处理，并将处理结果追加到builder中
          // 最终平畴出解析后的完整内容
          builder.append(handler.handleToken(expression.toString()));
          offset = end + closeToken.length();
        }
      }
      // 移动start
      start = text.indexOf(openToken, offset);
    }
    if (offset < src.length) {
      builder.append(src, offset, src.length - offset);
    }
    return builder.toString();
  }
}





```
