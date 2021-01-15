import carPark.LocalServerHelper;
import carPark.vehEvt;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JOptionPane;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class PayStation extends javax.swing.JFrame {
	private static final long serialVersionUID = 3999136200583814944L;
	public javax.swing.JButton btnPay;
	public javax.swing.JLabel lblCDT;
	public javax.swing.JLabel lblMachineName;
	public javax.swing.JLabel lblRegNum;
	public javax.swing.JLabel lblTitle;
	public javax.swing.JTextField tfRegNum;

	private SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
	private SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyyMMddHHmmss");
	private SimpleDateFormat sdfFormattedDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public String machineName;

	public PayStation(String args[]) {

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
				if (LocalServer.exists_payStation(args[3])) {
					System.err.println("Error: Pay Station: '" + args[3] + "' is already connected to Local Server: '"
							+ name + "'.");
					System.exit(1);
				} else {
					machineName = args[3];
					lblMachineName.setText("Machine Name: " + machineName);
					LocalServer.add_payStation(machineName);
					System.out.println("Connected to Local Server: '" + name + "' at '" + LocalServer.pid() + "'.");
				}
			} else {
				System.err.println("Error: No machine name parameter defined.");
				System.exit(1);
			}

			btnPay.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!tfRegNum.getText().isEmpty()) {
						if (LocalServer.veh_inCarPark(tfRegNum.getText().toUpperCase())) {
							String input = JOptionPane.showInputDialog(null,
									"Please enter the duration (in hours) you would like to park for (min: 1hr, max 24hrs): ",
									"Parking", JOptionPane.OK_CANCEL_OPTION);
							if (input != null) {
								if (input.matches("^[0-9]*$") && !input.equals("") && input.length() <= 2) {
									if (Integer.valueOf(input) <= 24 && Integer.valueOf(input) >= 1) {
										vehEvt recentRegEntryEvt = null;
										vehEvt recentRegPayEvt = null;
										for (vehEvt evt : LocalServer.log()) {
											if (evt.regNum.equals(tfRegNum.getText().toUpperCase())
													&& evt.evtType.equals("entry")) {
												recentRegEntryEvt = evt;
											} else if (evt.regNum.equals(tfRegNum.getText().toUpperCase())
													&& evt.evtType.equals("pay")) {
												recentRegPayEvt = evt;
											}
										}
										if (recentRegEntryEvt != null) {
											try {
												Date entryDT = sdfDateTime.parse(recentRegEntryEvt.dateTime);
												Date expireDT = new Date();
												boolean cont = true;
												if (recentRegPayEvt != null && sdfDateTime
														.parse(recentRegPayEvt.paidUntil).after(new java.util.Date())) {
													int confirm = JOptionPane.showOptionDialog(null,
															"You already have a valid ticket for this vehicle. Would you like to extend its validity?",
															"Update Ticket?", JOptionPane.YES_NO_OPTION,
															JOptionPane.QUESTION_MESSAGE, null, null, null);
													if (confirm == JOptionPane.YES_OPTION) {
														expireDT = Date.from(LocalDateTime
																.ofInstant(sdfDateTime.parse(recentRegPayEvt.paidUntil)
																		.toInstant(), ZoneId.systemDefault())
																.plusHours(Integer.valueOf(input))
																.atZone(ZoneId.systemDefault()).toInstant());
													} else {
														cont = false;
													}
												} else {
													expireDT = Date.from(LocalDateTime
															.ofInstant(entryDT.toInstant(), ZoneId.systemDefault())
															.plusHours(Integer.valueOf(input))
															.atZone(ZoneId.systemDefault()).toInstant());
												}
												if (cont) {
													vehEvt evt = new carPark.vehEvt(tfRegNum.getText().toUpperCase(),
															sdfDateTime.format(entryDT), sdfDateTime.format(expireDT),
															"pay");
													int confirm = JOptionPane.showOptionDialog(null, "Please insert £"
															+ Integer.valueOf(input)
															+ " into the machine and press 'OK' to dispense ticket.",
															"Pay and Print Ticket", JOptionPane.OK_CANCEL_OPTION,
															JOptionPane.INFORMATION_MESSAGE, null, null, null);
													if (confirm == JOptionPane.OK_OPTION) {
														LocalServer.veh_pay(evt);
														LocalServer.add_cash(Integer.valueOf(input));
														printTicket(evt, Integer.valueOf(input));
														JOptionPane.showMessageDialog(null,
																"Ticket has been printed. Thank you for parking at our car park!",
																"Success", JOptionPane.INFORMATION_MESSAGE);
													}
												}
											} catch (ParseException e1) {
												e1.printStackTrace();
											}
										} else {
											JOptionPane.showMessageDialog(null,
													"Error: Car entry not present in vehicle event log.", "Error",
													JOptionPane.ERROR_MESSAGE);
										}
									} else {
										JOptionPane.showMessageDialog(null,
												"Error: Invalid input. You cannot park for less than 1 hour, or for more than 24 hours. Please try again.",
												"Error", JOptionPane.ERROR_MESSAGE);
									}
								} else {
									JOptionPane.showMessageDialog(null,
											"Error: Invalid input. Please try again using only numerical input. Please note, you cannot park for less than 1 hour, or for more than 24 hours.",
											"Error", JOptionPane.ERROR_MESSAGE);
								}
							}
						} else {
							JOptionPane.showMessageDialog(null, "Error: Vehicle with registration: '"
									+ tfRegNum.getText()
									+ "' has not been detected as entering the car park. Please enter the car park and try again.",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"Error: Registration field cannot be blank. Please try again.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
					tfRegNum.setText("");
				}
			});

			// If instance closes, remove from connected Local Server:
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					LocalServer.rm_payStation(args[3]);
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
		btnPay = new javax.swing.JButton();
		lblCDT = new javax.swing.JLabel();
		lblMachineName = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		lblTitle.setFont(new java.awt.Font("Tahoma", 1, 24));
		lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblTitle.setText("Car Park Pay Station");
		lblTitle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		lblRegNum.setFont(new java.awt.Font("Tahoma", 1, 14));
		lblRegNum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblRegNum.setText("Registration Number:");

		btnPay.setText("Pay For Ticket");

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
												.addComponent(tfRegNum).addComponent(btnPay,
														javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
												.addGap(82, 82, 82)))));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(lblTitle).addGap(35, 35, 35)
						.addComponent(lblRegNum).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(tfRegNum, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
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

	// Create new Ticket file:
	public void printTicket(vehEvt evt, Integer hoursPaid) throws ParseException {
		PrintWriter w;
		String path = "/home/waqas/DCSS-Assignment-2/Tickets/Ticket_" + evt.regNum + "_"
				+ sdfDateTime.format(new java.util.Date()) + ".txt";
		File file = new File(path);
		String arrivedDT = sdfFormattedDateTime.format(sdfDateTime.parse(evt.dateTime));
		String expiryDT = sdfFormattedDateTime.format(sdfDateTime.parse(evt.paidUntil));
		file.getParentFile().mkdirs();
		try {
			w = new PrintWriter(path, "UTF-8");
			w.println("----------------------------------------");
			w.println("Car Parking Ticket:");
			w.println("----------------------------------------");
			w.println("Car Reg: '" + evt.regNum + "'");
			w.println("Arrived At: " + arrivedDT + "");
			w.println("Amount Paid (this transaction): £" + hoursPaid);
			w.println("Paid For (this transaction): " + hoursPaid + " hours");
			w.println("Ticket Valid Until: " + expiryDT + "");
			w.println("----------------------------------------");
			w.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new PayStation(args).setVisible(true);
			}
		});
	}
}
