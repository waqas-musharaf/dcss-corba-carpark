package carPark;


/**
* carPark/LocalServerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CP_Start.idl
* Wednesday, 3 April 2019 14:25:22 o'clock BST
*/

public interface LocalServerOperations 
{
  String pid ();
  carPark.vehEvt[] log ();
  void set_spaces (int spaces);
  int return_freeSpaces ();
  void veh_in (carPark.vehEvt evt);
  void veh_out (carPark.vehEvt evt);
  void veh_pay (carPark.vehEvt evt);
  void add_entryGate (String gateName);
  void add_exitGate (String gateName);
  void add_payStation (String stationName);
  void rm_entryGate (String gateName);
  void rm_exitGate (String gateName);
  void rm_payStation (String stationName);
  boolean exists_entryGate (String gateName);
  boolean exists_exitGate (String gateName);
  boolean exists_payStation (String stationName);
  boolean toggle_entryGate (String gateName, boolean status);
  boolean toggle_exitGate (String gateName, boolean status);
  boolean toggle_payStation (String gateName, boolean status);
  boolean veh_inCarPark (String regNum);
  void add_cash (int cash);
  int return_cashTotal ();
} // interface LocalServerOperations
