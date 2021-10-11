package utility;

public class Matrix {
	
	public double[][] matrix; 
	//dimension 1 is row, D2 is column.
	
	public Matrix(int D1, int D2) {
		matrix = new double[D1][D2];
		for (int x = 0; x < D1; ++x) {
			for (int y = 0; y < D2; ++y) {
				matrix[x][y] = 0;
			}
		}
	}
	
	public int D1size() {
		return matrix.length;
	}
	
	public int D2size() {
		return matrix[0].length;
	}
	
	/**
	 * returns: new array
	 */
	public double[] getD1(int d1) {
		double[] c = new double[this.D2size()];
		for (int i = 0; i < D2size(); ++i) {
			c[i] = matrix[d1][i];
		}
		return c;
	}
	
	/**
	 * returns: new array
	 */
	public double[] getD2(int d2) {
		double[] c = new double[this.D1size()];
		for (int i = 0; i < D1size(); ++i) {
			c[i] = matrix[i][d2];
		}
		return c;
	}
	 
	public double getElement(int d1, int d2) {
		return matrix[d1][d2];
	}
	
	public void setElement(int d1, int d2, double value) {
		matrix[d1][d2] = value;
	}
	
	public static String print(double[] out) {
		String o = "";
		for (int i = 0; i < out.length; ++i) {
			o += out[i];
			if (i != out.length - 1) {
				o+= "; ";
			}
		}
		return o;
	}
	
	public static String print(double[][] out) {
		String o = "";
		for (int i = 0; i < out.length; ++i) {
			o += print(out[i]) + "\n";
		}
		return o;
	}
	
	public String toString() {
		String o = "";
		for (int i = 0; i < this.D1size(); ++i) {
			for (int j = 0; j < this.D2size(); ++j) {
				o += matrix[i][j];
				if (j < this.D2size() - 1) {
					o += ", ";
				}
			}
			o += "\n";
		}
		return o;
	}
	
	/**
	 * effects: NONE
	 */
	public Matrix multiply(Matrix other) {
		if (this.D2size() != other.D1size()) {
			return null;
		} else {
			Matrix m = new Matrix(this.D1size(), other.D2size());
			for (int i = 0; i < this.D1size(); ++i) {
				for (int j = 0; j < other.D2size(); ++j) {
					m.setElement(i, j, Matrix.dot(this.getD1(i), other.getD2(j)));
				}
			}
			return m;
		}
	}
	
	/**
	 * effects: NONE
	 */
	public static double dot(double[] a, double[] b) {
		double ans = 0;
		for (int e = 0; e < a.length; ++e) {
			ans += (a[e] * b[e]);
		}
		return ans;
	}
	
	/**
	 * effects: NONE
	 * returns: a matrix that can be multiplied by a vector to obtain the rotated result
	 */
	public static Matrix constructRotationMatrix(Vector axis_in, double angle) {
		double rad = angle * Math.PI / 180;
		Vector axis = axis_in.clone();
		axis.normalize();
		double i = 1 - Math.cos(rad);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		double x = axis.getX();
		double y = axis.getY();
		double z = axis.getZ();
		Matrix a = new Matrix(3,3);
		a.matrix[0] = new double[] {cos + x*x*i, x*y*i - z*sin, x*z*i + y*sin};
		a.matrix[1] = new double[] {y*x*i + z*sin, cos+y*y*i, y*z*i - x*sin};
		a.matrix[2] = new double[] {z*x*i - y*sin, z*y*i + x*sin, cos + z*z*i};
		return a;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Matrix) {
			Matrix other = (Matrix) o;
			if (other.D1size() ==  D1size() && other.D2size() == D2size()) {
				for (int i = 0; i < D1size(); ++i) {
					for (int j = 0; j < D2size(); ++j) {
						if (matrix[i][j] != other.matrix[i][j]) {
							return false;
						}
					}
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * effects: NONE
	 * returns: a new matrix that is the flipped version of this
	 */
	public static Matrix flip(Matrix x) {
		Matrix y = new Matrix(x.D2size(), x.D1size());
		for (int i = 0; i < x.D1size(); ++i) {
			for (int j = 0; j < x.D2size(); ++j) {
				y.matrix[j][i] = x.matrix[i][j];
			}
		}
		return y;
	}
}