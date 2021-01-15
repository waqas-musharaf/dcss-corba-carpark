import carPark.LocalServerHelper;
import carPark.vehEvt;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class ExitGate extends javax.swing.JFrame {
	private static final long serialVersionUID = 5207317343945869933L;
	public javax.swing.JButton btnExit;
	public javax.swing.JLabel lblCDT;
	public javax.swing.JLabel lblMachineName;
	public javax.swing.JLabel lblRegNum;
	public javax.swing.JLabel lblTitle;
	public javax.swing.JTextField tfRegNum;

	private SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
	private SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyyMMddHHmmss");

	public String machineName;

	public ExitGate(String[] args) {

		// Call methods:
		initComponents();
		liveDateTime();

		try {
			// Initialise the ORB:
			System.out.println("Initialising the ORB");
			ORB orb = ORB.init(args, null);

			// Get a reference to the naming service:
			org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references("NameService");

			// Use NamingContextExt which is part of the INS specification:
			NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);

			// Resolve the Name object reference in the naming service:
			if (args.length < 3) {
				System.err.println("Error: No naming service object reference parameter defined.");
				System.exit(1);
			}
			final String name = args[2];
			carPark.LocalServer LocalServer = LocalServerHelper.narrow(nameService.resolve_str(name));
			
			// Set machine name:
			if (args.length >= 4) {
				if (LocalServer.exists_exitGate(args[3])) {
					System.err.println("Error: Exit Gate: '" + args[3] + "' is already connected to Local Server: '"
							+ name + "'.");
					System.exit(1);
				} else {
					machineName = args[3];
					lblMachineName.setText("Machine Name: " + machineName);
					LocalServer.add_exitGate(machineName);
					System.out.println("Connected to Local Server: '" + name + "' at '" + LocalServer.pid() + "'.");
				}
			} else {
				System.err.println("Error: No machine name parameter defined.");
				System.exit(1);
			}

			btnExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!tfRegNum.getText().isEmpty()) {
						if (LocalServer.veh_inCarPark(tfRegNum.getText().toUpperCase())) {
							vehEvt evt = new carPark.vehEvt(tfRegNum.getText().toUpperCase(),
									sdfDateTime.format(new java.util.Date()), "", "exit");
							LocalServer.veh_out(evt);
							LocalServer.set_spaces(LocalServer.return_freeSpaces() + 1);
							JOptionPane.showMessageDialog(null,
									"Success: Vehicle with registration '" + tfRegNum.getText()
											+ "' recognised as exiting the car park.",
									"Success", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "Error: No vehicle with registration '"
									+ tfRegNum.getText() + "' is in the car park.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Error: Registration field cannot be blank.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					tfRegNum.setText("");
				}
			});
			
			// If instance closes, remove from connected Local Server
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					LocalServer.rm_exitGate(args[3]);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}

	}

	// Create GUI:
	private void initComponents() {
		lblTitle = new javax.swing.JLabel();
		lblRegNum = new javax.swing.JLabel();
		tfRegNum = new javax.swing.JTextField();
		btnExit = new javax.swing.JButton();
		lblCDT = new javax.swing.JLabel();
		lblMachineName = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		lblTitle.setFont(new java.awt.Font("Tahoma", 1, 24));
		lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblTitle.setText("Car Park Exit Gate");
		lblTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		lblRegNum.setFont(new java.awt.Font("Tahoma", 1, 14));
		lblRegNum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblRegNum.setText("Registration Number:");

		btnExit.setText("Exit Car Park");

		lblCDT.setFont(new java.awt.Font("Tahoma", 1, 12));
		lblCDT.setText("Current Date/Time:");

		lblMachineName.setFont(new java.awt.Font("Tahoma", 1, 12));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addContainerGap()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(lblMachineName).addComponent(lblCDT))
										.addGap(0, 0, Short.MAX_VALUE))
								.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(lblTitle,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)))
						.addContainerGap())
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addGap(0, 0, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(lblRegNum, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.PREFERRED_SIZE, 318,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
										layout.createSequentialGroup().addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
												.addComponent(tfRegNum).addComponent(btnExit,
														javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
												.addGap(82, 82, 82)))));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(lblTitle).addGap(35, 35, 35)
						.addComponent(lblRegNum).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(tfRegNum, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
						.addComponent(lblCDT).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(lblMachineName).addContainerGap()));
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
				new ExitGate(args).setVisible(true);
			}
		});
	}
}
