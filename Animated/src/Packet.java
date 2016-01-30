
public class Packet extends Object {
   String proc, arriv, serv, alg;

   public Packet(String p, String a, String s, String al) {
      proc=p;
      arriv=a;
      serv=s;
      alg=al;
   } // constructor, packet-er

   // interface functions
   public String getProc() { return proc; }
   public String getArriv() { return arriv; }
   public String getServ() { return serv; }
   public String getAlg() { return alg; }

} // packet input information
