/*****************************************************************
                        Shortest Process Next
PURPOSE: This is the algorithm for Shortest Process Next CPU scheduling. It 
inherits data and functionalities from base class Scheduler.
*****************************************************************/
import java.util.Vector;

public class SPN extends Scheduler implements Runnable {

   public SPN(Vector q, statsFrame s, animeFrame a, inputFrame i, int c) {
      super(q,s,a,i,c);
    
      thread = new Thread(this,"SPN");
      thread.start();
   } // constructor

   
   public void run() {
      int all=Q.size();     

      do {
         clock++;
         T = processready(clock);
         if (T != null) {
            readyQ.addElement(T);
            Q.removeElement(T);
            an.upstatus("Time "+T.getArrival()+":Process "+T.getName()+" ready.");
            try { Thread.sleep(1000); } catch (InterruptedException e) {};
         } // put in ready queue
         if (idle) {
            if (readyQ.size()==0)
               continue;
            idle = false;
            P = (process)readyQ.firstElement();
            int sp = P.getService();
            for (int j=1; j<readyQ.size(); j++)
               if (sp > ((process)readyQ.elementAt(j)).getService()) {
                  P = (process)readyQ.elementAt(j);
                  sp = P.getService();
               } // find shortest process next
            readyQ.removeElement(P);            
         } // put in run state
         P.servicing();
         an.drawbar(P,clock);
         an.upstatus("Time "+clock+":Serving process "+P.getName()+".");
         try { Thread.sleep(1000); } catch (InterruptedException ex) {};
         if (P.getTminus()==0) {
            an.upstatus("Time "+(clock+1)+":Process "+P.getName()+" done.");
            try { Thread.sleep(1000); } catch (InterruptedException e) {};
            P.report(clock+1); // anticipate completion
            finishQ.addElement(P);
            idle = true;
         } // put in finish queue
      } while (finishQ.size()<all);
      an.upstatus("Algorithm finished.");
      st.report(finishQ,"Shortest Process Next");
      in.resetGUI();
   } // run thread

} // SPN class
