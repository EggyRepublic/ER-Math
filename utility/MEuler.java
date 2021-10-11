package utility;

public class MEuler extends Euler {
	//same as Euler except the third term is not roll, it's magnitude

	public MEuler(double yaw, double pitch, double magnitude) {
		/**instead of roll, the third parameter describes length*/
		super(yaw, pitch, magnitude);
	}
	
	@Override
	public void set(Vector vector) {
		double magnitude = vector.magnitude();
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
		this.setZ(magnitude);
	}
	
	@Override
	public Vector toVector() {
		//return a normalized vector form MEuler
		double y = -Math.sin(this.getY() * Math.PI / 180);
		double length = Math.cos(this.getY() * Math.PI / 180);
		double x = 0;
		double z = 0;
		if (length > 0) {
			x = length * Math.sin(-this.getX() * Math.PI / 180);
			z = length * Math.cos(this.getX() * Math.PI / 180); 
			//this technically needs a negative inside the cos function, but since cos is symmetrical in this case it's removed to improve performance
		}
		Vector vec = new Vector(x, y, z);
		return vec.normalize().multiply(this.getZ());
	}
	
	@Override
	public double magnitude() {
		return this.getZ();
	}
}
