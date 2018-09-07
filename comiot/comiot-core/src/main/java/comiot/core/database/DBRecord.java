package comiot.core.database;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DBRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int pk;

	public DBRecord() {
		pk = -1;
	}
	
	@JsonCreator
	public DBRecord(@JsonProperty("pk")int pk) {
		this.pk = pk;
	}
	
	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}
}
