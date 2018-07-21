package achmad.rifai.aitopsis.beans;

public class Crit {
	@com.google.gson.annotations.SerializedName("cb")
	private String cb;
	@com.google.gson.annotations.SerializedName("krit")
	private String krit;
	@com.google.gson.annotations.SerializedName("kep")
	private float kep;

	public Crit() {
		super();
	}

	public Crit(String cb, String krit, float kep) {
		super();
		this.cb = cb;
		this.krit = krit;
		this.kep = kep;
	}

	public String getCb() {
		return cb;
	}

	public void setCb(String cb) {
		this.cb = cb;
	}

	public String getKrit() {
		return krit;
	}

	public void setKrit(String krit) {
		this.krit = krit;
	}

	public float getKep() {
		return kep;
	}

	public void setKep(float kep) {
		this.kep = kep;
	}
}
