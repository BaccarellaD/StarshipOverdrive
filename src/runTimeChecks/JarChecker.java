package runTimeChecks;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class JarChecker {

	private boolean inJar;
	private String  resourceRelativePath;
	
	public JarChecker(String resourceRelativePath) throws IOException {
		this.resourceRelativePath = resourceRelativePath;
		 runJarCheck();
	}
	
	
	private void runJarCheck() throws IOException {
		
		URL url = this.getClass().getClassLoader().getResource(resourceRelativePath);
		URLConnection urlConnection = url.openConnection();
		
		System.out.println("URL CONNECTION: " + urlConnection.getURL());
		
		String protocol = url.getProtocol();
		inJar = protocol.equals("jar") || protocol.equals("rsrc");
	}
	
	public String getJarFilePathModifer() {
		return inJar? "jar:" : "";
	}
	
	public boolean isInJar() {
		return inJar;
	}
	
}
