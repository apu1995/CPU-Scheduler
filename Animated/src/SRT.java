import java.util.Vector;

public class SRT extends Scheduler implements Runnable {

  
   public SRT(Vector q, statsFrame s, animeFrame a, inputFrame i, int c) {
      super(q,s,a,i,c);
   
      thread = new Thread(this,"SRT");
      thread.start();
   } // constructor

   public void run() {
      int all=Q.size();
      boolean interrupt=false;
      process X=null; // preempted process

      do {
         clock++;
         T = processready(clock);
         if (T != null) {
            readyQ.addElement(T);
            Q.removeElement(T);
            interrupt = true;
            an.upstatus("Time "+T.getArrival()+":Process "+T.getName()+" ready.");
            try { Thread.sleep(1000); } catch (InterruptedException e) {};
         } // put in ready queue
         if (idle || interrupt) {
            if (interrupt) {
               interrupt=false;
               if (X!=null)
                  readyQ.addElement(X);
            } // reenter process
            if (readyQ.size()==0)
               continue;
            if (idle)
               idle = false;
            P = (process)readyQ.firstElement();
            int sr = P.getTminus();
            for (int j=1; j<readyQ.size(); j++)
               if (sr > ((process)readyQ.elementAt(j)).getTminus()) {
                     P = (process)readyQ.elementAt(j);
                     sr = P.getTminus();
               } // find earliest process with shortest remaining time
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
            X=null;
         } // put in finish queue
         else
            X=P;         
      } while (finishQ.size()<all);
      an.upstatus("Algorithm finished.");
      st.report(finishQ,"Shortest Remaining Time");
      in.resetGUI();
   } // run thread

} // SRT class
