import carPark.CompanyHQHelper;
import carPark.LocalServerHelper;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import org.omg.CosNaming.*;

public class LocalServer {

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
	
	public static void ORBConnection(String args[]) {
		try {
			// Initialise the ORB:
			ORB orb = ORB.init(args, null);

			// Get reference to rootpoa and activate the POAManager:
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();

			// Create the LocalServer servant object:
			LocalServerImpl localServer = new LocalServerImpl();

			// Get object reference from the servant:
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(localServer);
			carPark.LocalServer lsref = LocalServerHelper.narrow(ref);

			// Get a reference to the naming service:
			org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references("NameService");

			// Use NamingContextExt which is part of the INS specification:
			NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);

			// Resolve the CompanyHQ object reference in the naming service:
			carPark.CompanyHQ CompanyHQ = CompanyHQHelper.narrow(nameService.resolve_str("hq"));

			// Bind the Name object in the naming service:
			if (args.length >= 3) {
				final String name = args[2];
				if (CompanyHQ.exists_localServer(name)) {
					System.err.println("Error: Local Server: '" + name + "' is already connected to Company HQ.");
					System.exit(1);
				} else {
					// Obtain and validate the 'spaces' variable from args[]:
					int spaces = -1;
					if (args.length >= 4) {
						if (args[3] != null && args[3] != "") {
							try {
								spaces = Integer.parseInt(args[3]);
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					}
					if (spaces <= 0) {
						System.err.println("Error: Invalid spaces parameter defined.");
						System.exit(1);
					} else {
						NameComponent[] nameComponent = nameService.to_name(name);
						nameService.rebind(nameComponent, lsref);
						CompanyHQ.register_localServer(name);
						localServer.set_spaces(spaces);
					}
				}
			} else {
				System.err.println("Error: No machine name parameter defined.");
				System.exit(1);
			}

			// If instance closes, remove from connected Company HQ:
			Runtime.getRuntime().addShutdownHook(new Thread() {
				public void run() {
					CompanyHQ.remove_localServer(args[2]);
				}
			});

			// Wait for invocations from clients:
			System.out.println(
					"Server: '" + args[2] + "' at '" + localServer.pid() + "' is ready, awaiting client invocation...");
			orb.run();

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
	}

	public static void main(String[] args) {
		ORBConnection(args);
	}
}
