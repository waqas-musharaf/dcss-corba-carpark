import carPark.LocalServerPOA;
import carPark.vehEvt;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;

public class LocalServerImpl extends LocalServerPOA {
	
	LocalServerImpl() {
		super();
		regNums = new ArrayList<>();
		log = new ArrayList<>();
		entryGates = new ArrayList<>();
		exitGates = new ArrayList<>();
		payStations = new ArrayList<>();
		cashTotal = 0;
	}
	
	public int _spaces;
	public ArrayList<String> regNums;
	public ArrayList<vehEvt> log;
	public ArrayList<String> entryGates;
	public ArrayList<String> exitGates;
	public ArrayList<String> payStations;
	public int cashTotal;
	
	public static String processID = ManagementFactory.getRuntimeMXBean().getName();

	@Override
	public String pid() {
		return processID;
	}

	@Override
	public vehEvt[] log() {
		vehEvt[] logArray = log.toArray(new vehEvt[log.size()]);
		return logArray;
	}
	
	@Override
	public void set_spaces(int spaces) {
		_spaces = spaces;		
	}

	@Override
	public int return_freeSpaces() {
		return _spaces;
	}
	
	@Override
	public void veh_in(vehEvt evt) {
		regNums.add(evt.regNum);
		log.add(evt);
		System.out.println("Registration Number: '" + evt.regNum + "' added.");
	}

	@Override
	public void veh_out(vehEvt evt) {
		if (regNums.contains(evt.regNum)) {
			regNums.remove(regNums.indexOf(evt.regNum));
			log.add(evt);
			System.out.println("Registration Number: '" + evt.regNum + "' removed.");
		} else {
			System.err.println("Error: Registration Number: '" + evt.regNum + "' not found.");
		}
	}

	@Override
	public void veh_pay(vehEvt evt) {
		if (regNums.contains(evt.regNum)) {
			log.add(evt);
			System.out.println("Registration Number: '" + evt.regNum + "' paid for.");
		} else {
			System.err.println("Error: Registration Number: '" + evt.regNum + "' not found.");
		}
	}

	@Override
	public void add_entryGate(String gateName) {
		entryGates.add(gateName);
		System.out.println("Entry Gate: '" + gateName + "' connected.");
	}

	@Override
	public void add_exitGate(String gateName) {
		exitGates.add(gateName);
		System.out.println("Exit Gate: '" + gateName + "' connected.");
	}

	@Override
	public void add_payStation(String stationName) {
		payStations.add(stationName);
		System.out.println("Pay Station: '" + stationName + "' connected.");
	}

	@Override
	public void rm_entryGate(String gateName) {
		if (entryGates.contains(gateName)) {
			entryGates.remove(entryGates.indexOf(gateName));
			System.out.println("Entry Gate: '" + gateName + "' disconnected.");
		}
	}

	@Override
	public void rm_exitGate(String gateName) {
		if (exitGates.contains(gateName)) {
			exitGates.remove(exitGates.indexOf(gateName));
			System.out.println("Exit Gate: '" + gateName + "' disconnected.");
		}
	}

	@Override
	public void rm_payStation(String stationName) {
		if (payStations.contains(stationName)) {
			payStations.remove(payStations.indexOf(stationName));
			System.out.println("Pay Station: '" + stationName + "' disconnected.");
		}
	}

	@Override
	public boolean exists_entryGate(String gateName) {
		if (entryGates.contains(gateName)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean exists_exitGate(String gateName) {
		if (exitGates.contains(gateName)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean exists_payStation(String stationName) {
		if (payStations.contains(stationName)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean toggle_entryGate(String gateName, boolean status) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean toggle_exitGate(String gateName, boolean status) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean toggle_payStation(String gateName, boolean status) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean veh_inCarPark(String regNum) {
		if (regNums.contains(regNum)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void add_cash(int cash) {
		cashTotal += cash;
	}
	
	@Override
	public int return_cashTotal() {
		return cashTotal;
	}
}
