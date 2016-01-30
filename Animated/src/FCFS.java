import java.util.Vector;

public class FCFS extends Scheduler implements Runnable {

   public FCFS(Vector q, statsFrame s, animeFrame a, inputFrame i, int c) 
   {
      super(q,s,a,i,c);
      thread = new Thread(this,"FCFS");
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
      st.report(finishQ,"First Come First Serve");
      in.resetGUI();
   } // run thread

} // FCFS class
