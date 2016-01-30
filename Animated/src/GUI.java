import java.util.Observable;

public class GUI extends Observable {   

   public GUI() {
      clearChanged();
   } // constructor

   public void input(Object info) {
      setChanged();
      notifyObservers(info);
   } // get user input

} // GUI class
