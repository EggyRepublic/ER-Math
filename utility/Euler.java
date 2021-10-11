package utility;
import org.bukkit.Location;

public class Euler extends Vector {

	public Euler(double yaw, double pitch, double roll) {
		super(yaw, pitch, roll);
	}
	
	public Euler(Vector vector) {
		super(0,0,0);
		set(vector);
	}
	
	/**
	 * parameters: location object with non-null values for pitch and yaw
	 * effects: changes this euler
	 */
	public void set(Location location) {
		// sets an euler angle based on current player yaw and pitch
		this.setX(location.getYaw());
		this.setY(location.getPitch());
		this.setZ(0);
	}
	
	/**
	 * parameters: vector object with non-null values X, Y and Z
	 * effects: changes this euler
	 */
	public void set(Vector vector) {
		// sets this object to be a euler same in direction as the input vector
		double yaw = 0;
		double factor = 180 / Math.PI;
		if (vector.getX() > 0) {
			yaw = Math.atan(vector.getZ()/vector.getX()) * factor - 90;
		} else if (vector.getX() < 0) {
			yaw = Math.atan(vector.getZ()/vector.getX()) * factor + 90;
		} else if (vector.getZ() < 0 ){
			yaw = -180;
		}
		double pitch = 90;
		double length = Math.sqrt(vector.getX() * vector.getX() + vector.getZ() * vector.getZ());
		if (length > 0) {
			pitch = -(Math.atan(vector.getY() / length)) * factor;
		} else if (vector.getY() > 0){
			pitch = -90;
		} else if (length == 0) {
			pitch = 0;
		}
		this.setX(yaw);
		this.setY(pitch);
		this.setZ(0);
	}
	
	/**
	 * effects: NONE
	 * returns: a new vector that points in same direction as this euler
	 */
	public Vector toVector() {
		//return a normalized vector
		double y = -Math.sin(this.getY() * Math.PI / 180);
		double length = Math.cos(this.getY() * Math.PI / 180);
		double x = 0;
		double z = 0;
		if (length > 0) {
			x = length * Math.sin(-this.getX() * Math.PI / 180);
			z = length * Math.cos(this.getX() * Math.PI / 180); 
			//this technically needs a negative inside the cos function, but since cos is symmetrical in this case it's removed
		}
		return new Vector(x, y, z);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	//EULER AND VECTOR HELPERS
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public double yaw() {
		return getX();
	}
	
	public double pitch() {
		return getY();
	}
	
	public double roll() {
		return getZ();
	}
	
	/**
	 * effects: modifies this Euler
	 * returns: a clone of this Euler
	 */
	public Vector normalize() {
		double yaw = this.getX();
		double pitch = this.getY();
		while (pitch <= -180) {
			pitch += 360;
		}
		while (pitch > 180) {
			pitch -= 360;
		}
		if (pitch > 90) {
			pitch = 180 - pitch;
			yaw += 180;
		}
		else if (pitch < -90) {
			pitch = -180 - pitch;
			yaw += 180;
		}
		while (yaw <= -180) {
			yaw += 360;
		}
		while (yaw > 180) {
			yaw -= 360;
		}
		this.setX(yaw);
		this.setY(pitch);
		return this.clone();
	}
	
	@Override
	public double magnitude() {
		return 1;
	}
	
	@Override
	public Euler clone() {
		Euler e = new Euler(getX(), getY(), getZ());
		return e;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Euler) {
			Euler other = (Euler) o;
			return (getX() == other.getX() && getY() == other.getY() && getZ() == other.getZ());
		}
		return false;
	}
	
	@Override 
	public String toString() {
		return Util.simpleEuler(this);
		//return "(" + this.getX() + ", " + this.getY() + ", " + this.getZ() + ")";
	}
}
