package comiot.core.database;

import java.io.Serializable;

public class DBRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
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
