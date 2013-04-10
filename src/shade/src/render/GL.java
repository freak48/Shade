package shade.src.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import shade.src.io.ResourceLocator;

public class GL {

	private static int orthoPushes = 0;

	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();

	public static void bindTexture(int t) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, t);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
				GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
				GL11.GL_NEAREST);
		GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE,
				GL11.GL_MODULATE);
	}

	public static void bindTexture(String t) {
		bindTexture(getTexture(t));
	}

	public static void bindTexture(Texture t) {
		bindTexture(t.id);
	}

	public static int genTexture(int internalformat, int w, int h, int format,
			ByteBuffer buffer) {
		int id = GL11.glGenTextures();
		bindTexture(id);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, internalformat, w, h, 0,
				format, GL11.GL_UNSIGNED_BYTE, buffer);
		unbindTexture();
		return id;
	}

	public static ByteBuffer getImageData(BufferedImage image) {
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(),
				pixels, 0, image.getWidth());
		boolean alpha = image.getColorModel().hasAlpha();
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth()
				* image.getHeight() * (alpha ? 4 : 3));

		for (int y = 0; y < image.getHeight(); y++)
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[(y * image.getWidth()) + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				if (alpha)
					buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		buffer.flip();
		return buffer;
	}

	public static Texture getTexture(String f) {
		return textures.get(f);
	}

	public static void loadTexture(String f) {
		try {
			BufferedImage image = ImageIO.read(ResourceLocator.findFile(f));
			boolean alpha = image.getColorModel().hasAlpha();
			ByteBuffer buffer = getImageData(image);
			int id = genTexture(GL11.GL_RGBA, image.getWidth(),
					image.getHeight(), alpha ? GL11.GL_RGBA : GL11.GL_RGB,
					buffer);
			Texture tex = new Texture(id, image.getWidth(), image.getHeight());
			textures.put(f.substring(f.lastIndexOf(File.separatorChar) + 1),
					tex);
		} catch (Exception e) {
			throw new RuntimeException("Problem loading texture: " + f, e);
		}
	}

	public static void popOrtho() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		orthoPushes--;
		if (orthoPushes < 0)
			throw new RuntimeException("UNDERFLOW_ERROR");
	}

	public static void pushOrtho() {
		pushOrtho(Display.getDisplayMode().getWidth(), Display.getDisplayMode()
				.getHeight());
	}

	public static void pushOrtho(int width, int height) {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, 0, height, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		orthoPushes++;
	}

	public static void unbindTexture() {
		bindTexture(0);
	}

}