package utility;

public class Vector {
	
	double X;
	double Y;
	double Z;
	
	public Vector(double X, double Y, double Z) {
		this.X = X;
		this.Y = Y;
		this.Z = Z;
	}
	
	public double getX() {
		return X;
	}
	
	public double getY() {
		return Y;
	}
	
	public double getZ() {
		return Z;
	}
	
	public void setX(double X) {
		this.X = X;
	}
	
	public void setY(double Y) {
		this.Y = Y;
	}
	
	public void setZ(double Z) {
		this.Z = Z;
	}
	
	/**
	 * effects: NONE
	 */
	public double dot(Vector b) {
		return (getX() * b.getX() + getY() * b.getY() + getZ() * b.getZ());
	}
	
	/**
	 * effects: NONE
	 */
	public Vector cross(Vector b) {
		return new Vector(getY()*b.getZ() - getZ()*b.getY(), getZ()*b.getX() - getX()*b.getZ(), getX()*b.getY() - getY()*b.getX());
	}
	
	/**
	 * effects: NONE
	 */
	public Vector multiply(double factor) {
		return new Vector(getX() * factor, getY() * factor, getZ() * factor);
	}
	
	/**
	 * effects: NONE
	 */
	public Vector add(Vector add) {
		return new Vector(getX() + add.getX(), getY() + add.getY(), getZ() + add.getZ());
	}
	
	/**
	 * effects: NONE
	 */
	public Vector invert() {
		return new Vector(-getX(), -getY(), -getZ());
	}
	
	/**
	 * effects: NONE
	 */
	public Vector add(double add) {
		return new Vector(getX() + add, getY() + add, getZ() + add);
	}
	
	/**
	 * effects: NONE
	 */
	public Vector perpendicularComponent(Vector v) {
		//project u onto v
		return add(parallelComponent(v).invert());
	}
	
	/**
	 * effects: NONE
	 */
	public Vector parallelComponent(Vector v) {
		//project u onto v
		return v.multiply( dot(v) / ( v.magnitude() * v.magnitude() ) );
	}
	
	/**
	 * effects: NONE
	 */
	public double magnitude() {
		return Math.sqrt(getX() * getX() + getY() * getY() + getZ() * getZ());
	}

	/**
	 * effects: modifies this vector
	 * returns: a clone of this vector
	 */
	public Vector normalize() {
		double mag = magnitude();
		setX(getX()/mag);
		setY(getY()/mag);
		setZ(getZ()/mag);
		return this.clone();
	}

	@Override
	public String toString() {
		return Util.simpleVector(this);
		//return "{" + this.getX() + ", " + this.getY() + ", " + this.getZ() + "}";
	}
	
	@Override
	public Vector clone() {
		return new Vector(getX(), getY(), getZ());
	}
	
	@Override
	public boolean equals(Object o) {
		if (o.getClass().equals(Vector.class)) {
			Vector other = (Vector) o;
			return (getX() == other.getX() && getY() == other.getY() && getZ() == other.getZ());
		}
		return false;
	}
	
	/**
	 * effects: NONE
	 */
	public boolean eplison(Vector v) {
		if (Math.abs(getX() - v.getX()) < 0.1 && Math.abs(getY() - v.getY()) < 0.1 && Math.abs(getZ() - v.getZ()) < 0.1) {
			return true;
		}
		return false;
	}
}
