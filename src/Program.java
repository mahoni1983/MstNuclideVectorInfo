import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.sun.webkit.ContextMenu.ShowContext;

public class Program {
	final String INITIALISATION_FILE_NAME = "Nuclide vector information of MST programs.ini";
	final String LSC_INI_FILE_NAME = "lsc.ini";
	final String LSC_MDB_FILE_NAME = "\\projects\\lsc_iniparam.mdb";
	public String lscIniPath;
	public String lscMdbPath;
	public String mstPath;

	MainFrame mainFrame;
	public MdbConnection lscConnection;
	public String nuclideVector;
	public String nuclideVectorDate;
	public String nuclidesLibrary;
	ArrayList<NuclideVectorElement> alNuclideVectorList;
	ArrayList<String> alNuclideLibraryList;

	public ArrayList<NuclideVectorElement> getAlNuclideVectorList() {
		return alNuclideVectorList;
	}

	public static void main(String[] args) throws Exception {
		Program program = new Program();
		program.start();
	}

	private void start() throws Exception {
/*		JFrame frameLoading = new JFrame("Loading");
		frameLoading.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameLoading.setBounds(100, 100, 500, 400);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frameLoading.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		JTextArea textAreaLoadingText = new JTextArea("Reading text file with paths");
		contentPane.add(textAreaLoadingText, BorderLayout.CENTER);
		frameLoading.setVisible(true);*/
		
		mainFrame = new MainFrame(this);
		TxtFileReader txtFileReader = new TxtFileReader(INITIALISATION_FILE_NAME);
		if (txtFileReader.fileExists) {
			if (txtFileReader.mstPath==null) // if it is null
			{
				System.out.println("mstPath is not defined");
				mainFrame.showMessage("Error", "Path to MST's mdb file not defined.");
			}
			else {
				mstPath = txtFileReader.mstPath;
				try {
					nuclideVector = getNvFromMst(mstPath);
				} catch (Exception e) {
					mainFrame.showMessage("Error", "Problem with connection to " + mstPath);
				}
				System.out.println("nuclideVector from getNvFromMs: "
						+ nuclideVector);
			}

			if (txtFileReader.lscIniPath==null)
			{
				System.out.println("lscIniPath is not defined");
				mainFrame.showMessage("Error", "Path to LSC's ini file not defined.");
			}
			else {
				lscIniPath = txtFileReader.lscIniPath;
				// lscConnection = new MdbConnection(lscIniPath);
				try {
					nuclidesLibrary = getNuclideLibrary(lscIniPath);
				} catch (Exception e) {
					mainFrame.showMessage("Error", "Problem with connection to " + lscIniPath);
					e.printStackTrace();
				}
				// alNv = getNuclideVectorList();
			}

			if (txtFileReader.lscMdbPath==null)
			{
				System.out.println("lscMdbPath is not defined");
				mainFrame.showMessage("Error", "Path to LSC's mdb file not defined.");
			}
			else {
				lscMdbPath = txtFileReader.lscMdbPath;
				try {
					lscConnection = new MdbConnection(lscMdbPath);
				
				// nuclidesLibrary = getNuclideLibrary();
				alNuclideVectorList = getNuclideVectorListFromLscMdb();
				nuclideVectorDate = alNuclideVectorList.get(alNuclideVectorList
						.size() - 1).date.toString();
				// for (NuclideVectorElement nve: alNuclideVectorList)
				// System.out.println(nve);
				alNuclideLibraryList = getNuclideLibraryListFromLscMdb();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					mainFrame.showMessage("Error", "Problem with connection to " + lscMdbPath);
				}
			}
		} else
		{
			System.out.println("text file for initialization not found");
			mainFrame.showMessage("Error", "\"" + INITIALISATION_FILE_NAME + "\" file "
					+ "for program initialisation not found.\n It will not work correctly.");
		}
	//	mainFrame.frame.dispose();
	//	mainFrame = new MainFrame(this);
		mainFrame.loadContent();
		
		mainFrame.txtVectorName.setText(nuclideVector);
		mainFrame.txtVectorDate.setText(nuclideVectorDate);
		// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		mainFrame.txtNuclideLibrary.setText(nuclidesLibrary);
		mainFrame.setNuclideVectorList(alNuclideVectorList);
		mainFrame.setNuclideLibraryList(alNuclideLibraryList);
		mainFrame.txtLastRefresh.setText(new Date().toLocaleString());
	//	
		lscConnection.getConnection().close();
		lscConnection = null;

	}

	private String getNvFromMst(String mstPath) {
		// TODO Auto-generated method stub
		String nv = null;
		MdbConnection mstMdbConnection;
		try {
			mstMdbConnection = new MdbConnection(mstPath);
		
		

			String query = "select paramValue from IniParam where paramName ='NuclideVector';";
			System.out.println(query);
			PreparedStatement ps = mstMdbConnection.getInstance().getConnection()
					.prepareStatement(query);
			// ps.setString(1, "Nuclide Vector");
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs.next())
				nv = rs.getString("paramValue");
			mstMdbConnection.closeConnection();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("getNvFromMst error");
			mainFrame.showMessage("Error in getNV", e.getMessage());
		}
		
		//System.out.println("mstMdbConnection closed in Program");
		return nv;
	}

	private String getNuclideLibrary(String LscIniPath) {
		// TODO Auto-generated method stub
		TxtFileReader txtFileReader = null;
		;
		try {
			txtFileReader = new TxtFileReader(LscIniPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nuclidesLibrary = txtFileReader.nuclideLibrary;
/*		if (nuclidesLibrary != null)
			System.out.println("nuclidesLibrary has been got: "
					+ nuclidesLibrary);*/

		return nuclidesLibrary;
	}

	private ArrayList<NuclideVectorElement> getNuclideVectorListFromLscMdb() {
		ArrayList<NuclideVectorElement> alNuclideVector = new ArrayList<NuclideVectorElement>();
		try {
			String query = "select Nuclide, ReferenceNuclide, Ratio, RatioDate"
					+ " from NuclideVector where NuclideVector = ?;";
			PreparedStatement ps = lscConnection.getInstance().getConnection()
					.prepareStatement(query);
			ps.setString(1, nuclideVector);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			NuclideVectorElement nvElement;
			while (rs.next()) {
				nvElement = new NuclideVectorElement();
				nvElement.nuclide = rs.getString("Nuclide");
				nvElement.referenceNuclide = rs.getString("ReferenceNuclide");
				nvElement.ratio = rs.getFloat("Ratio");
				nvElement.date = rs.getDate("RatioDate");
				alNuclideVector.add(nvElement);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("getNuclideVectorList error");
		}
		return alNuclideVector;
	}

	private ArrayList<String> getNuclideLibraryListFromLscMdb() {
		// TODO Auto-generated method stub
		ArrayList<String> alNuclideLibraryList = new ArrayList<String>();
		try {
			String query = "select Nuclide"
					+ " from NuclideLibraryNuclides where LibraryFile = ?;";
			PreparedStatement ps = lscConnection.getInstance().getConnection()
					.prepareStatement(query);
			ps.setString(1, nuclidesLibrary);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			String nuclide;
			while (rs.next()) {
				nuclide = rs.getString("Nuclide");
				alNuclideLibraryList.add(nuclide);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("getNuclideLibraryList error");
		}
		return alNuclideLibraryList;
	}

	public void refreshData() {
		nuclideVector = null;
		nuclidesLibrary = null;
		alNuclideVectorList = null;
		nuclideVectorDate = null;
		alNuclideLibraryList = null;

		mainFrame.resetData();
		lscConnection = new MdbConnection(lscMdbPath);

		nuclideVector = getNvFromMst(mstPath);
		nuclidesLibrary = getNuclideLibrary(lscIniPath);
		alNuclideVectorList = getNuclideVectorListFromLscMdb();
		nuclideVectorDate = alNuclideVectorList
				.get(alNuclideVectorList.size() - 1).date.toString();
		alNuclideLibraryList = getNuclideLibraryListFromLscMdb();

		mainFrame.txtVectorName.setText(nuclideVector);
		mainFrame.txtVectorDate.setText(nuclideVectorDate);

		mainFrame.txtNuclideLibrary.setText(nuclidesLibrary);
		mainFrame.setNuclideVectorList(alNuclideVectorList);
		mainFrame.setNuclideLibraryList(alNuclideLibraryList);
		mainFrame.txtLastRefresh.setText(new Date().toLocaleString());
		lscConnection.closeConnection();
	}
}
