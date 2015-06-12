package miscellaneous.utils.math.geometry.geometry3D;

public interface Rotatable<T extends Rotatable<T>> {
	public T rotateXYZ(double u, double v, double w);
	public T rotateX(double u);
	public T rotateY(double u);
	public T rotateZ(double u);
}
