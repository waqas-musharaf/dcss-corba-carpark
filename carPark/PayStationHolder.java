package carPark;

/**
* carPark/PayStationHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CP_Start.idl
* Wednesday, 3 April 2019 14:25:22 o'clock BST
*/

public final class PayStationHolder implements org.omg.CORBA.portable.Streamable
{
  public carPark.PayStation value = null;

  public PayStationHolder ()
  {
  }

  public PayStationHolder (carPark.PayStation initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = carPark.PayStationHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    carPark.PayStationHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return carPark.PayStationHelper.type ();
  }

}
