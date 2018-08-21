package database;

public class DBRecord {
	private int pk;

	public DBRecord() {
		pk = -1;
	}
	
	public DBRecord(int pk) {
		this.pk = pk;
	}
	
	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}
}
