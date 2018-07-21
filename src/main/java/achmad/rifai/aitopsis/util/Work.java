package achmad.rifai.aitopsis.util;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonArray;

import achmad.rifai.aitopsis.beans.*;

public class Work {
	public static List<Crit> loadCrit(String krit_path) throws IOException {
		List<Crit>l=new java.util.LinkedList<>();
		java.io.File f=new java.io.File(krit_path);
		com.google.gson.JsonParser p=new com.google.gson.JsonParser();
		if(f.exists()) {
			java.io.FileReader r=new java.io.FileReader(f);
			JsonArray a=(JsonArray) p.parse(r);
			for(int x=0;x<a.size();x++) {
				com.google.gson.JsonElement e=a.get(x);
				Crit c=new Crit();
				c.setCb(e.getAsJsonObject().get("cb").getAsString());
				c.setKrit(e.getAsJsonObject().get("krit").getAsString());
				c.setKep(e.getAsJsonObject().get("kep").getAsFloat());
				l.add(c);
			} r.close();
		}else System.out.println("Krit.json not found");
		return l;
	}

	public static List<Data> loadData(String data_path) throws IOException {
		List<Data>l=new java.util.LinkedList<>();
		java.io.File f=new java.io.File(data_path);
		com.google.gson.JsonParser p=new com.google.gson.JsonParser();
		if(f.exists()) {
			java.io.FileReader r=new java.io.FileReader(f);
			JsonArray a=(JsonArray) p.parse(r);
			for(int x=0;x<a.size();x++) {
				com.google.gson.JsonElement e=a.get(x);
				Data d=new Data();
				d.setNama(e.getAsJsonObject().get("nama").getAsString());
				d.setKrit(e.getAsJsonObject().get("krit").getAsString());
				d.setValue(e.getAsJsonObject().get("value").getAsFloat());
				l.add(d);
			} r.close();
		}else System.out.println("Data.json not found");
		return l;
	}

	public static Matriks KritToMatriks(List<Crit> lc) {
		Matriks m=new Matriks("Matriks Krit",1,lc.size());
		for(int x=0;x<lc.size();x++)
			m.set(0, x, lc.get(x).getKep());
		return m;
	}

	public static Matriks DataToMatriks(List<Data> ld, List<Crit> lc) {
		List<String>ln=listNamaData(ld);
		Matriks m=new Matriks("Matriks Data",ln.size(),lc.size());
		for(int x=0;x<ln.size();x++) {
			String nama=ln.get(x);
			for(int y=0;y<lc.size();y++) {
				Crit c=lc.get(y);
				double d=getValueNow(ld,nama,c.getKrit());
				m.set(x, y, d);
			}
		} return m;
	}

	private static double getValueNow(List<Data> ld, String nama, String krit) {
		double f=0;
		for(Data d:ld)
			if(nama.equals(d.getNama())&&krit.equals(d.getKrit()))
				f=d.getValue();
		return f;
	}

	private static List<String> listNamaData(List<Data> ld) {
		List<String>l=new java.util.LinkedList<>();
		ld.forEach((d)->{
			if(!l.contains(d.getNama()))l.add(d.getNama());
		});
		return l;
	}

	public static Matriks genPembagi(Matriks dm) {
		Matriks m=new Matriks("Pembagi",1,dm.getKolom());
		for(int y=0;y<dm.getKolom();y++) {
			double f=0;
			for(int x=0;x<dm.getBaris();x++)
				f+=Math.pow(dm.get(x, y), 2);
			f=Math.sqrt(f);
			m.set(0, y, f);
		} return m;
	}

	public static Matriks genNormalisasi(Matriks dm, Matriks pembagi) {
		Matriks m=new Matriks("Ternormalisasi",dm.getBaris(),dm.getKolom());
		for(int x=0;x<m.getBaris();x++) {
			for(int y=0;y<m.getKolom();y++)
				m.set(x, y, dm.get(x, y)/pembagi.get(0, y));
		} return m;
	}

	public static Matriks genBobot(Matriks normal, List<Crit> lc) {
		Matriks m=new Matriks("Terbobot",normal.getBaris(),normal.getKolom());
		for(int x=0;x<m.getBaris();x++)for(int y=0;y<m.getKolom();y++) {
			Crit c=lc.get(y);
			m.set(x, y, normal.get(x, y)*c.getKep());
		} return m;
	}

	public static Matriks genAplus(Matriks bobot, List<Crit> lc) {
		Matriks m=new Matriks("A+",1,lc.size());
		for(int y=0;y<m.getKolom();y++) {
			Crit c=lc.get(y);
			double d=0;
			if("BENEFIT".equals(c.getCb()))for(int x=0;x<bobot.getBaris();x++) {
				if(d==0)d=bobot.get(x, y);
				else d=Math.max(d, bobot.get(x, y));
			}else for(int x=0;x<bobot.getBaris();x++) {
				if(d==0)d=bobot.get(x, y);
				else d=Math.min(d, bobot.get(x, y));
			}m.set(0, y, d);
		} return m;
	}

	public static Matriks genAmin(Matriks bobot, List<Crit> lc) {
		Matriks m=new Matriks("A-",1,lc.size());
		for(int y=0;y<m.getKolom();y++) {
			Crit c=lc.get(y);
			double d=0;
			if("COST".equals(c.getCb()))for(int x=0;x<bobot.getBaris();x++) {
				if(d==0)d=bobot.get(x, y);
				else d=Math.max(d, bobot.get(x, y));
			}else for(int x=0;x<bobot.getBaris();x++) {
				if(d==0)d=bobot.get(x, y);
				else d=Math.min(d, bobot.get(x, y));
			}m.set(0, y, d);
		} return m;
	}

	public static Matriks genDplus(Matriks bobot, Matriks aplus) {
		Matriks m=new Matriks("D+",bobot.getBaris(),1);
		for(int x=0;x<m.getBaris();x++) {
			double d=0;
			for(int y=0;y<aplus.getKolom();y++)
				d+=Math.pow(aplus.get(0, y)-bobot.get(x, y), 2);
			d=Math.sqrt(d);
			m.set(x, 0, d);
		} return m;
	}

	public static Matriks genDmin(Matriks bobot, Matriks amin) {
		Matriks m=new Matriks("D-",bobot.getBaris(),1);
		for(int x=0;x<m.getBaris();x++) {
			double d=0;
			for(int y=0;y<amin.getKolom();y++)
				d+=Math.pow(bobot.get(x, y)-amin.get(0, y), 2);
			d=Math.sqrt(d);
			m.set(x, 0, d);
		} return m;
	}

	public static List<MyResult> genResult(List<Data> ld, Matriks dplus, 
			Matriks dmin) {
		List<MyResult>l=new java.util.LinkedList<>();
		List<String>ln=listNamaData(ld);
		for(int x=0;x<ln.size();x++) {
			MyResult m=new MyResult();
			m.setVal(dmin.get(x, 0)/(dplus.get(x, 0)+dmin.get(x, 0)));
			m.setNama(ln.get(x));
			l.add(m);
		} return l;
	}
}
