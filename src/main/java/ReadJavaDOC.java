

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;

/**
 * TRUCKS-1681
 * 
 * @author FS0192
 *
 */
public class ReadJavaDOC {

	final Charset charset = Charset.forName("Cp1252");
	private static final String fileDir = "C:\\Downloads\\jdk-8u144-docs-all\\docs\\";

	public static void main(String[] args) throws CommentRemoverException {
		new ReadJavaDOC().listf(fileDir);
		for (String s : set){
			System.out.println(s);
		}
	}
	
	public File[] listf(String directoryName) throws CommentRemoverException {

		// .............list file
		File directory = new File(directoryName);

		// get all the files from a directory
		File[] fList = directory.listFiles();

		for (File file : fList) {
			if (file.isFile()) {
				if (file.getName().toLowerCase().endsWith(".html") || file.getName().toLowerCase().endsWith(".htm")) {
					run(file);
				}
			} else if (file.isDirectory()) {
				listf(file.getAbsolutePath());
			}
		}

		return fList;
	}

	String v17 = "<dt><span class=\"simpleTagLabel\">Since:</span></dt>\n<dd>1.7</dd>";
	String v18 = "<dt><span class=\"simpleTagLabel\">Since:</span></dt>\n<dd>1.8</dd>";

	private static final Set<String> set = new HashSet<String>();
	public void run(File f) throws CommentRemoverException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), charset))) {

			StringBuilder classStringBuild = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				classStringBuild.append(line + "\n");
			}
			
			if (classStringBuild.toString().contains(v18) || classStringBuild.toString().contains(v17)){
				// Only interfaces with default methods
				boolean onlyDefaultMethods = false;
				int startAuxOnlyDefMeth = 0;
				int indexOfDefaultMethod = classStringBuild.indexOf("</h4>\n<pre>default", startAuxOnlyDefMeth);
				while (indexOfDefaultMethod > -1){
					startAuxOnlyDefMeth = indexOfDefaultMethod+1;
					int endCurrentMethod = classStringBuild.indexOf("</h4>\n<pre>", classStringBuild.indexOf(v18, startAuxOnlyDefMeth));
					if (endCurrentMethod < 0){
						onlyDefaultMethods = true;
						break;
					}
					if (classStringBuild.indexOf(v18, startAuxOnlyDefMeth) > indexOfDefaultMethod && classStringBuild.indexOf(v18, startAuxOnlyDefMeth) < endCurrentMethod){
						indexOfDefaultMethod = classStringBuild.indexOf("</h4>\n<pre>default", startAuxOnlyDefMeth);
						onlyDefaultMethods = true;
						continue;
					} else {
						onlyDefaultMethods = false;
						break;
					}					
				}
				
				// Only abstract classes with abstract methods
				boolean hasAbstractMethods = false;
				int startAuxOnlyAbstractMeth = 0;
				int indexOfAbstracttMethod = classStringBuild.indexOf("</h4>\n<pre>public abstract", startAuxOnlyAbstractMeth);
				while (indexOfAbstracttMethod > -1){
					startAuxOnlyAbstractMeth = indexOfAbstracttMethod+1;
					int endCurrentMethod = classStringBuild.indexOf("</h4>\n<pre>", startAuxOnlyAbstractMeth);
					int xxxv18 = classStringBuild.indexOf(v18, startAuxOnlyAbstractMeth);
					int xxxv17 = classStringBuild.indexOf(v17, startAuxOnlyAbstractMeth);
					if (xxxv18 > -1 && xxxv18 < endCurrentMethod){
						hasAbstractMethods = true; 
					} else if (xxxv17 > -1 && xxxv17 < endCurrentMethod){
						hasAbstractMethods = true; 
					}
					indexOfAbstracttMethod = classStringBuild.indexOf("</h4>\n<pre>public abstract", startAuxOnlyAbstractMeth);
				}
				
				// 
				String abstractClassModel = "public abstract class ";
				String keywordsModel = "<meta name=\"keywords\" content=\"";
				final int start = classStringBuild.indexOf(keywordsModel) ;
				if (start > -1){
					String keywords = classStringBuild.substring(start+ keywordsModel.length(), classStringBuild.indexOf("\"", start+ keywordsModel.length()));
					String[] keywordsArr = keywords.split(" ");
					String clazz = keywordsArr[0];
					String type = keywordsArr[1];
					boolean abstractClass = false;
					if (type.equals("class")){
						final int startAbstractClass = classStringBuild.indexOf(abstractClassModel);
						if (startAbstractClass>-1){
							abstractClass = true;
						}
					}
					// ignore if not abstract
					if (type.equals("class") && !abstractClass){
						return;
					} else if (abstractClass && !hasAbstractMethods){
						return;
					} else if (type.equals("interface") && onlyDefaultMethods){
						return;
					}
					set.add("\"" + clazz + ":"+type + "\",");
				}
			}
		} catch (Exception e) {

		}

	}
}