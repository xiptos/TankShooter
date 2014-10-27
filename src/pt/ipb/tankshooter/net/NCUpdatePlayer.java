package pt.ipb.tankshooter.net;

import java.io.Serializable;

import pt.ipb.game.engine.Entity;

@SuppressWarnings("serial")
public class NCUpdatePlayer implements Serializable {
	String id;
	double x;
	double y;
	double angle;
	
	public NCUpdatePlayer(Entity entity) {
		this.x = entity.getX();
		this.y = entity.getY();
		this.angle = entity.getAngle();
		this.id = entity.getId();
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

	public String getId() {
		return id;
	}
}
