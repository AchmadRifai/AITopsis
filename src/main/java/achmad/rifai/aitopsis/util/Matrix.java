/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achmad.rifai.aitopsis.util;

import java.util.List;

/**
 *
 * @author ashura
 */
public class Matrix {
    private List<List<Double>>l;
    private int baris,kolom;

    public Matrix() {
    }

    public Matrix(List<List<Double>> l, int baris, int kolom) {
        this.l = l;
        this.baris = baris;
        this.kolom = kolom;
        inisialList();
    }

    public List<List<Double>> getL() {
        return l;
    }

    public void setL(List<List<Double>> l) {
        this.l = l;
    }

    public int getBaris() {
        return baris;
    }

    public void setBaris(int baris) {
        this.baris = baris;
        inisialList();
    }

    public int getKolom() {
        return kolom;
    }

    public void setKolom(int kolom) {
        this.kolom = kolom;
        inisialList();
    }

    public Double get(int a, int b){
        return l.get(a).get(b);
    }

    @Override
    public String toString() {
        String s="";
        for(int x=0;x<baris;x++){
            s+="[";
            for(int y=0;y<kolom;y++)s+=""+get(x,y)+" ";
            s+="]\n";
        }return s;
    }

    public void set(int a,int b,Double v){
        l.get(a).set(b, v);
    }

    private void inisialList() {
        l=new java.util.LinkedList<>();
        for(int x=0;x<baris;x++){
            List<Double>l1=new java.util.LinkedList<>();
            for(int y=0;y<kolom;y++)l1.add(Double.valueOf(0));
            l.add(l1);
        }
    }
}