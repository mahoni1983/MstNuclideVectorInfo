import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connector to DB using Singleton pattern
 * @author User
 *
 */
public class MdbConnection {
	String CONNECTION_STRING =
		//	"jdbc:ucanaccess://C:/Users/Eugene/Desktop/db1.mdb";
			"jdbc:ucanaccess://"; // need to add path to mdb file
	String USER = "root";
	String PASS = "root";
//	private static MdbConnection instance = null;
	private MdbConnection instance = null;
	private Connection connection = null;

//	public static MdbConnection getInstance() 
	public MdbConnection getInstance()
	{
	//	if (instance == null)
	//		instance = new MdbConnection();
		return this;
	}

	public MdbConnection(String pathToMdb) {
		try {
			String pathToMdbSlashesChanged = pathToMdb.replace("\\", "/");
			
		//	Class.forName("com.mysql.jdbc.Driver");
			// immediatelyReleaseResources=true - to release connection immediately after closing connection
			connection = DriverManager.getConnection
					(CONNECTION_STRING + pathToMdbSlashesChanged + ";immediatelyReleaseResources=true");
			
		
		//	(CONNECTION_STRING, USER, PASS);
			System.out.println("MDB connected: " + pathToMdbSlashesChanged);
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
			connection = null;
			System.out.println("Connection to SQL server problems:" + e.getMessage());
		}
	}

	public Connection getConnection() {
		return connection;
	}
	public void closeConnection()
	{
		try {
			connection.close();
			System.out.println("connection.isClosed(): "  + connection.isClosed());
			System.out.println("mstMdbConnection closed in MdbConnection");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}