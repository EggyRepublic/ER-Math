package tests;

import utility.*;

import org.junit.Test;
import static org.junit.Assert.*;
import org.bukkit.Location;
import org.junit.BeforeClass;

/*
 * all changes to the Math program should pass all these tests
 * 
 * originally created with junit 4
 */

public final class testSuite {
	
	@BeforeClass
	public static void print() {
		System.out.println("Beginning JUNIT tests for ER Math \n");
	}
	
	public boolean eplison(double a, double b) {
		return Math.abs(a - b) < 0.01;
	}

	@Test
	public void eulerSet() {
		Vector u1 = new Vector(1.2247,1,1.2247);
		Euler e1 = new Euler(0,0,0);
		e1.set(u1);
		Euler e2 = new Euler(u1);
		assertTrue(e1.eplison(e2));
		assertTrue(eplison(e1.yaw(),-45));
		assertTrue(eplison(e1.pitch(),-30));
		
		Vector u2 = new Vector(-1.2247,1,1.2247);
		e1.set(u2);
		assertTrue(eplison(e1.yaw(),45));
		assertTrue(eplison(e1.pitch(),-30));
		
		Vector u3 = new Vector(-1.2247,1,-1.2247);
		e1.set(u3);
		assertTrue(eplison(e1.yaw(),135));
		assertTrue(eplison(e1.pitch(),-30));
		
		Vector u4 = new Vector(0,1,0);
		e1.set(u4);
		assertTrue(eplison(e1.yaw(),0));
		assertTrue(eplison(e1.pitch(),-90));
		
		Vector u5 = new Vector(1,0,0);
		e1.set(u5);
		assertTrue(eplison(e1.yaw(),-90));
		assertTrue(eplison(e1.pitch(),0));
		
		Vector u6 = new Vector(0,0,0);
		e1.set(u6);
		assertTrue(eplison(e1.yaw(),0));
		assertTrue(eplison(e1.pitch(),0));
		
		Vector u7 = new Vector(0,0,-1);
		e1.set(u7);
		assertTrue(eplison(e1.yaw(),-180));
		assertTrue(eplison(e1.pitch(),0));
		
		Location l = new Location(null, 0,0,0);
		l.setYaw(-45);
		l.setPitch(-30);
		e1.set(l);
		assertTrue(e1.eplison(e2));
		assertTrue(eplison(e1.magnitude(),1));
		assertTrue(e1.roll() == 0);
		
		//MEuler tests
		MEuler me1 = new MEuler(0,0,0);
		me1.set(u1);
		assertTrue(eplison(me1.yaw(),-45));
		assertTrue(eplison(me1.pitch(),-30));
		assertTrue(eplison(me1.magnitude(),2));
		
		Vector v1 = me1.toVector();
		assertTrue(u1.eplison(v1));
		
		me1.set(u2);
		assertTrue(eplison(me1.yaw(),45));
		assertTrue(eplison(me1.pitch(),-30));
		assertTrue(eplison(me1.magnitude(),2));

		me1.set(u3);
		assertTrue(eplison(me1.yaw(),135));
		assertTrue(eplison(me1.pitch(),-30));

		me1.set(u4);
		assertTrue(eplison(me1.yaw(),0));
		assertTrue(eplison(me1.pitch(),-90));

		me1.set(u5);
		assertTrue(eplison(me1.yaw(),-90));
		assertTrue(eplison(me1.pitch(),0));

		me1.set(u6);
		assertTrue(eplison(me1.yaw(),0));
		assertTrue(eplison(me1.pitch(),0));

		me1.set(u7);
		assertTrue(eplison(me1.yaw(),-180));
		assertTrue(eplison(me1.pitch(),0));
	}
	
	@Test
	public void eulerToVector() {
		Euler e1 = new Euler(-45, -30, 0);
		Vector v = e1.toVector();
		Vector target = new Vector(0.61235, 0.5, 0.61235);
		assertTrue(v.eplison(target));
	}
	
	@Test
	public void eulerNormalize() {
		Euler e1 = new Euler(720, 360, 0);
		Euler e2 = new Euler(720, 180, 0);
		Euler e3 = new Euler(720, -505, 0);
		Euler e4 = new Euler(-180, 360, 0);
		e1.normalize();
		e2.normalize();
		e3.normalize();
		e4.normalize();
		Euler target1 = new Euler(0,0,0);
		Euler target2 = new Euler(180,0,0);
		Euler target3 = new Euler(180,-35,0);
		Euler target4 = new Euler(180,0,0);
		assertTrue(e1.eplison(target1));
		assertTrue(e2.eplison(target2));
		assertTrue(e3.eplison(target3));
		assertTrue(e4.eplison(target4));
	}
	
	@Test
	public void eulerEquals() {
		Euler e1 = new Euler(1,1,1.232);
		Euler e2 = new Euler(1.0,1,1.232);
		Euler e3 = new Euler(1.01,1,1.232);
		assertTrue(e1.equals(e2));
		assertTrue(!e1.equals(e3));
		assertTrue(!e1.equals(new Vector(1,1,1.232)));
	}
	
	@Test
	public void vectorDot() {
		Vector u = new Vector(10,20,30);
		Vector v = new Vector(2,3,4);
		assertTrue(eplison(u.dot(v),200.0));
	}
	
	@Test
	public void vectorCross() {
		Vector u = new Vector(10,20,30);
		Vector v = new Vector(2,3,4);
		Vector target = new Vector(-10, 20, -10);
		assertTrue(u.cross(v).eplison(target));
	}
	
	@Test
	public void vectorMultiply() {
		Vector u = new Vector(10.1,20,30);
		Vector target = new Vector(25.25,50,75);
		assertTrue(u.multiply(2.5).eplison(target));
	}
	
	@Test
	public void vectorAdd() {
		Vector u = new Vector(10,20,30);
		Vector v = new Vector(10.1,20.1,30.1);
		Vector target = new Vector(20.1,40.1,60.1);
		assertTrue(u.add(v).eplison(target));
		target = new Vector(11.2, 21.2, 31.2);
		assertTrue(v.add(1.1).eplison(target));
	}
	
	@Test
	public void vectorInvert() {
		Vector u = new Vector(0, -10.4, 24.4);
		Vector target = new Vector(0, 10.4, -24.4);
		assertTrue(u.invert().eplison(target));
	}
	
	@Test
	public void vectorParallelComponent() {
		Vector u = new Vector(1, 2, 10);
		Vector v1 = new Vector(10,0,0);
		Vector v2 = new Vector(-1,2,4);
		Vector target1 = new Vector(1,0,0);
		Vector target2 = v2.multiply(2.048);
		assertTrue(u.parallelComponent(v1).eplison(target1));
		assertTrue(u.parallelComponent(v2).eplison(target2));
	}
	
	@Test
	public void vectorPerpendicularComponent() {
		Vector u = new Vector(1, 2, 10);
		Vector v1 = new Vector(10,0,0);
		Vector v2 = new Vector(-1,2,4);
		Vector target1 = new Vector(0, 2, 10);
		Vector target2 = new Vector(3.048, -2.096, 1.808);
		assertTrue(u.perpendicularComponent(v1).eplison(target1));
		assertTrue(u.perpendicularComponent(v2).eplison(target2));
	}
	
	@Test
	public void vectorMagnitude() {
		Vector u = new Vector(1,2,3.5);
		assertTrue(eplison(u.magnitude(), 4.153));
	}
	
	@Test
	public void vectorNormalize() {
		Vector u = new Vector(1,2,3.5);
		Vector target = new Vector(1/4.153, 2/4.153, 3.5/4.153);
		assertTrue(u.normalize().eplison(target));
	}
	
	@Test
	public void vectorComparisons() {
		Vector u1 = new Vector(1.1,2.2,3.33);
		Vector u2 = new Vector(1.1,2.2,3.3300001);
		Vector u3 = new Vector(1.1,2.2,3.33000);
		Vector u4 = new Vector(1.1,2.2,3.43001);
		assertTrue(u1.equals(u3));
		assertTrue(!u1.equals(u2));
		assertTrue(u1.eplison(u2));
		assertTrue(u1.eplison(u3));
		assertTrue(!u1.eplison(u4));
		assertTrue(!u1.equals(new Euler(1.1,2.2,3.33)));
	}
	
	@Test
	public void vectorCopy() {
		Vector u1 = new Vector(1,2,3);
		Vector u2 = u1.clone();
		assertTrue(u2.equals(new Vector(1,2,3)));
		u2.setX(10);
		assertTrue(u1.equals(new Vector(1,2,3)));
		assertTrue(u2.equals(new Vector(10,2,3)));
	}
	
	@Test
	public void utilToEuler() {
		Vector u1 =  new Vector(0.61235, 0.5, 0.61235);
		Vector u2 =  new Vector(0,0,-10);
		Vector u3 =  new Vector(0,-1,0);
		Vector u4 =  new Vector(0,1,0);
		Vector u5 =  new Vector(-3,0,3);
		Vector u6 =  new Vector(0,-1,0);
		Euler e1 = new Euler(-45, -30, 0);
		Euler e2 = new Euler(-180,0,0);
		Euler e3 = new Euler(0,90,0);
		Euler e4 = new Euler(0,-90,0);
		assertTrue(Util.toEuler(u1).eplison(e1));
		assertTrue(Util.toEuler(u2).eplison(e2));
		assertTrue(Util.toEuler(u3).eplison(e3));
		assertTrue(Util.toEuler(u4).eplison(e4));
		MEuler me1 = new MEuler(-45, -30, 1);
		MEuler me2 = new MEuler(-180, 0, 10);
		MEuler me3 = new MEuler(0, 90, 1);
		MEuler me4 = new MEuler(0, -90, 1);
		MEuler me5 = new MEuler(45,0,4.243);
		MEuler me6 = new MEuler(0,90,1);
		assertTrue(Util.toMEuler(u1).eplison(me1));
		assertTrue(Util.toMEuler(u2).eplison(me2));
		assertTrue(Util.toMEuler(u3).eplison(me3));
		assertTrue(Util.toMEuler(u4).eplison(me4));
		assertTrue(Util.toMEuler(u5).eplison(me5));
		assertTrue(Util.toMEuler(u6).eplison(me6));
		
		Location l = new Location(null, 0, 0, 0);
		l.setYaw(-45);
		l.setPitch(30);
		
		assertTrue(Util.toEuler(l).eplison(new Euler(-45,30,0)));
	}
	
	@Test
	public void utilToVector() {
		assertTrue(Util.toVector(new Euler(-405, -30, 0)).eplison(new Vector(0.61235, 0.5, 0.61235)));
		assertTrue(Util.toVector(new Euler(0,90,0)).eplison(new Vector(0, -1, 0)));
		assertTrue(Util.toMVector(new MEuler(-45, -30, 2)).eplison(new Vector(0.61235 *2, 0.5 *2, 0.61235 *2)));
	}
	
	@Test
	public void utilRotation() {
		System.out.println("Beginning utilRotation test");
		Vector u1 = new Vector(0.61235, 0.5, 0.61235);
		Vector axis = new Vector(0,1,0);
		Util.rotateAboutAxis(u1, axis, 90);
		assertTrue(u1.eplison(new Vector(-0.61235, 0.5, 0.61235)));
		System.out.println("\tOriginal vector: " + u1);
		System.out.println("\tExpressed as Euler: " + Util.toEuler(u1));
		
		//applyRotation with location tests
		Location l = new Location(null, 0, 0, 0);
		l.setYaw(-45);
		l.setPitch(30);
		Vector u2 = Util.applyRotation(u1, l);
		assertTrue(u2.eplison(new Vector(0,0,1)));
		
		LocalVector l1 = new LocalVector();
		l1.syncYaw(u1);
		
		//quick localVector test
		l.setYaw(45);
		l.setPitch(-30);
		LocalVector l2 = new LocalVector(l);
		assertTrue(l2.roll_axis.eplison(u1));
		
		//beginning applyRotation tests
		
		System.out.println("\tLocalVector before first rotation: " + l1);
		u1 = Util.applyRotation(u1, new Euler(90,30,0), l1);
		System.out.println("\tVector after rotating 90 yaw, 30 pitch: " + u1);
		assertTrue(u1.eplison(new Vector(-0.707,0,-0.707)));
		
		l1.syncYaw(u1);
		u1 = Util.applyRotation(u1, new Euler(-90,-30,0), l1);
		assertTrue(u1.eplison(new Vector(-0.61235, 0.5, 0.61235)));
		
		l1.syncYaw(u1);
		u1 = Util.applyRotation(u1, new Euler(0,-30,0), l1);
		assertTrue(u1.eplison(new Vector(-0.354, 0.866, 0.354)));
		
		l1.syncYaw(u1);
		u1 = Util.applyRotation(u1, new Euler(45,0,0), l1);
		assertTrue(u1.eplison(new Vector(-0.5, 0.866, 0)));
		
		l1.syncYaw(u1);
		l1.rollShift(90);
		u1 = Util.applyRotation(u1, new Euler(0,-90,0), l1);
		assertTrue(u1.eplison(new Vector(0, 0.866, 0.5)));
		
		l1.sync(u1);
		assertTrue(l1.roll_axis.eplison(u1));
		
		//Global rotations:
		
		u1 = new Vector(0.61235, 0.5, 0.61235);
		u2 = new Vector(0,0,1);
		l1 = new LocalVector();
		l1.syncYaw(u1);
		u1 = Util.applyRotationGlobal(u1, new Euler(-90, 90, 0), l1);
		u2 = Util.applyRotationGlobal(u2, new Euler(90, -60, 0));
		assertTrue(u1.eplison(new Vector(0.966, 0, -0.259)));	
		assertTrue(u2.eplison(new Vector(-1, 0, 0)));
		
		System.out.println("Successfully finished utilRotation test\n");
	}
	
	@Test
	public void utilLocationInc() {
		Location l = new Location(null, 1, 2.5, 0);
		l.setYaw(45);
		l.setPitch(-30);
		Vector u1 = new Vector(12,2.2,3.3);
		Util.locationInc(l, u1, 2.5);
		assertTrue(eplison(l.getX(), 31));
		assertTrue(eplison(l.getY(), 8));
		assertTrue(eplison(l.getZ(), 8.25));
	}
	
	@Test
	public void matrix() {
		System.out.println("Beginning Matrix test");
		Matrix m1 = new Matrix(3,3);
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				m1.setElement(i, j, 2*i+j);
			}
		}
		Matrix m2 = new Matrix(3,3);
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				m2.setElement(i, j, 2*j+i);
			}
		}
		System.out.println("\tMatrix 1: \n" + m1);
		m1 = Matrix.flip(m1);
		System.out.println("\tMatrix 1 flipped: \n" + m1);
		assertTrue(m1.equals(m2));
		m2.setElement(1,1,10);
		assertTrue(m2.getElement(1, 1) == 10);
		assertTrue(!m1.equals(m2));
		
		Matrix m3 = new Matrix(4,3);
		assertTrue(!m1.equals(m3));
		
		assertTrue(m1.multiply(m3) == null);
		
		System.out.println("\tMatrix 1 printing through static function: \n" + Matrix.print(m1.matrix));
		System.out.println("\tMatrix 1 printing first line through static function: \n" + Matrix.print(m1.matrix[0]));
		
		System.out.println("Successfully finished Matrix test\n");
	}
}
