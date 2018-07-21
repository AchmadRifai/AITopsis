package achmad.rifai.aitopsis.beans;

public class MyResult {
	private String nama;
	private double val;

	public MyResult() {
		super();
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public double getVal() {
		return val;
	}

	public void setVal(double val) {
		this.val = val;
	}

	@Override
	public String toString() {
		return "MyResult [nama=" + nama + ", val=" + val + "]";
	}
}
