
import java.util.Vector;

public class FB1 extends Scheduler implements Runnable {

 
   public FB1(Vector q, statsFrame s, animeFrame a, inputFrame i, int c) {//
      super(q,s,a,i,c);      
    
      thread = new Thread(this,"FB1");
      thread.start();
   } // constructor

   
   public void run() {
      int all=Q.size(), interval=0, next=0;
      boolean interrupt = false;
      Vector readyQ1 = new Vector(1,1);

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
            if (readyQ.size()!=0)
               P = (process)readyQ.firstElement();
            else if (readyQ1.size()!=0) {                                 
               if (next < readyQ1.size()-1)
                  next++;
               else
                  next=0;
               if (P==(process)readyQ1.elementAt(next))
                  if (next<readyQ1.size()-1)
                     next++;
                  else
                     next=0;
               P = (process)readyQ1.elementAt(next);
            } // get process from RQ1
            else
               continue;
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
            int I = search(P, readyQ, readyQ1);
            if (I==0)
               readyQ.removeElement(P);
            else if (I==1) {
               next--; // recenter ptr
               readyQ1.removeElement(P);
            } // readjust RQ1
            idle = true;            
            interval=0;
         } // put in finish queue
         else if (interval==1)
            if (search(P,readyQ,readyQ1)==0) {
               readyQ.removeElement(P);
               readyQ1.addElement(P);
            } // relegate process
            interrupt = true;                    
      } while (finishQ.size()<all);
      an.upstatus("Algorithm finished.");
      st.report(finishQ,"Feedback,q = 1");
      in.resetGUI();
   } // run thread

   /*--------------------------------------------------------
                       
    To determine which queue a process is in
      ----*/   
   private int search(process A, Vector q0, Vector q1) {
      for (int j=0; j<q0.size(); j++)
         if (A==(process)q0.elementAt(j))
            return 0;
      for (int j=0; j<q1.size(); j++)
         if (A==(process)q1.elementAt(j))
            return 1;
      return -1; // shouldn't happen
   } // determines which queue 

} // FB1 class
