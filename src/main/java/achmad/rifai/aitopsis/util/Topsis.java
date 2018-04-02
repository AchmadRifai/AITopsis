/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achmad.rifai.aitopsis.util;

import achmad.rifai.aitopsis.beans.Conf;
import achmad.rifai.aitopsis.beans.Data;
import java.util.List;

/**
 *
 * @author ashura
 */
public class Topsis {
    private List<Conf>lconf;
    private List<Data>ldata;
    private Matrix m1,m2;

    public Topsis(List<Conf> lconf, List<Data> ldata) {
        this.lconf = lconf;
        this.ldata = ldata;
        m1=Filehelper.genMatrixSyarat(lconf);
        m2=Filehelper.genMatrixData(ldata);
    }

    public Matrix genPembagi(){
        Matrix m=new Matrix(new java.util.LinkedList<>(),1,m1.getKolom());
        for(int x=0;x<m2.getBaris();x++)for(int y=0;y<m2.getKolom();y++){
            if(x==0)m.set(0, y, Math.pow(m2.get(x, y), 2));
            else m.set(0, y, m.get(0, y)+Math.pow(m2.get(x, y), 2));
        }for(int x=0;x<m.getKolom();x++)m.set(0, x, Math.sqrt(m.get(0, x)));
        return m;
    }

    public Matrix genNormal(Matrix bagi){
        Matrix m=new Matrix(new java.util.LinkedList<>(),m2.getBaris(),m2.getKolom());
        for(int x=0;x<m.getBaris();x++)for(int y=0;y<m.getKolom();y++)
            m.set(x, y, m2.get(x, y)/bagi.get(0, y));
        return m;
    }

    public Matrix genBobot(Matrix normal) {
        Matrix m=new Matrix(new java.util.LinkedList<>(),m2.getBaris(),m2.getKolom());
        for(int x=0;x<m.getBaris();x++)for(int y=0;y<m.getKolom();y++)
            m.set(x, y, normal.get(x, y)*m1.get(0, y));
        return m;
    }

    public Matrix genAplus(Matrix bobot) {
        Matrix m=new Matrix(new java.util.LinkedList<>(),1,bobot.getKolom());
        for(int x=0;x<bobot.getKolom();x++){
            Conf c=lconf.get(x);
            List<Double>l=genKolomMatrix(bobot,x);
            if(c.getArah()==Conf.ConfStatus.BENEFIT)m.set(0, x, genMax(l));
            else m.set(0, x, genMin(l));
        }return m;
    }

    private List<Double> genKolomMatrix(Matrix bobot, int i) {
        List<Double>l=new java.util.LinkedList<>();
        for(int x=0;x<bobot.getBaris();x++)l.add(bobot.get(x, i));
        return l;
    }

    private Double genMax(List<Double> l) {
        Double d=Double.valueOf(0);
        for(Double v:l){
            if(d!=0){
                if(d<v)d=v;
            }else d=v;
        }return d;
    }

    private Double genMin(List<Double> l) {
        Double d=Double.valueOf(0);
        for(Double v:l){
            if(d!=0){
                if(d>v)d=v;
            }else d=v;
        }return d;
    }

    public Matrix genAmin(Matrix bobot) {
        Matrix m=new Matrix(new java.util.LinkedList<>(),1,bobot.getKolom());
        for(int x=0;x<bobot.getKolom();x++){
            Conf c=lconf.get(x);
            List<Double>l=genKolomMatrix(bobot,x);
            if(c.getArah()==Conf.ConfStatus.COST)m.set(0, x, genMax(l));
            else m.set(0, x, genMin(l));
        }return m;
    }

    public Matrix genDplus(Matrix bobot, Matrix aplus) {
        Matrix m=new Matrix(null,0,0);
        m.setBaris(bobot.getBaris());
        m.setKolom(1);
        for(int x=0;x<bobot.getBaris();x++){
            double d=0;
            for(int y=0;y<bobot.getKolom();y++)d+=Math.pow(aplus.get(0, y)-bobot.get(x, y), 2);
            m.set(x, 0, Math.sqrt(d));
        }return m;
    }

    public Matrix genDmin(Matrix bobot, Matrix amin) {
        Matrix m=new Matrix(null,0,0);
        m.setBaris(bobot.getBaris());
        m.setKolom(1);
        for(int x=0;x<bobot.getBaris();x++){
            double d=0;
            for(int y=0;y<bobot.getKolom();y++)d+=Math.pow(bobot.get(x, y)-amin.get(0, y), 2);
            m.set(x, 0, Math.sqrt(d));
        }return m;
    }

    public Matrix ourValue(Matrix dplus, Matrix dmin) {
        Matrix m=new Matrix(null,dplus.getBaris(),dplus.getKolom());
        for(int x=0;x<dplus.getBaris();x++)
            for(int y=0;y<dplus.getKolom();y++)
                m.set(x, y, dmin.get(x, y)/(dmin.get(x, y)+dplus.get(x, y)));
        return m;
    }
}