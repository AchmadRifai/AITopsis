/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achmad.rifai.aitopsis.util;

import achmad.rifai.aitopsis.beans.Conf;
import achmad.rifai.aitopsis.beans.Data;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author ashura
 */
public class Filehelper {
    public static javax.swing.table.TableModel dataTable(java.io.File f,java.io.File d) throws ParserConfigurationException, SAXException, IOException{
        List<Conf>lconf=muatConfig(f);
        String[]s=new String[1+lconf.size()];
        for(int x=1;x<=lconf.size();x++){
            Conf c=lconf.get(x-1);
            s[x]=c.getNama();
        }s[0]="Nama";
        DefaultTableModel m=new DefaultTableModel(s, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int x) {
                if(x==0)return String.class;
                else return BigDecimal.class;
            }
        };fillData(m, lconf,d);
        return m;
    }

    public static List<Conf> muatConfig(java.io.File f) throws ParserConfigurationException, SAXException, IOException {
        List<Conf>l=new java.util.LinkedList<>();
        org.w3c.dom.Document d=javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f);
        org.w3c.dom.NodeList nl=d.getElementsByTagName("formate");
        for(int x=0;x<nl.getLength();x++)if(nl.item(x).getNodeType()==org.w3c.dom.Node.ELEMENT_NODE){
            Element e=(Element) nl.item(x);
            Conf c=new Conf();
            c.setNama(e.getAttribute("nama"));
            c.setKepentingan(Integer.parseInt(e.getAttribute("kepentingan")));
            c.setArah(Conf.ConfStatus.valueOf(e.getAttribute("arah")));
            l.add(c);
        }return l;
    }

    private static void fillData(DefaultTableModel m1, List<Conf> lconf,java.io.File f) {
        DefaultTableModel m=m1;
        List<Conf>lc=lconf;
        new Thread(() -> {
            try {
                List<Data>l=loadData(f);
                for(Data d:l){
                    Object[]o=new Object[d.getValue().size()+1];
                    o[0]=d.getName();
                    int x=1;
                    for(Double b:d.getValue()){
                        o[x]=b;
                        x++;
                    }m.addRow(o);
                }
            } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(Filehelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    public static List<Data> loadData(java.io.File d) throws ParserConfigurationException, SAXException, IOException {
        List<Data>l=new java.util.LinkedList<>();
        org.w3c.dom.Document doc=javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(d);
        org.w3c.dom.NodeList nl=doc.getElementsByTagName("datane");
        for(int x=0;x<nl.getLength();x++)if(nl.item(x).getNodeType()==org.w3c.dom.Node.ELEMENT_NODE){
            Data da=new Data();
            Element e=(Element) nl.item(x);
            da.setName(e.getAttribute("nama"));
            da.setValue(listData(e));
            l.add(da);
        }return l;
    }

    private static List<Double> listData(Element d) {
        List<Double>l=new java.util.LinkedList<>();
        org.w3c.dom.NodeList nl=d.getElementsByTagName("nilai");
        for(int x=0;x<nl.getLength();x++)if(nl.item(x).getNodeType()==org.w3c.dom.Node.ELEMENT_NODE){
            l.add(Double.parseDouble(nl.item(x).getTextContent()));
        }return l;
    }

    public static Matrix genMatrixSyarat(List<Conf> lconf){
        Matrix m=new Matrix();
        m.setBaris(1);
        m.setKolom(lconf.size());
        List<List<Double>>l1=new java.util.LinkedList<>();
        List<Double>l2=new java.util.LinkedList<>();
        m.setL(l1);
        l1.add(l2);
        lconf.forEach((Conf c) -> {
            l2.add((double)c.getKepentingan());
        });return m;
    }

    public static Matrix genMatrixData(List<Data>ldata){
        Matrix m=new Matrix();
        m.setBaris(ldata.size());
        m.setKolom(ldata.get(0).getValue().size());
        List<List<Double>>l1=new java.util.LinkedList<>();
        m.setL(l1);
        ldata.forEach((d) -> {
            l1.add(d.getValue());
        });return m;
    }

    public static void saveConfig(List<Conf> conf, String fName) throws ParserConfigurationException, TransformerException {
        Document d=javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        org.w3c.dom.Element root=d.createElement("conf");
        conf.stream().map((c) -> {
            org.w3c.dom.Element e=d.createElement("formate");
            e.setAttribute("nama", c.getNama());
            e.setAttribute("kepentingan", ""+c.getKepentingan());
            e.setAttribute("arah", ""+c.getArah());
            return e;
        }).forEachOrdered((e) -> {
            root.appendChild(e);
        });d.appendChild(root);
        saveXML(d,fName);
    }

    private static void saveXML(Document d, String fName) throws TransformerException {
        javax.xml.transform.dom.DOMSource ds=new javax.xml.transform.dom.DOMSource(d);
        java.io.File f=new java.io.File(fName);
        if(!f.getParentFile().exists())f.getParentFile().mkdirs();
        if(f.exists())f.delete();
        javax.xml.transform.stream.StreamResult sr=new javax.xml.transform.stream.StreamResult(f);
        javax.xml.transform.Transformer t=javax.xml.transform.TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        t.transform(ds, sr);
    }

    public static void saveData(List<Data> datane, String fName) throws ParserConfigurationException, TransformerException {
        Document d=javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        org.w3c.dom.Element root=d.createElement("data");
        for(Data da:datane){
            org.w3c.dom.Element e=d.createElement("datane");
            e.setAttribute("nama", da.getName());
            da.getValue().stream().map((dou) -> {
                Element e2=d.createElement("nilai");
                e2.setTextContent(""+dou);
                return e2;
            }).forEachOrdered((e2) -> {
                e.appendChild(e2);
            });root.appendChild(e);
        }d.appendChild(root);
        saveXML(d,fName);
    }
}