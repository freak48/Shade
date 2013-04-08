package shade.src.render;

import org.lwjgl.opengl.GL11;

/**
 * A class that is used as the one and only Camera, therefore its methods are
 * static
 * 
 * @author Alec
 */
public class Camera {

	/**
	 * The position of the Camera on the X-axis
	 */
	public static float posX;

	/**
	 * The position of the Camera on the Y-axis
	 */
	public static float posY;

	/**
	 * The position of the Camera on the Z-Axis
	 */
	public static float posZ;

	/**
	 * The camera's rotation yaw (Think of the hands of a clock on the ground)
	 */
	public static float yaw = 0.0f;

	/**
	 * The camera's rotation pitch (Think of the hands of a clock on a wall
	 * perpendicular to the ground)
	 */
	public static float pitch = 0.0f;

	/**
	 * The camera's rotation roll (Think if rolling down a hill perpendicular to
	 * it)
	 */
	public static float roll = 0.0f;

	/**
	 * Looks through the camera's "eye"
	 */
	public static void lookThrough() {
		GL11.glLoadIdentity();
		GL11.glRotatef(pitch, -1, 0, 0);
		GL11.glRotatef(yaw, 0, 1, 0);
		GL11.glRotatef(roll, 0, 0, 1);
		GL11.glTranslatef(posX, posY, posZ);
	}
}