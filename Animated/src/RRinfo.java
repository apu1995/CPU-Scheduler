
 class RRinfo {
      process X; // reference only
      int wait, service;
      double RR;
      
  
      public RRinfo(process A) {
         X = A;
         wait = 0;
         service = X.getService();
         RR = 1.0;
      } // constructor

      // interface
      public process getProc() { return X; }
      public double getRR() { return RR; }
         
   
      public void delay() {
         RR = (++wait + service)/service;
      } // update RR
   } // added RR information


