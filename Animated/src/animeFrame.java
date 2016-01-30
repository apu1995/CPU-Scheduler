
import java.awt.*;
import java.util.Vector;

public class animeFrame extends Frame {
   Canvas board;
   TextField statusLine,algTitle;
   Graphics gr;
   int inc1; // origin in time axis
  
   public animeFrame ()  {
      super("Animation View");
      board = new Canvas();
      statusLine = new TextField(30);
      statusLine.setEditable(false);
      algTitle = new TextField(30);
      algTitle.setEditable(false);

      this.setLayout(new BorderLayout());
      add("North", algTitle);
      add("South", statusLine);
      add("Center", board);
   } // constructor
   
   public boolean handleEvent(Event evtObj)  {
      if (evtObj.id == Event.WINDOW_DESTROY)  {
         dispose();
         return true;
      } // handle destroy button
      return super.handleEvent(evtObj);
   } // handle window options

   public void upstatus(String txt) {
      statusLine.setText(txt);
   } // update status string

   public void setTitle(String txt) {
      algTitle.setText(txt);
   } // update title

  
   public void drawbar(process P, int t) {
      int x,y;
      x=P.getNWcorner().x+P.getUnitLength()*(t-inc1);
      y=P.getNWcorner().y;
      gr.fillRect(x,y,P.getUnitLength(), P.getBarWidth());
   } // draw Unit bar for P

   public void cleargrid() {
      gr = board.getGraphics();
      this.update(gr); // use system empty paint to "clear" screen
   } // clears grid
   public int setgrid(Vector L) {
	   //System.out.println(L);
      process temp = (process)L.firstElement();
      int hbuf = temp.getName().length();
      inc1 = temp.getArrival();
      int inc2 = temp.getService();
      // L.size() - 5;
      for (int j=1; j < L.size(); j++) {
    	  //System.out.println(L.size());
         temp = (process)L.elementAt(j);
         if (hbuf < temp.getName().length())
            hbuf = temp.getName().length();
        // System.out.println(hbuf);
         if (inc1 > temp.getArrival())
            inc1 = temp.getArrival();
         inc2 += temp.getService();
        // System.out.println(inc2);
      } // traverse input queue
      hbuf = hbuf*5+10; // margin
      //System.out.println(hbuf);
      inc2 += inc1; // offset

      Dimension d = board.size();
      int hinc = (int)((d.width-10)/(inc2-inc1+2));
      int vinc = (int)((d.height-10)/(L.size()+2));
      temp.setUnitLength(hinc);
      temp.setBarWidth(vinc);

      int c=0;
      for (int j=inc1; j<=inc2; j++,c++) {
         if ((j-inc1)%5==0)
            gr.drawString(String.valueOf(j),hbuf+c*hinc,15);
         gr.drawLine(hbuf+c*hinc,15,hbuf+c*hinc,20);
      } // set horizontal axis
      gr.drawLine(hbuf,20,hbuf+(c-1)*hinc,20);
      for (int j=0; j<L.size(); j++) {
         gr.drawString(((process)L.elementAt(j)).getName(),5,40+j*vinc);
         ((process)L.elementAt(j)).upNWcorner(hbuf,10+20+j*vinc);        
      } // set vertical axis
      return inc1;
   } // draws grid scale

} // end of AlgAnime frame


