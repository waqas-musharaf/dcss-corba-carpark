package carPark;


/**
* carPark/CompanyHQOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CP_Start.idl
* Wednesday, 3 April 2019 14:25:22 o'clock BST
*/

public interface CompanyHQOperations 
{
  void raise_alarm (carPark.vehEvt evt);
  void register_localServer (String serverName);
  void remove_localServer (String serverName);
  boolean exists_localServer (String serverName);
} // interface CompanyHQOperations
