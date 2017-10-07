import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class TxtFileReader {
	final String LSC_INI_IN_TEXT = "LSC.ini location: "; 
	final String MST_IN_TEXT = "MST_iniparam.mdb location: ";
	final String LSC_MDB_IN_TEXT = "LSC_.mdb location: ";
	final String NUCLIDE_LIBRARY_FROM_LSC_INI = "NuclideLibrary =";
	private String filePath;
	private File file;
	public boolean fileExists = false;
	public String lscIniPath;
	public String lscMdbPath;
	public String mstPath;
	public String nuclideLibrary;
	
	public TxtFileReader(String filePath) throws Exception {
		//super();
		this.filePath = filePath.trim();
		file = new File(filePath);
		System.out.println("File exists: " + file.exists()); 
		if (file.exists())
		{
			if (filePath.endsWith(".ini"))
					scanTextFromLscIni();
			fileExists = true;
			scanText();
		}
	}
	

	public void getText() throws Exception
	{
	BufferedReader br = new BufferedReader(new FileReader(filePath));
	System.out.println(br == null);
	try {
	    StringBuilder sb = new StringBuilder();
	    String line = br.readLine();

	    while (line != null) {
	    //	System.out.println(line);
	        sb.append(line);
	        sb.append(System.lineSeparator());
	        line = br.readLine();
	    }
	    String everything = sb.toString();
	} finally {
	    br.close();
	}
	}
	
	private void scanText() throws Exception
	{
		BufferedReader br = new BufferedReader(new FileReader(filePath));
	//	System.out.println(br == null);
		try {
		//    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		   // 	System.out.println(line);
		    	line = line.toLowerCase();
		    	if (line.startsWith(LSC_INI_IN_TEXT.toLowerCase()))
		    	{
		    		lscIniPath=line.substring(LSC_INI_IN_TEXT.length());
		    		System.out.println("lscIniPath found: " + lscIniPath);
		    	}
		    	
		    	if (line.startsWith(LSC_MDB_IN_TEXT.toLowerCase()))
		    	{
		    		lscMdbPath=line.substring(LSC_MDB_IN_TEXT.length());
		    		System.out.println("lscMdbPath found: " + lscMdbPath);
		    	}
		    	
		    	if (line.startsWith(MST_IN_TEXT.toLowerCase()))
		    	{
		    		mstPath=line.substring(MST_IN_TEXT.length());
		    		System.out.println("mstPath found: " + lscIniPath);
		    	}
	//	        sb.append(line);
	//	        sb.append(System.lineSeparator());
		    	
		        line = br.readLine();
		    }
	//	    String everything = sb.toString();
		} finally {
		    br.close();
		}
	}
	
	private void scanTextFromLscIni() throws Exception
	{
		BufferedReader br = new BufferedReader(new FileReader(filePath));
	//	System.out.println(br == null);
		try {
		//    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		 //   	System.out.println(line);
		    	line = line.toLowerCase();
		    	if (line.startsWith(NUCLIDE_LIBRARY_FROM_LSC_INI.toLowerCase()))
		    	{
		    		nuclideLibrary = line.substring(NUCLIDE_LIBRARY_FROM_LSC_INI.length()).trim();
		    		System.out.println("nuclideLibrary found from LSC.ini: " + nuclideLibrary);
		    	}
		    	
		        line = br.readLine();
		    }
		} finally {
		    br.close();
		}
	}

}
