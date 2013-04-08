package shade.src.io;

import java.io.*;

public class ResourceLocator {
	
	
	public static InputStream getAsStream(String path) throws FileNotFoundException {
		try {
			String p = path;
			if(!p.startsWith(File.separator))
				p = File.separator + p;
			return ResourceLocator.class.getClassLoader().getResourceAsStream(path);
		} catch (Exception e) {
			return new FileInputStream(findFile(path));
		}
	}
	
	public static File findFile(String path) throws FileNotFoundException {
		if(!path.startsWith(File.separator))
			path = path + File.separator;
		File f = new File(new File("").getAbsoluteFile() + path);
		if(!f.exists()) {
			f = new File(path);
			if(f.exists() && f.isFile())
				return f.getAbsoluteFile();
		} else if(f.isFile())
			return f;
		throw new FileNotFoundException("No such file: " + path);
	}
	
}