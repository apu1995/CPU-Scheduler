import java.util.Vector;

public class RR4 extends Scheduler implements Runnable {

   public RR4(Vector q, statsFrame s, animeFrame a, inputFrame i, int c) {
      super(q,s,a,i,c);      
    
      thread = new Thread(this,"RR4");
      thread.start();
   } // constructor

   
   public void run() {
      int all=Q.size(), interval=0, next=0;
      boolean interrupt = false;      

      do {
         clock++;
         T = processready(clock);
         if (T != null) {
            readyQ.addElement(T);
            Q.removeElement(T);
            an.upstatus("Time "+T.getArrival()+":Process "+T.getName()+" ready.");
            try { Thread.sleep(1000); } catch (InterruptedException e) {};
         } // put in ready queue
         if (idle || interrupt) {
            if (interrupt) {
               interrupt = false;
               interval = 0;
            } // reset interval
            if (readyQ.size()==0)               
               continue;
            if (next < readyQ.size()-1)
               next++;
            else
               next=0;
            P = (process)readyQ.elementAt(next);
            idle = false;                       
         } // get next process
         P.servicing();
         interval++;
         an.drawbar(P,clock);
         an.upstatus("Time "+clock+":Serving process "+P.getName()+".");
         try { Thread.sleep(1000); } catch (InterruptedException ex) {};
         if (P.getTminus()==0) {
            an.upstatus("Time "+(clock+1)+":Process "+P.getName()+" done.");
            try { Thread.sleep(1000); } catch (InterruptedException e) {};
            P.report(clock+1); // anticipate completion
            finishQ.addElement(P);
            readyQ.removeElement(P);
            next--; // recenter ptr
            idle = true;            
            interval=0;
         } // put in finish queue
         else if (interval==4)
            interrupt = true;                    
      } while (finishQ.size()<all);
      an.upstatus("Algorithm finished.");
      st.report(finishQ,"Round Robin,q = 4");
      in.resetGUI();
   } // run thread

} // RR4 class
