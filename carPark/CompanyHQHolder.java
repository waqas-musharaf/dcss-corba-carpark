package carPark;

/**
* carPark/CompanyHQHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CP_Start.idl
* Wednesday, 3 April 2019 14:25:22 o'clock BST
*/

public final class CompanyHQHolder implements org.omg.CORBA.portable.Streamable
{
  public carPark.CompanyHQ value = null;

  public CompanyHQHolder ()
  {
  }

  public CompanyHQHolder (carPark.CompanyHQ initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = carPark.CompanyHQHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    carPark.CompanyHQHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return carPark.CompanyHQHelper.type ();
  }

}