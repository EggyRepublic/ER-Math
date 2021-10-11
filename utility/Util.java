package utility;

import java.text.DecimalFormat;
import org.bukkit.Location;

public final class Util {
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// CONVERT BETWEEN EULER AND VECTOR, based on Minecraft's system of 0 yaw at positive Z, and the X axis is inverted
	// from a rotated version of the cartesian coordinate system
	//
	// everything is in degrees!
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//All methods should be static in this class so the class shouldn't ever need to be instigated. 
	private Util() {}
	
	/**
	 * parameter: a vector with x, y, z values
	 * returns: an Euler transformation with pitch and yaw that points in same direction as the vector
	 * effects: NONE
	 */
	public static Euler toEuler(Vector vector) {
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
		}
		
		Euler euler = new Euler(yaw, pitch, 0.0);
		return euler;
	}
	
	public static Euler toEuler(Location location) {
		return new Euler(location.getYaw(), location.getPitch(), 0);
	}
	
	/**
	 * same as toEuler except it preserves the magnitude data of the vector by storing it in the third argument of Vector
	 */
	public static MEuler toMEuler(Vector vector) {
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
		}
		MEuler euler = new MEuler(yaw, pitch, magnitude);
		return euler;
	}
	
	/**
	 * effects: NONE
	 */
	public static Vector toVector(Euler euler) {
		//return a normalized vector
		double y = -Math.sin(euler.getY() * Math.PI / 180);
		double length = Math.cos(euler.getY() * Math.PI / 180);
		double x = 0;
		double z = 0;
		x = length * Math.sin(-euler.getX() * Math.PI / 180);
		z = length * Math.cos(euler.getX() * Math.PI / 180); 
		//this technically needs a negative inside the cos function, but since cos is symmetrical in this case it's removed to improve performance
		return new Vector(x, y, z);
	}
	
	public static Vector toMVector(MEuler euler) {
		//return a normalized vector form Meuler
		double y = -Math.sin(euler.getY() * Math.PI / 180);
		double length = Math.cos(euler.getY() * Math.PI / 180);
		double x = 0;
		double z = 0;
		x = length * Math.sin(-euler.getX() * Math.PI / 180);
		z = length * Math.cos(euler.getX() * Math.PI / 180); 
		//this technically needs a negative inside the cos function, but since cos is symmetrical in this case it's removed to improve performance
		Vector vec = new Vector(x, y, z);
		return vec.normalize().multiply(euler.getZ());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// EULER AND VECTOR PRINTING
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * effects: NONE
	 */
	public static String simpleVector(Vector vector) {
		/** displays a vector to 3 decimal places */
		DecimalFormat f = new DecimalFormat("0.000");
		return "x: " + f.format(vector.getX()) + " y: " + f.format(vector.getY()) + " z: " + f.format(vector.getZ());
	}
	
	/**
	 * effects: NONE
	 */
	public static String simpleEuler(Euler euler) {
		/** displays a vector to 3 decimal places */
		DecimalFormat f = new DecimalFormat("0.000");
		if (euler instanceof MEuler) {
			return "pitch: " + f.format(euler.getX()) + " yaw: " + f.format(euler.getY()) + " magnitude: " + f.format(euler.getZ());
		} else {
			return "pitch: " + f.format(euler.getX()) + " yaw: " + f.format(euler.getY()) + " roll: " + f.format(euler.getZ());
		}
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// ROTATIONS
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * parameter: local_vector is the vector that will be rotated
	 * parameter: offset_euler is the set of rotations that should be performed (yaw and pitch only)
	 * parameter: l is the set of axes that local_vector should rotate about. In world space, these are just the X, Y and Z axes.
	 * effects: NONE
	 * 
	 * NOTE: when this function performs a rotation, it will rotate the local vector along with it
	 */
	public static Vector applyRotation(Vector vector, Euler offset_euler, LocalVector l) {
		/**applies the euler angle rotations to a vector*/
		Vector resultant_vector = vector.clone();
		LocalVector local_vector = l.clone();
		rotateAboutAxis(resultant_vector, local_vector.yaw_axis, offset_euler.getX());
		local_vector.yawShift(offset_euler.getX());
		rotateAboutAxis(resultant_vector, local_vector.pitch_axis, offset_euler.getY());
		local_vector.pitchShift(offset_euler.getY());
		return resultant_vector;
	}
	
	public static Vector applyRotation(Vector vector, Location location) {
		/**applies the player's yaw and pitch rotations to a vector*/
		LocalVector l = new LocalVector();
		l.syncYaw(vector);
		return applyRotation(vector, new Euler(location.getYaw(), location.getPitch(), 0), l);
	}
	
	/**
	 * parameter: local_vector is the vector that will be rotated
	 * parameter: offset_euler is the set of rotations that should be performed (yaw and pitch only)
	 * parameter: l is the set of axes that local_vector should rotate about. In world space, these are just the X, Y and Z axes.
	 * effects: NONE
	 * 
	 * NOTE: when this function performs a rotation, it keeps the local vector constant as in the local vector will not rotate
	 * along with the rotations performed
	 */
	public static Vector applyRotationGlobal(Vector vector, Euler offset_euler, LocalVector g) {
		/**applies the Euler angle rotations to a vector*/
		Vector resultant_vector = vector.clone();
		rotateAboutAxis(resultant_vector, g.yaw_axis, offset_euler.getX());
		rotateAboutAxis(resultant_vector, g.pitch_axis, offset_euler.getY());
		return resultant_vector;
	}
	
	public static Vector applyRotationGlobal(Vector vector, Euler offset_euler) {
		return applyRotationGlobal(vector, offset_euler, new LocalVector());
	}

	/**
	 * rotates the vector "target" about the axis "axis" by the specified "angle" amount
	 * 
	 * parameters: target vector, axis, angle
	 * effects: target vector will be modified into its new rotated location
	 */
	public static void rotateAboutAxis(Vector target, Vector axis, double angle) {
		Vector parallel = target.parallelComponent(axis);
		Vector perpendicular = target.add(parallel.invert());
		Matrix m = new Matrix(1,3);
		m.matrix[0] = new double[] {perpendicular.getX(), perpendicular.getY(), perpendicular.getZ()};
		Matrix r = Matrix.constructRotationMatrix(axis, angle);
		Matrix result = m.multiply(r);
		Vector perpendicular2 = new Vector(result.matrix[0][0], result.matrix[0][1], result.matrix[0][2]);
		Vector final_vec = perpendicular2.add(parallel);
		target.setX(final_vec.getX());
		target.setY(final_vec.getY());
		target.setZ(final_vec.getZ());
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// MISC
	//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * effects: changes the location argument
	 */
	public static void locationInc(Location location, Vector vec, double factor) {
		location.setX(location.getX() + vec.getX() * factor);
		location.setY(location.getY() + vec.getY() * factor);
		location.setZ(location.getZ() + vec.getZ() * factor);
	}
}
