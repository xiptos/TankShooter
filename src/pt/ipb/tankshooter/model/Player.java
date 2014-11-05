package pt.ipb.tankshooter.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Player implements Serializable {
	String id;
	double x;
	double y;
	double angle;
	int points;
	boolean alive = true;
	int num;

	public Player(String id) {
		this.id = id;
		this.num = -1;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Player) {
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
		return getId();
	}

	public void incPoints() {
		this.points++;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isAlive() {
		return alive;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
