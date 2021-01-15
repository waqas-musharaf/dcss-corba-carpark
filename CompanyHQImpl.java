import carPark.CompanyHQPOA;
import carPark.vehEvt;
import java.util.ArrayList;

public class CompanyHQImpl extends CompanyHQPOA {

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
	
	CompanyHQImpl() {
		super();
		localServers = new ArrayList<>();
		serverInformation = new ArrayList<>();
	}

	public ArrayList<String> localServers;
	public ArrayList<serverInformation> serverInformation;

	@Override
	public void raise_alarm(vehEvt evt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void register_localServer(String serverName) {
		localServers.add(serverName);
		System.out.println("Local Server: '" + serverName + "' registered.");
	}

	@Override
	public void remove_localServer(String serverName) {
		if (localServers.contains(serverName)) {
			localServers.remove(localServers.indexOf(serverName));
			System.out.println("Local Server: '" + serverName + "' disconnected.");
		}
	}

	@Override
	public boolean exists_localServer(String serverName) {
		if (localServers.contains(serverName)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void add_serverInformation(serverInformation servInfo) {
		serverInformation.add(servInfo);
	}

	public ArrayList<serverInformation> return_serverInformation() {
		return serverInformation;
	}
}
