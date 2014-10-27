package pt.ipb.tankshooter.net;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NCRegisterPlayer implements Serializable {
	String name;
	
	public NCRegisterPlayer(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
