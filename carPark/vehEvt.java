package carPark;


/**
* carPark/vehEvt.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CP_Start.idl
* Wednesday, 3 April 2019 14:25:21 o'clock BST
*/

public final class vehEvt implements org.omg.CORBA.portable.IDLEntity
{
  public String regNum = null;
  public String dateTime = null;
  public String paidUntil = null;
  public String evtType = null;

  public vehEvt ()
  {
  } // ctor

  public vehEvt (String _regNum, String _dateTime, String _paidUntil, String _evtType)
  {
    regNum = _regNum;
    dateTime = _dateTime;
    paidUntil = _paidUntil;
    evtType = _evtType;
  } // ctor

} // class vehEvt
