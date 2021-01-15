package carPark;


/**
* carPark/LocalServerPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CP_Start.idl
* Wednesday, 3 April 2019 14:25:22 o'clock BST
*/

public abstract class LocalServerPOA extends org.omg.PortableServer.Servant
 implements carPark.LocalServerOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("_get_pid", new java.lang.Integer (0));
    _methods.put ("_get_log", new java.lang.Integer (1));
    _methods.put ("set_spaces", new java.lang.Integer (2));
    _methods.put ("return_freeSpaces", new java.lang.Integer (3));
    _methods.put ("veh_in", new java.lang.Integer (4));
    _methods.put ("veh_out", new java.lang.Integer (5));
    _methods.put ("veh_pay", new java.lang.Integer (6));
    _methods.put ("add_entryGate", new java.lang.Integer (7));
    _methods.put ("add_exitGate", new java.lang.Integer (8));
    _methods.put ("add_payStation", new java.lang.Integer (9));
    _methods.put ("rm_entryGate", new java.lang.Integer (10));
    _methods.put ("rm_exitGate", new java.lang.Integer (11));
    _methods.put ("rm_payStation", new java.lang.Integer (12));
    _methods.put ("exists_entryGate", new java.lang.Integer (13));
    _methods.put ("exists_exitGate", new java.lang.Integer (14));
    _methods.put ("exists_payStation", new java.lang.Integer (15));
    _methods.put ("toggle_entryGate", new java.lang.Integer (16));
    _methods.put ("toggle_exitGate", new java.lang.Integer (17));
    _methods.put ("toggle_payStation", new java.lang.Integer (18));
    _methods.put ("veh_inCarPark", new java.lang.Integer (19));
    _methods.put ("add_cash", new java.lang.Integer (20));
    _methods.put ("return_cashTotal", new java.lang.Integer (21));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // carPark/LocalServer/_get_pid
       {
         String $result = null;
         $result = this.pid ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // carPark/LocalServer/_get_log
       {
         carPark.vehEvt $result[] = null;
         $result = this.log ();
         out = $rh.createReply();
         carPark.vehEvtLogHelper.write (out, $result);
         break;
       }

       case 2:  // carPark/LocalServer/set_spaces
       {
         int spaces = in.read_long ();
         this.set_spaces (spaces);
         out = $rh.createReply();
         break;
       }

       case 3:  // carPark/LocalServer/return_freeSpaces
       {
         int $result = (int)0;
         $result = this.return_freeSpaces ();
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       case 4:  // carPark/LocalServer/veh_in
       {
         carPark.vehEvt evt = carPark.vehEvtHelper.read (in);
         this.veh_in (evt);
         out = $rh.createReply();
         break;
       }

       case 5:  // carPark/LocalServer/veh_out
       {
         carPark.vehEvt evt = carPark.vehEvtHelper.read (in);
         this.veh_out (evt);
         out = $rh.createReply();
         break;
       }

       case 6:  // carPark/LocalServer/veh_pay
       {
         carPark.vehEvt evt = carPark.vehEvtHelper.read (in);
         this.veh_pay (evt);
         out = $rh.createReply();
         break;
       }

       case 7:  // carPark/LocalServer/add_entryGate
       {
         String gateName = in.read_string ();
         this.add_entryGate (gateName);
         out = $rh.createReply();
         break;
       }

       case 8:  // carPark/LocalServer/add_exitGate
       {
         String gateName = in.read_string ();
         this.add_exitGate (gateName);
         out = $rh.createReply();
         break;
       }

       case 9:  // carPark/LocalServer/add_payStation
       {
         String stationName = in.read_string ();
         this.add_payStation (stationName);
         out = $rh.createReply();
         break;
       }

       case 10:  // carPark/LocalServer/rm_entryGate
       {
         String gateName = in.read_string ();
         this.rm_entryGate (gateName);
         out = $rh.createReply();
         break;
       }

       case 11:  // carPark/LocalServer/rm_exitGate
       {
         String gateName = in.read_string ();
         this.rm_exitGate (gateName);
         out = $rh.createReply();
         break;
       }

       case 12:  // carPark/LocalServer/rm_payStation
       {
         String stationName = in.read_string ();
         this.rm_payStation (stationName);
         out = $rh.createReply();
         break;
       }

       case 13:  // carPark/LocalServer/exists_entryGate
       {
         String gateName = in.read_string ();
         boolean $result = false;
         $result = this.exists_entryGate (gateName);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 14:  // carPark/LocalServer/exists_exitGate
       {
         String gateName = in.read_string ();
         boolean $result = false;
         $result = this.exists_exitGate (gateName);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 15:  // carPark/LocalServer/exists_payStation
       {
         String stationName = in.read_string ();
         boolean $result = false;
         $result = this.exists_payStation (stationName);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 16:  // carPark/LocalServer/toggle_entryGate
       {
         String gateName = in.read_string ();
         boolean status = in.read_boolean ();
         boolean $result = false;
         $result = this.toggle_entryGate (gateName, status);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 17:  // carPark/LocalServer/toggle_exitGate
       {
         String gateName = in.read_string ();
         boolean status = in.read_boolean ();
         boolean $result = false;
         $result = this.toggle_exitGate (gateName, status);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 18:  // carPark/LocalServer/toggle_payStation
       {
         String gateName = in.read_string ();
         boolean status = in.read_boolean ();
         boolean $result = false;
         $result = this.toggle_payStation (gateName, status);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 19:  // carPark/LocalServer/veh_inCarPark
       {
         String regNum = in.read_string ();
         boolean $result = false;
         $result = this.veh_inCarPark (regNum);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       case 20:  // carPark/LocalServer/add_cash
       {
         int cash = in.read_long ();
         this.add_cash (cash);
         out = $rh.createReply();
         break;
       }

       case 21:  // carPark/LocalServer/return_cashTotal
       {
         int $result = (int)0;
         $result = this.return_cashTotal ();
         out = $rh.createReply();
         out.write_long ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:carPark/LocalServer:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public LocalServer _this() 
  {
    return LocalServerHelper.narrow(
    super._this_object());
  }

  public LocalServer _this(org.omg.CORBA.ORB orb) 
  {
    return LocalServerHelper.narrow(
    super._this_object(orb));
  }


} // class LocalServerPOA