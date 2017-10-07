import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame {
	final String ABOUT_MESSAGE = "Nuclide vector information of MST programs v1.0\n"
			+ "The program is to work with Monitoring Stations (MST) of Ignalina Nuclear Power Plant. \n"
			+ "It gets and shows short information about current nuclide vector and measuring nuclides. \n"
			+ "The program gets paths for MST files from \"Nuclide vector information of MST programs.ini\" in the same folder.\n"
			+ "Lines for paths must start with:\n"
			+ "LSC.ini location: \n"
			+ "MST_iniparam.mdb location: \n"
			+ "LSC_.mdb location: \n"
			+ "Example:\n"
			+ "LSC.ini location: c:\\Program Files\\LSC\\LSC.ini\n"
			+ "Case not sensitive. All other lines are ignored. The order is not important."
			+ "\n"
			+ "The program reads the files to get information about nuclide vector and measuring nuclides then shows in appropriate fields.\n"
			+ "The files are read on the original paths again upon refresh button.\n"
			+ "\n"
			+ "Jevgenij Kariagin \n"
			+ "2017";
	JFrame frame;
	Program program;
	
	private JPanel contentPane;
	public JTextField txtVectorName;
	private JPanel jpanel2;
	public JTextField txtVectorDate;
	private JTable tableNuclides;
	private JScrollPane scrollPaneNuclides;
	private JPanel panelCenter;
	private JPanel jpanelSouth;
	public JTextArea txtAreaMeasuredNuclides;
	public JTextField txtNuclideLibrary;
	public JTextField txtLastRefresh;
	private JButton btnRefresh;
	private JButton btnAbout;
	
	public ArrayList <NuclideVectorElement> alNuclideVectorList;
	public ArrayList <String> alNuclideLibraryList;
	
	private String[][] arrayNuclideVectorListForTable;
	private String[] columnNames;

	MainFrame(final Program program) {
		this.program = program;
		frame = new JFrame("Loading..");

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 500, 400);
	
		frame.setVisible(true);
	}
	
	public void loadContent()
	{
		frame.setTitle("Nuclide vector information of MST programs");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		jpanel2 = new JPanel();
		contentPane.add(jpanel2, BorderLayout.NORTH);

		txtVectorName = new JTextField();
		txtVectorName.setColumns(12);
		txtVectorName.setBorder(new TitledBorder("Nuclide Vector"));
		txtVectorName.setEditable(false);
		jpanel2.add(txtVectorName);

		txtVectorDate = new JTextField();
		txtVectorDate.setColumns(12);
		txtVectorDate.setBorder(new TitledBorder("Nuclide Vector Date"));
		txtVectorDate.setEditable(false);
		jpanel2.add(txtVectorDate);
		

		
		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//showMessage("Nuclide vector information for MST programs refresh", "Refreshing data");
				program.refreshData();
			}
		});
		jpanel2.add(btnRefresh);
		
		btnAbout = new JButton("About");
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showMessage("Nuclide vector information of MST programs", ABOUT_MESSAGE);
			}
		});
		jpanel2.add(btnAbout);

		columnNames = new String []{"No", "Nuclide", "Reference Nuclide", "Ratio" };
/*
		String[][] arrayNuclides = new String[1][3];
		arrayNuclides[0][0] = "Nuclide not set";
		arrayNuclides[0][1] = "Reference Nuclide not set";
		arrayNuclides[0][2] = "Ratio not set";
		ArrayList<NuclideVectorElement> alNuclideVectorListEmpty = new ArrayList<NuclideVectorElement>();
		NuclideVectorElement nveEmpty = new NuclideVectorElement("Nuclide not set", "Reference Nuclide not set", 0, null);
		alNuclideVectorListEmpty.add(nveEmpty);
		setNuclideVectorList(alNuclideVectorListEmpty);*/
		
		ArrayList<NuclideVectorElement> alNuclideVectorListEmpty2 = new ArrayList<NuclideVectorElement>();
		NuclideVectorElement nveEmpty2 = new NuclideVectorElement("-", "-", 0, null);
		alNuclideVectorListEmpty2.add(nveEmpty2);
		setNuclideVectorList(alNuclideVectorListEmpty2);
		
		// forming South part
		jpanelSouth = new JPanel();
		
		txtNuclideLibrary = new JTextField();
		txtNuclideLibrary.setColumns(12);
		txtNuclideLibrary.setBorder(new TitledBorder("Nuclide Library"));
		txtNuclideLibrary.setEditable(false);
		jpanelSouth.add(txtNuclideLibrary);
		
		txtAreaMeasuredNuclides = new JTextArea ();
		txtAreaMeasuredNuclides.setColumns(12);
		txtAreaMeasuredNuclides.setBorder(new TitledBorder("Measured Nuclides"));
		txtAreaMeasuredNuclides.setEditable(false);
		jpanelSouth.add(txtAreaMeasuredNuclides);
		
		
		txtLastRefresh = new JTextField();
		txtLastRefresh.setColumns(12);
		txtLastRefresh.setBorder(new TitledBorder("Last refreshed"));
		txtLastRefresh.setEditable(false);
		jpanelSouth.add(txtLastRefresh);
			
		contentPane.add(jpanelSouth, BorderLayout.SOUTH);	
	}
	
	public void setNuclideVectorList(ArrayList <NuclideVectorElement> alNuclideVectorList)
	{
		if (scrollPaneNuclides != null)
			contentPane.remove(scrollPaneNuclides);
		this.alNuclideVectorList = alNuclideVectorList;
	//	alNuclideVectorList.sort(NuclideVectorElement);
		arrayNuclideVectorListForTable = new String[alNuclideVectorList.size()][4];
		int i=0;
		for (NuclideVectorElement nve : alNuclideVectorList)
		{
			arrayNuclideVectorListForTable[i][0]=Integer.toString(i+1);
			arrayNuclideVectorListForTable[i][1]=nve.nuclide;
			arrayNuclideVectorListForTable[i][2]=nve.referenceNuclide;
			arrayNuclideVectorListForTable[i][3]=Float.toString(nve.ratio);
			i++;
		}
		//String[] columnNames = { "Nuclide", "Reference Nuclide", "Ratio" };
		tableNuclides = new JTable(new NonEditableTableModel( arrayNuclideVectorListForTable, columnNames));
		//contentPane.get
		if (scrollPaneNuclides != null)
			contentPane.remove(scrollPaneNuclides);
		//tableNuclides.set
//		tableNuclides.setModel(NonEditableTableModel);
		scrollPaneNuclides = new JScrollPane(tableNuclides);
		scrollPaneNuclides.setViewportView(tableNuclides);
		
		contentPane.add(scrollPaneNuclides, BorderLayout.CENTER);
		contentPane.revalidate();
	//	contentPane.revalidate();
	}
	
	public void setNuclideLibraryList(ArrayList <String> alNuclideLibraryList)
	{
		this.alNuclideLibraryList = alNuclideLibraryList;
		StringBuilder nuclideList = new StringBuilder();
		for (String nuclide : alNuclideLibraryList)
			nuclideList.append(nuclide + "\n");
			
		nuclideList.deleteCharAt(nuclideList.length()-1);
		this.txtAreaMeasuredNuclides.setText(nuclideList.toString());
	}
	
	public void showMessage(String title, String message) {
		if (title == "")
			JOptionPane.showMessageDialog(frame, message);
		else
			JOptionPane.showMessageDialog(frame, message, title, JOptionPane.PLAIN_MESSAGE);
	}
	
	public void resetData()
	{
		txtVectorName.setText("");
		txtVectorDate.setText("");
		tableNuclides = null;		
		txtAreaMeasuredNuclides.setText("");
		txtNuclideLibrary.setText("");
		txtLastRefresh.setText("");	
		alNuclideVectorList = null;
		alNuclideLibraryList = null;
		arrayNuclideVectorListForTable = null;
		
	}
}
