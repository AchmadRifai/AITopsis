/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package achmad.rifai.aitopsis;

import java.io.IOException;
import java.util.List;

import achmad.rifai.aitopsis.util.Work;

/**
 *
 * @author ashura
 */
public class Main {
    public static void main(String[]args){
    	String krit_path="parame/crit.json",data_path="parame/data.json";
    	try {
			List<achmad.rifai.aitopsis.beans.Crit>lc=Work.loadCrit(krit_path);
			List<achmad.rifai.aitopsis.beans.Data>ld=Work.loadData(data_path);
			achmad.rifai.aitopsis.beans.Matriks km=Work.KritToMatriks(lc),dm=
					Work.DataToMatriks(ld,lc),pembagi=Work.genPembagi(dm),normal=
					Work.genNormalisasi(dm,pembagi),bobot=Work.genBobot(normal,lc),
					aplus=Work.genAplus(bobot,lc),amin=Work.genAmin(bobot,lc),
					dplus=Work.genDplus(bobot,aplus),dmin=Work.genDmin(bobot,amin);
			System.out.println(""+km+"\n"+dm+"\n"+pembagi+"\n"+normal+"\n"+bobot
					+"\n"+aplus+"\n"+amin+"\n"+dplus+"\n"+dmin);
			List<achmad.rifai.aitopsis.beans.MyResult>res=
					Work.genResult(ld,dplus,dmin);
			res.forEach((r)->System.out.println(r));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}