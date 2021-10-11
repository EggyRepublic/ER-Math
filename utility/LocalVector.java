package utility;

import org.bukkit.Location;


public class LocalVector {
	
	//represents the orientation of the object in respect to the global perspective, contains the three axis or rotation
	
	//when at the origin with no deviation, the roll axis should be pointing at 0,0,1, which is positive Z
	
	public Vector roll_axis; //Z axis
	public Vector yaw_axis; //Y axis
	public Vector pitch_axis; //X axis (Minecraft just needed to do this backwards!)
	
	public LocalVector() {
		reset();
	}
	
	public LocalVector(Location location) {
		reset();
		this.yawShift(location.getYaw());
		this.pitchShift(location.getPitch());
	}
	
	public void reset() {
		roll_axis = new Vector(0, 0, 1); 
		yaw_axis = new Vector(0, 1, 0); 
		pitch_axis = new Vector(-1, 0, 0);
	}
	
	public void sync(Vector v) {
		syncYaw(v);
		pitchShift(Util.toEuler(v).getY());
	}
	
	public void syncYaw(Vector v) {
		reset();
		yawShift(Util.toEuler(v).getX());
	}
	
	public void yawShift(double angle) {
		Util.rotateAboutAxis(roll_axis, yaw_axis, angle);
		Util.rotateAboutAxis(pitch_axis, yaw_axis, angle);
	}
	
	public void pitchShift(double angle) {
		Util.rotateAboutAxis(roll_axis, pitch_axis, angle);
		Util.rotateAboutAxis(yaw_axis, pitch_axis, angle);
	}
	
	public void rollShift(double angle) {
		Util.rotateAboutAxis(yaw_axis, roll_axis, angle);
		Util.rotateAboutAxis(pitch_axis, roll_axis, angle);
	}
	
	@Override
	public String toString() {
		return "YAW AXIS: " + Util.simpleVector(yaw_axis) + " PITCH AXIS: " + Util.simpleVector(pitch_axis) + " ROLL AXIS: " + Util.simpleVector(roll_axis);
	}

	@Override
	public LocalVector clone() {
		LocalVector l = new LocalVector();
		l.roll_axis = this.roll_axis.clone();
		l.yaw_axis = this.yaw_axis.clone();
		l.pitch_axis = this.pitch_axis.clone();
		return l;
	}
}