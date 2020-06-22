package com.mybatis.study.chapter02;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

public class XPathTest {


    public static void main(String[] args) throws Exception {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        // 开启验证
        documentBuilderFactory.setValidating(true);
        documentBuilderFactory.setNamespaceAware(false);
        documentBuilderFactory.setIgnoringComments(true);
        documentBuilderFactory.setIgnoringElementContentWhitespace(false);
        documentBuilderFactory.setCoalescing(false);
        documentBuilderFactory.setExpandEntityReferences(true);

        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();

        builder.setErrorHandler(new ErrorHandler() {
            public void warning(SAXParseException exception) throws SAXException {
                System.out.println("WARN:"+exception.getMessage());
            }

            public void error(SAXParseException exception) throws SAXException {
                System.out.println("error:"+exception.getMessage());
            }

            public void fatalError(SAXParseException exception) throws SAXException {
                System.out.println("fatalError:"+exception.getMessage());
            }
        });

        Document doc = builder.parse("src/main/resources/inventory.xml");

        XPathFactory factory = XPathFactory.newInstance();
        //创建XPath对象
        XPath xPath = factory.newXPath();
        XPathExpression expr = xPath.compile("//book[author='Neal Stephenson']/title/text()");


        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        System.out.println("查询作者为Neal Stepenson的图书标题");

        NodeList nodes = (NodeList) result;

        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getNodeValue());
        }



    }

}
