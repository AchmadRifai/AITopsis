/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achmad.rifai.aitopsis.beans;

/**
 *
 * @author ashura
 */
public class Conf {

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public ConfStatus getArah() {
        return arah;
    }

    public void setArah(ConfStatus arah) {
        this.arah = arah;
    }

    public int getKepentingan() {
        return kepentingan;
    }

    public void setKepentingan(int kepentingan) {
        this.kepentingan = kepentingan;
    }
    public enum ConfStatus{
        COST, BENEFIT
    }

    private String nama;
    private ConfStatus arah;
    private int kepentingan;

    public Conf(String nama, ConfStatus arah, int kepentingan) {
        this.nama = nama;
        this.arah = arah;
        this.kepentingan = kepentingan;
    }

    public Conf() {
    }
}