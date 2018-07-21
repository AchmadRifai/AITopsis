package achmad.rifai.aitopsis.beans;

public class Data {
	@com.google.gson.annotations.SerializedName("nama")
	private String nama;
	@com.google.gson.annotations.SerializedName("krit")
	private String krit;
	@com.google.gson.annotations.SerializedName("value")
	private float value;

	public Data() {
		super();
	}

	public Data(String nama, String krit, float value) {
		super();
		this.nama = nama;
		this.krit = krit;
		this.value = value;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getKrit() {
		return krit;
	}

	public void setKrit(String krit) {
		this.krit = krit;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
}
