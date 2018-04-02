/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achmad.rifai.aitopsis.beans;

import java.util.List;

/**
 *
 * @author ashura
 */
public class Data {
    private String name;
    private List<Double>value;

    public Data() {
    }

    public Data(String name, List<Double> value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getValue() {
        return value;
    }

    public void setValue(List<Double> value) {
        this.value = value;
    }
}