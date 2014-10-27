package pt.ipb.tankshooter.net;

import java.io.Serializable;

import org.jgroups.Address;

@SuppressWarnings("serial")
public class NetworkPlayer implements Serializable {
	String name;
	String id;
	int num;
	Address address;

	public NetworkPlayer(String id, String name, int num, Address address) {
		this.name = name;
		this.id = id;
		this.num = num;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public Address getAddress() {
		return address;
	}

	public int getNum() {
		return num;
	}

	@Override
	public String toString() {
		return getNum() + "." + getName() + " (" + getId() + "; " + getAddress() + ")";
	}
}
