/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achmad.rifai.aitopsis;

import achmad.rifai.aitopsis.beans.Conf;
import achmad.rifai.aitopsis.beans.Data;
import achmad.rifai.aitopsis.util.Matrix;
import achmad.rifai.aitopsis.util.Topsis;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author ashura
 */
public class Main {
    public static void main(String[]args){
        try {
            List<Conf>conf=achmad.rifai.aitopsis.util.Filehelper.muatConfig(new java.io.File(System.getProperty("user.home")+"/confTopsis"));
            List<Data>datane=achmad.rifai.aitopsis.util.Filehelper.loadData(new java.io.File(System.getProperty("user.home")+"/dataTopsis"));
            achmad.rifai.aitopsis.util.Topsis t=new achmad.rifai.aitopsis.util.Topsis(conf, datane);
            Matrix bagi=t.genPembagi();
            Matrix normal=t.genNormal(bagi);
            Matrix bobot=t.genBobot(normal);
            Matrix aplus=t.genAplus(bobot),amin=t.genAmin(bobot);
            Matrix dplus=t.genDplus(bobot, aplus),dmin=t.genDmin(bobot, amin);
            Matrix v=t.ourValue(dplus, dmin);
            System.out.println("Value\n"+v);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void initConf(List<Conf> conf) {
        conf.add(new Conf("Harga",Conf.ConfStatus.COST,4));
        conf.add(new Conf("Kualitas",Conf.ConfStatus.BENEFIT,5));
        conf.add(new Conf("Fitur",Conf.ConfStatus.BENEFIT,4));
        conf.add(new Conf("Populer",Conf.ConfStatus.BENEFIT,3));
        conf.add(new Conf("Purna Jual",Conf.ConfStatus.BENEFIT,3));
        conf.add(new Conf("Keawetan",Conf.ConfStatus.BENEFIT,2));
    }

    private static void initDatane(List<Data> datane) {
        Data d1=new Data(),d2=new Data(),d3=new Data(),d4=new Data();
        d1.setName("Galaxy");
        d2.setName("IPhone");
        d3.setName("BB");
        d4.setName("Lumia");
        List<Double>l1=new java.util.LinkedList<>(),l2=new java.util.LinkedList<>(),l3=new java.util.LinkedList<>(),l4=new java.util.LinkedList<>();
        l1.add(3500.0);
        l1.add(70.0);
        l1.add(10.0);
        l1.add(80.0);
        l1.add(3000.0);
        l1.add(36.0);
        l2.add(4500.0);
        l2.add(90.0);
        l2.add(10.0);
        l2.add(60.0);
        l2.add(2500.0);
        l2.add(48.0);
        l3.add(4000.0);
        l3.add(80.0);
        l3.add(9.0);
        l3.add(90.0);
        l3.add(2000.0);
        l3.add(48.0);
        l4.add(4000.0);
        l4.add(70.0);
        l4.add(8.0);
        l4.add(50.0);
        l4.add(1500.0);
        l4.add(60.0);
        d1.setValue(l1);
        d2.setValue(l2);
        d3.setValue(l3);
        d4.setValue(l4);
        datane.add(d1);
        datane.add(d2);
        datane.add(d3);
        datane.add(d4);
    }
}