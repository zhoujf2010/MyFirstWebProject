package com.zjf.xml;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class test
{

    public static void main(String[] args) throws Exception {
        String xmlfile = "E:\\WorkSpace\\MyFirstWebProject\\src\\main\\resources\\updateInfo.xml";

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        FileInputStream fis = new FileInputStream(xmlfile);
        Document doc = db.parse(fis);

        Element root = doc.getDocumentElement();

        // NodeList nodeList = root.getChildNodes();
        // for(int i =0 ;i < nodeList.getLength();i++){
        //
        // System.out.println(nodeList.item(i).getNodeName());
        // System.out.println(nodeList.item(i).getNodeValue());
        // }

        Node node = getNodeByName(root, "version");
        System.out.println(node.getNodeName() + "  " + node.getTextContent());
        List<Node> nodeList = getNodeListByName(getNodeByName(root, "descs"), "desc");
        for (Node item : nodeList) {
            String v = item.getAttributes().getNamedItem("v").getNodeValue();
            String inn = item.getTextContent().replace("\t", "");
            System.out.println(item.getNodeName() + "  v=" + v + "  inn=" + inn);
        }
    }

    private static List<Node> getNodeListByName(Node rootNode, String Name) {
        List<Node> ret = new ArrayList<Node>();
        NodeList nodeList = rootNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeName().equals(Name))
                ret.add(nodeList.item(i));
        }
        return ret;
    }

    private static Node getNodeByName(Node rootNode, String Name) {
        NodeList nodeList = rootNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeName().equals(Name))
                return nodeList.item(i);
        }
        return null;
    }

}
