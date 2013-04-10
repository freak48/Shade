package shade.src.render;

import java.io.File;

public class Texture {

	public static Texture genTexture(String f) {
		GL.loadTexture(f);
		return GL
				.getTexture(f.substring(f.lastIndexOf(File.separatorChar) + 1));
	}

	public final int id;
	public final int width;

	public final int height;

	public Texture(int i, int w, int h) {
		id = i;
		width = w;
		height = h;
	}

}