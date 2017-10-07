import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;


public class FrameTrainingManually {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		String[] columnNames = { "Nuclide", "Reference Nuclide", "Ratio" };
		String[][] arrayNuclides = new String[1][3];
		arrayNuclides[0][0] = "Nuclide1";
		arrayNuclides[0][1] = "Reference Nuclide1";
		arrayNuclides[0][2] = "Ratio1";
		JTable tableNuclides = new JTable(arrayNuclides, columnNames);
		scrollPane.setViewportView(tableNuclides);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		tableNuclides.setFillsViewportHeight(true);
		frame.setVisible(true);
	}

}
