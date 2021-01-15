import carPark.CompanyHQHelper;
import java.io.IOException;
import java.lang.Object;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import org.omg.CosNaming.*;

public class CompanyHQ extends javax.swing.JFrame {
	private static final long serialVersionUID = 2356383751942729166L;
	private static final int PORT = 64000;
	@SuppressWarnings("unused")
	private static ServerSocket soc;

	public javax.swing.JLabel lblCDT;
	public javax.swing.JLabel lblTitle;
	public javax.swing.JPanel pnlMainPanel;
	public javax.swing.JScrollPane spServersSP;
	public javax.swing.JTable tblServers;

	private SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
	
	public ArrayList<serverInformation> serverArray = new ArrayList<>();
	
	public class serverInformation {
		String serverName;
		int serverSpaces;
		int serverFreeSpaces;
		int serverCashTotal;
		
		public serverInformation(String sn, int ss, int sfs, int sct) {
			this.serverName = sn;
			this.serverSpaces = ss;
			this.serverFreeSpaces = sfs;
			this.serverCashTotal = sct;
		}
	}

	public CompanyHQ(String[] args) {
		// Call methods:
		initComponents();
		liveDateTime();
	}

	public static void ORBConnection(String[] args) {
		// Ensure only one instance of Company HQ can run at a time:
		try {
			soc = new ServerSocket(PORT, 0, InetAddress.getLocalHost());
		} catch (BindException e) {
			System.err.println("Error: Company HQ is already running.");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(e);
			System.exit(1);
		}

		try {
			// Initialise the ORB:
			ORB orb = ORB.init(args, null);

			// Get reference to rootpoa and activate the POAManager:
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();

			// Create the CompanyHQ servant object:
			CompanyHQImpl companyHQ = new CompanyHQImpl();

			// Get object reference from the servant:
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(companyHQ);
			carPark.CompanyHQ hqref = CompanyHQHelper.narrow(ref);

			// Get a reference to the naming service:
			org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references("NameService");

			// Use NamingContextExt which is part of the INS specification:
			NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);

			// Bind the Name object in the naming service:
			final String name = "hq";
			NameComponent[] nameComponent = nameService.to_name(name);
			nameService.rebind(nameComponent, hqref);

			// Wait for invocations from local servers:
			System.out.println("Company HQ is ready, awaiting local server invocation...");
			orb.run();

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
	}

	// Create GUI:
	private void initComponents() {

		lblTitle = new javax.swing.JLabel();
		lblCDT = new javax.swing.JLabel();
		pnlMainPanel = new javax.swing.JPanel();
		spServersSP = new javax.swing.JScrollPane();
		tblServers = new javax.swing.JTable();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		lblTitle.setFont(new java.awt.Font("Tahoma", 1, 24));
		lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblTitle.setText("Car Park Headquarters");
		lblTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		lblCDT.setFont(new java.awt.Font("Tahoma", 1, 12));
		lblCDT.setText("Current Date/Time:");

		tblServers.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "Server Name", "Total Spaces", "Spaces Available", "Cash Total Today" }));
		spServersSP.setViewportView(tblServers);

		javax.swing.GroupLayout pnlMainPanelLayout = new javax.swing.GroupLayout(pnlMainPanel);
		pnlMainPanel.setLayout(pnlMainPanelLayout);
		pnlMainPanelLayout.setHorizontalGroup(pnlMainPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 641, Short.MAX_VALUE)
				.addGroup(pnlMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(spServersSP, javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)));
		pnlMainPanelLayout.setVerticalGroup(pnlMainPanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 457, Short.MAX_VALUE)
				.addGroup(pnlMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(spServersSP, javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
						.addGroup(layout.createSequentialGroup().addComponent(lblCDT).addGap(0, 0, Short.MAX_VALUE))
						.addComponent(pnlMainPanel, javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE))
				.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(lblTitle)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(pnlMainPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
						.addComponent(lblCDT).addContainerGap()));

		pack();
		setResizable(false);
	}

	// Thread for showing a live date time on the GUI:
	public void liveDateTime() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				while (true) {
					java.util.Date dateTime = new java.util.Date();
					lblCDT.setText("Current Date Time: " + sdfDate.format(dateTime) + " " + sdfTime.format(dateTime));
					try {
						Thread.sleep(999);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		Thread t = new Thread(runnable);
		t.start();
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CompanyHQ(args).setVisible(true);
			}
		});
		ORBConnection(args);
	}
}
