package pt.ipb.tankshooter.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Player implements Serializable {
	String id;
	double x;
	double y;
	double angle;
	int points;

	public Player(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Player) {
			Player other = (Player) obj;
			return getId().equals(other.getId());
		}
		return false;
	}
	
	public String getId() {
		return id;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAngle() {
		return angle;
	}

	public int getPoints() {
		return points;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return getId()+" - "+getX()+","+getY()+" - "+getAngle();
	}

	public void incPoints() {
		this.points++;
	}
}