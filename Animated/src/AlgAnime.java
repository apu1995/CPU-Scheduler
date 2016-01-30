import java.awt.*;
import java.applet.*;
import java.util.Vector;
import java.util.Observer;
import java.util.Observable;
import java.io.*;
import java.io.*;

import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;

public class AlgAnime extends Applet implements Observer {
   animeFrame anime;
   statsFrame stats;
   inputFrame input;

   Scheduler lesson;
   Vector Q;

   public void init() {
      anime = new animeFrame();
      anime.show();
      anime.resize(300,300);

      stats = new statsFrame();
      stats.pack();
      stats.show();
      stats.resize(300,300);

      input = new inputFrame(this);
      input.show();
      input.resize(300,300);

      lesson=null;
      Q = new Vector(1,1);
   } 

   public void update(Observable obj, Object arg) {
      if (arg instanceof String) {
         if (((String)arg).equals("pause") && lesson!=null)
            lesson.thread.suspend();
         else if (((String)arg).equals("resume") && lesson!=null)
            lesson.thread.resume();
         else if (((String)arg).equals("clear")) {
            input.resetGUI();
            Q.setSize(0);
            lesson.resetQ();
            lesson.thread.stop();
            lesson = null;               
         } 
         else if (((String)arg).equals("quit")) {
            input.hide();   input.dispose();
            anime.hide();   anime.dispose();
            stats.hide();   stats.dispose();
         }
      } 
      else if (arg instanceof Packet) {
         getParms((Packet)arg);
         anime.cleargrid();
         int click = anime.setgrid(Q);
         if (((Packet)arg).getAlg().equals("FCFS")) {            
            anime.setTitle("First Come First Serve Scheduling");
            FileWriter wr;
        	try {
        		wr = new FileWriter("Algo.txt");
        		wr.write("First Come First Serve Scheduling");
        		wr.close();
        	} catch (IOException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
            lesson = new FCFS (Q, stats, anime,input,click);
         } 
         else if (((Packet)arg).getAlg().equals("RR1")) {
            anime.setTitle("Round Robin, q = 1");
            lesson = new RR1 (Q, stats, anime,input,click);
            FileWriter wr;
        	try {
        		wr = new FileWriter("Algo.txt");
        		wr.write("RR1");
        		wr.close();
        	} catch (IOException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
            lesson = new FCFS (Q, stats, anime,input,click);
         } 
         else if (((Packet)arg).getAlg().equals("RR4")) {
            anime.setTitle("Round Robin, q = 4");
            lesson = new RR4 (Q, stats, anime, input,click);
         } 
         else if (((Packet)arg).getAlg().equals("SPN")) {
            anime.setTitle("Shortest Process Next");
            lesson = new SPN(Q, stats, anime, input,click);
         } 
         else if (((Packet)arg).getAlg().equals("SRT")) {
            anime.setTitle("Shortest Remaining Time");
            lesson = new SRT(Q, stats, anime, input,click);
         } 
         else if (((Packet)arg).getAlg().equals("HRRN")) {
            anime.setTitle("Highest Response Ratio Next");
           
         } 
         else if (((Packet)arg).getAlg().equals("FB1")) {
            anime.setTitle("Feedback, q = 1");
            lesson = new FB1(Q, stats, anime, input,click);
         } 
      } 
   } 

   private void getParms(Packet in) {  
      String name, t1, t2, t3, mark ="\n";
      Integer I = new Integer(0);
      int pos, a, s;
      boolean empty=false;
      t1 = in.getProc();
      t2 = in.getArriv();
      t3 = in.getServ();
      
      try 
      {
		FileWriter fw=new FileWriter("PieChartPass.txt");
		fw.write(t3);
		fw.close();
      }
      catch (IOException e) 
      {
		e.printStackTrace();
      }
      
      // MATLAB CONNECT CODE:::
      
      try 
      {
    	 Matlab m=new Matlab();
      }
      catch (MatlabConnectionException e) 
      {
		e.printStackTrace();
      }
      catch (MatlabInvocationException e) 
      {
		e.printStackTrace();
      }
      
      
      do {
         pos = t1.indexOf(mark);
         if (pos<0) {
            name = t1;
            empty = true;
         }
         else
            name = t1.substring(0,pos);
         t1 = t1.substring(pos+1);

         pos = t2.indexOf(mark);
         if (pos<0) {
            a = I.parseInt(t2);
            empty = true;
         }
         else
            a = I.parseInt(t2.substring(0,pos));
         t2 = t2.substring(pos+1);

         pos = t3.indexOf(mark);
         if (pos<0) {
            s = I.parseInt(t3);
            empty = true;
         }
         else
            s = I.parseInt(t3.substring(0,pos));
         t3 = t3.substring(pos+1);

         process temp = new process(name, a, s);
         Q.addElement(temp);         
      } while (!empty);      
   } // set up Queue

} // algorithm animation main driver
