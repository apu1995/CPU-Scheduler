import java.awt.Point;

public class process extends Object {
   String name;
   int ArrivalTime, ServiceTime, FinishTime, TimeLeft, Tq;
   double Tqs;

   static int barwidth, unitLength;
   Point NWcornerpt;

   public process(String n, int a, int s) {
      name=n;
      ArrivalTime=a;
      ServiceTime=TimeLeft=s;
      NWcornerpt= new Point(0,0); // dummy init
   } // constructor

   // interface functions for anime
   public Point getNWcorner() { return NWcornerpt; }
   public int getBarWidth() { return barwidth; }
   public int getUnitLength() { return unitLength; }
   public int getArrival() { return ArrivalTime; }
   public int getTminus() { return TimeLeft; }
   public String getName() { return name; }
   public int getService() { return ServiceTime; }
   public int getFinish() { return FinishTime; }
   public double getTq() { return Tq; }
   public double getTqs() { return Tqs; }

   // update functions
   public void setUnitLength(int x) { unitLength = x; }
   public void setBarWidth(int x) { barwidth = x; }
   public void upNWcorner(int x, int y) { NWcornerpt.translate(x,y); }

   public void servicing() { TimeLeft--;}

   public void report(int t) {
      FinishTime=t;
      Tq = FinishTime-ArrivalTime;
      Tqs = Math.round (Tq / ServiceTime);
   } // calculate data
} // process class
