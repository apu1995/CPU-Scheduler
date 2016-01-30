import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class statsFrame extends Frame {
   TextArea pad;
   Vector out;
   String P,A,S,F,Tq,Tqs;
Double Wt;
   static String name;
   static Double fcfs;
   static Double sjf;
   static Double rr1;
   static Double rr4;
   static Double avgtq=0.0;
   static Double avgwt = 0.0;
   static String mname=null;
   static Double min=0.0,min1=0.0;
   static int count;

  
   public statsFrame() {
      super("Statistics View");
      this.setLayout(new BorderLayout());
      pad = new TextArea(30,30);
      pad.setEditable(true);
      this.add("Center", pad);
   } // set display

   public void report(Vector R,String title) {
      pad.appendText("\n"+title+"\n\n");
      out = R;
      display();
     // System.out.println(title);
      name=title;
      System.out.println("\n");
      System.out.println(name+" Average Turn Around Time is: "+avgtq);
      System.out.println(name+" Average Waiting Time is: "+avgwt);
      System.out.println("\n");
      if(min==0.0)
      {
    	  mname=title;
    	 min=avgtq;
      }
      else
      {
    	  if(avgtq<min)
    	  {
    		  min=avgtq;
    		  mname=title;
    		  
    	  }
      }
      System.out.println("So far the Best Avg Turnaround time is of "+mname+" and value is "+min);
      //System.out.println("\n");
      if(min1==0.0)
      {
    	  mname=title;
    	 min1=avgwt;
      }
      else
      {
    	  if(avgwt<min1)
    	  {
    		  min1=avgwt;
    		  mname=title;
    		  
    	  }
      }
      System.out.println("So far the Best Avg Waiting time is of "+mname+" and value is "+min1);
   } // report statistics to notepad

  
   public boolean handleEvent (Event evtObj) {
      if (evtObj.id == Event.WINDOW_DESTROY)
         this.dispose();     
      return true;
   } // handle destroy event

 
   private void display() {
      process temp=null;

      P = "Process";
      A = "Arrival Time";
      S = "Service Time";
      F = "Finish Time";
      Tq = "Turnaround Time";
      Tqs = "Tq/Ts";
      avgtq = 0.0;
      avgwt = 0.0;
      buffer(P,A,S,F,Tq,Tqs);
      for (int j=0; j<out.size(); j++) {
         temp = (process)out.elementAt(j);
         P += temp.getName();
         A += temp.getArrival();
         S += temp.getService();
         F += temp.getFinish();
         Tq += temp.getTq();
         Tqs += temp.getTqs();
         Wt =  temp.getTq() - temp.getService() ;
      //   System.out.println( Wt);
        avgtq=avgtq+temp.getTq();
        avgwt=avgwt+Wt;
         buffer(P,A,S,F,Tq,Tqs);
      } // get info from each
      avgtq=avgtq/out.size();
      avgwt=avgwt/out.size();
      pad.appendText(P+"\n"+A+"\n"+S+"\n"+F+"\n"+Tq+"\n"+Tqs+"\n");
      
      String w=""+"\n"+P+temp.getName()+"\n"+A+temp.getArrival()+"\n"+S+temp.getService()+"\n"+F+temp.getFinish()+"\n"+Tq+temp.getTq()+"\n"+Tqs+temp.getTqs()+"\n";
      //System.out.println(name+" Average Turn Around Time is: "+avgtq);
      if(name=="First Come First Serve Scheduling")
    	  fcfs=avgtq;
      else if(name=="Round Robin, q = 1")
    	  rr1=avgtq;
      else if(name=="Round Robin, q = 4")
    	  rr4=avgtq;
      else
    	  sjf=avgtq;
      
      /*if(fcfs<=rr1 && fcfs<=rr4 && fcfs<=sjf)
    	  System.out.println("The Best Algorithm is First Come First Served");
      else if(rr1<=fcfs && rr1<=rr4 && rr1<=sjf)
    	  System.out.println("The Best Algorithm is Round Robin with Quantum 1");
      else if(rr4<=fcfs && rr4<=rr1 && rr4<=sjf)
    	  System.out.println("The Best Algorithm is Round Robin with Quantum 4");
      else
    	  System.out.println("The Best Algorithm is Shortest Remaining Time");*/
      
      FileWriter wr;
	try {
		wr = new FileWriter("A.txt");
		wr.write(""+w);
		wr.close();
	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
   } // display stats

  
   private void buffer(String p,String a, String s, String f, String tq, String tqs) {
      int max = Math.max(P.length(),Math.max(A.length(),Math.max(S.length(),Math.max(F.length(),
                Math.max(Tq.length(),Tqs.length())))));
      max += 5;
      P = space (P,max);
      A = space (A,max);
      S = space (S,max);
      F = space (F,max);
      Tq = space (Tq,max);
      Tqs = space (Tqs,max);
   } // format with buffer spaces, left justfied

  
   private String space(String x, int m) {
      while (x.length() < m)
         x += " ";
      return x;
   } // pad with spaces

} // stats Frame class
