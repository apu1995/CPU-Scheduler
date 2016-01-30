
import java.util.Vector;

abstract public class Scheduler extends Object {
   Vector readyQ, finishQ, Q;
   int clock;
   process P, T;
   boolean idle;
   public Thread thread;

   statsFrame st;
   animeFrame an;
   inputFrame in;


   public Scheduler(Vector q, statsFrame s, animeFrame a, inputFrame i, int c) {
      Q = q;
      st = s;
      an = a;
      in = i;
      clock = c-1; // stagger for run loop
      idle = true;
      readyQ = new Vector(1,1);
      finishQ = new Vector(1,1);      
   } // constructor

   public process processready(int tick) {
      for (int j=0; j<Q.size(); j++)
         if (((process)(Q.elementAt(j))).getArrival() <= tick)
            return (process)Q.elementAt(j);
      return null;
   } // clock

   public void resetQ() {
      readyQ.setSize(0);
      finishQ.setSize(0);
      Q.setSize(0);
      in.resetGUI();
   } // reset all queues

} // Scheduler class
