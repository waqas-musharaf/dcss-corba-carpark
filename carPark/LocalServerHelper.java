package carPark;


/**
* carPark/LocalServerHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CP_Start.idl
* Wednesday, 3 April 2019 14:25:22 o'clock BST
*/

abstract public class LocalServerHelper
{
  private static String  _id = "IDL:carPark/LocalServer:1.0";

  public static void insert (org.omg.CORBA.Any a, carPark.LocalServer that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static carPark.LocalServer extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (carPark.LocalServerHelper.id (), "LocalServer");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static carPark.LocalServer read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_LocalServerStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, carPark.LocalServer value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static carPark.LocalServer narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof carPark.LocalServer)
      return (carPark.LocalServer)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      carPark._LocalServerStub stub = new carPark._LocalServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static carPark.LocalServer unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof carPark.LocalServer)
      return (carPark.LocalServer)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      carPark._LocalServerStub stub = new carPark._LocalServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
