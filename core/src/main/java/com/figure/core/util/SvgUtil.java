package com.figure.core.util;

import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;


public class SvgUtil {
    public static Integer getSvgWidth(String svgURI) throws IOException {
        File file = new File(svgURI);
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        Document doc = f.createDocument(file.toURI().toString());
        Element element = doc.getDocumentElement();
        String viewBox = element.getAttribute("viewBox");
        String width = viewBox.split(" ")[2];
        return (int)Double.parseDouble(width);
    }

    public static Integer getSvgHeight(String svgURI) throws IOException {
        File file = new File(svgURI);
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        Document doc = f.createDocument(file.toURI().toString());
        Element element = doc.getDocumentElement();
        //view中包含这宽高信息
        String viewBox = element.getAttribute("viewBox");
        String height = viewBox.split(" ")[3];
        return (int)Double.parseDouble(height);
    }

    public static String parseSVG(String svgURI) throws Exception {
        File file = new File(svgURI);
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
        Document doc = f.createDocument(file.toURI().toString());
        Element element = doc.getElementById("Layer_1");
        String width = element.getAttribute("width");
        String height = element.getAttribute("height");
        System.out.println(width);
        System.out.println(height);
        String elementStr = convertElemToSVG(element);
        return elementStr;
    }

    // 将element转换成字符串
    public static String convertElemToSVG(Element element) {
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        StringWriter buffer = new StringWriter();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        try {
            transformer.transform(new DOMSource(element), new StreamResult(buffer));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        String elementStr = buffer.toString();
        return elementStr;
    }

    public static void main(String[] args) throws Exception {
        int svgHeight = getSvgHeight("/Users/ranqinghua/Downloads/color-scheme-left.svg");
        int svgWidth = getSvgWidth("/Users/ranqinghua/Downloads/color-scheme-left.svg");
        System.out.println(svgWidth);
        System.out.println(svgHeight);
    }
}
