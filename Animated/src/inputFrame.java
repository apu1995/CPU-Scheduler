/*****************************************************************
                        Input Frame
PURPOSE: This is the input view that prompts user for input in order to 
run simulation. This view is meant for the instructor, author.
*****************************************************************/
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observable;

import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class inputFrame extends Frame {
   TextArea proc,arriv,serv;
   Panel knobs;
   Choice alg;
   CheckboxGroup functions;
   Checkbox[] fun;
   Packet info;
   GUI vigil;
   String PrevBut;

   public inputFrame(AlgAnime parent) {
      super("Input View");
      vigil = new GUI();
      vigil.addObserver((java.util.Observer)parent);

      String sampleP = "1\n2\n3\n4\n5";
      String sampleA = "0\n2\n4\n6\n8";
      String sampleS = "3\n6\n4\n5\n2";
      
      proc = new TextArea(30,10);
      proc.setEditable(true);
      proc.appendText(sampleP);

      arriv = new TextArea(30,5);
      arriv.setEditable(true);
      arriv.appendText(sampleA);
      
      serv = new TextArea(30,5);
      serv.setEditable(true);
      serv.appendText(sampleS);

      alg = new Choice();
      alg.addItem("FCFS");
      alg.addItem("RR1");
      alg.addItem("RR4");
      alg.addItem("SPN");
      alg.addItem("SRT");
      alg.addItem("FB1");
     // alg.addItem("FB1");

      functions = new CheckboxGroup();
      fun = new Checkbox[8];
      fun[0] = new Checkbox("clear",functions,false);
      fun[1] = new Checkbox("run",functions,false);
      fun[2] = new Checkbox("pause",functions,false);
      fun[3] = new Checkbox("resume",functions,false);
      fun[4] = new Checkbox("quit",functions,false);
      fun[6] = new Checkbox("insert arrival time",functions,false);
      fun[5] = new Checkbox("insert Service time",functions,false);
      PrevBut = ""; // init

      knobs = new Panel();
      knobs.setLayout(new FlowLayout(FlowLayout.CENTER));
      knobs.add(alg);
      knobs.add(fun[0]);
      knobs.add(fun[1]);
      knobs.add(fun[2]);
      knobs.add(fun[3]);
      knobs.add(fun[4]);
      knobs.add(fun[5]);
      knobs.add(fun[6]);
     
      Panel labels = new Panel();
      labels.setLayout(new BorderLayout());
      labels.add("West", new Label("Arrival time:"));     
      labels.add("Center", new Label("Process name:"));
      labels.add("East", new Label("Service time:")); 

      this.setLayout(new BorderLayout());
      this.add("North",labels);
      this.add("West", arriv);
      this.add("Center",proc);
      this.add("East",serv);
      this.add("South", knobs);      
   } // set display

 
   public boolean handleEvent (Event evtObj) {
      if (evtObj.id == Event.WINDOW_DESTROY) {
         this.dispose();
         return true;
      } // destroy button
      else if (evtObj.id==Event.ACTION_EVENT)
         if (evtObj.target==fun[0]) {
            proc.setText("");
            arriv.setText("");
            serv.setText("");
            fun[1].enable();
            String cmd = functions.getCurrent().getLabel();
            vigil.input(cmd);
            return true;
         } // handle clear button
         else if (evtObj.target==fun[1]) {
            fun[1].disable();
            info = new Packet(proc.getText(), arriv.getText(), serv.getText(), alg.getSelectedItem());
            vigil.input(info);
            proc.setEditable(false);
            arriv.setEditable(false);
            serv.setEditable(false);
            return true;
         } // handle run button
         else if (evtObj.target==fun[2] || evtObj.target==fun[3]) {
            String cmd = functions.getCurrent().getLabel();
            if (PrevBut.equals(cmd))
               return false;
            else {
               PrevBut = cmd; // stagger for next event
               vigil.input(cmd);
               return true;
            } // balance pause to resume request
         } // handle pause/resume buttons
         else if (evtObj.target==fun[4]) {
            vigil.input("quit");
            return true;
         } // handle quit option
         else if (evtObj.target==fun[5]) {
        	 //proc.setText("");
             //arriv.setText("");
            serv.setText("");
             fun[1].enable();
             String cmd = functions.getCurrent().getLabel();
             vigil.input(cmd);
             
             String service=serv.getText();
             System.out.println(" Service is "+service);

             JFileChooser chooser = new JFileChooser();
             chooser.setMultiSelectionEnabled(true);
             int option = chooser.showOpenDialog(inputFrame.this);
             if (option == JFileChooser.APPROVE_OPTION) {
               File[] sf = chooser.getSelectedFiles();
               String filelist = "nothing";
               if (sf.length > 0) filelist = sf[0].getName();
               for (int i = 1; i < sf.length; i++) {
                 filelist += ", " + sf[i].getName();
               }
              //statusbar.setText("You chose " + filelist);
               String workingDirectory = System.getProperty("user.dir");
               String absoluteFilePath = "";
               absoluteFilePath = workingDirectory + File.separator + filelist;
               System.out.println(absoluteFilePath);
               try {
                   // FileReader reads text files in the default encoding.
                   FileReader fileReader =
                       new FileReader(absoluteFilePath);
                   // Always wrap FileReader in BufferedReader.
                   BufferedReader bufferedReader =
                       new BufferedReader(fileReader);
                   try {
           			StringBuffer stringBuffer = new StringBuffer();
           			String line;
           			while ((line = bufferedReader.readLine()) != null) {
           				stringBuffer.append(line);
           				stringBuffer.append("\n");
           			}
           			fileReader.close();
           			System.out.println("Contents of file:");
           			System.out.println(stringBuffer.toString());
           			serv.setText(stringBuffer.toString());
           		} catch (IOException e) {
           			e.printStackTrace();
           		}

                   // Always close files.
                   try {
     				bufferedReader.close();
     			} catch (IOException e) {
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     			}
               }
               catch(FileNotFoundException ex) {
                   System.out.println(
                       "Unable to open file '" +
                       filelist + "'");
               }
             }
             
        //     return true;
             //return false;      
   } // handle event
         else if (evtObj.target==fun[6]) {
        	// proc.setText("");
             arriv.setText("");
             serv.setText("");
             //fun[1].enable();
            // String cmd = functions.getCurrent().getLabel();
             // vigil.input(cmd);
             
             
             

             JFileChooser chooser = new JFileChooser();
             chooser.setMultiSelectionEnabled(true);
             int option = chooser.showOpenDialog(inputFrame.this);
             if (option == JFileChooser.APPROVE_OPTION) {
               File[] sf = chooser.getSelectedFiles();
               String filelist = "nothing";
               if (sf.length > 0) filelist = sf[0].getName();
               for (int i = 1; i < sf.length; i++) {
                 filelist += ", " + sf[i].getName();
               }
              //statusbar.setText("You chose " + filelist);
               String workingDirectory = System.getProperty("user.dir");
               String absoluteFilePath = "";
               absoluteFilePath = workingDirectory + File.separator + filelist;
               System.out.println(absoluteFilePath);
               try {
                   // FileReader reads text files in the default encoding.
                   FileReader fileReader =
                       new FileReader(absoluteFilePath);
                   // Always wrap FileReader in BufferedReader.
                   BufferedReader bufferedReader =
                       new BufferedReader(fileReader);
                   try {
           			StringBuffer stringBuffer = new StringBuffer();
           			String line;
           			while ((line = bufferedReader.readLine()) != null) {
           				stringBuffer.append(line);
           				stringBuffer.append("\n");
           			}
           			fileReader.close();
           			System.out.println("Contents of file:");
           			System.out.println(stringBuffer.toString());
           			arriv.setText(stringBuffer.toString());
           		} catch (IOException e) {
           			e.printStackTrace();
           		}

                   // Always close files.
                   try {
     				bufferedReader.close();
     			} catch (IOException e) {
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     			}
               }
               catch(FileNotFoundException ex) {
                   System.out.println(
                       "Unable to open file '" +
                       filelist + "'");
               }
             }
             
        //     return true;
             //return false;      
   } // handle event
         else if (evtObj.target==fun[7]) {
        	 proc.setText("");
             arriv.setText("");
             serv.setText("");
             //fun[1].enable();
            // String cmd = functions.getCurrent().getLabel();
             // vigil.input(cmd);
             
             
             

             JFileChooser chooser = new JFileChooser();
             chooser.setMultiSelectionEnabled(true);
             int option = chooser.showOpenDialog(inputFrame.this);
             if (option == JFileChooser.APPROVE_OPTION) {
               File[] sf = chooser.getSelectedFiles();
               String filelist = "nothing";
               if (sf.length > 0) filelist = sf[0].getName();
               for (int i = 1; i < sf.length; i++) {
                 filelist += ", " + sf[i].getName();
               }
              //statusbar.setText("You chose " + filelist);
               String workingDirectory = System.getProperty("user.dir");
               String absoluteFilePath = "";
               absoluteFilePath = workingDirectory + File.separator + filelist;
               System.out.println(absoluteFilePath);
               try {
                   // FileReader reads text files in the default encoding.
                   FileReader fileReader =
                       new FileReader(absoluteFilePath);
                   // Always wrap FileReader in BufferedReader.
                   BufferedReader bufferedReader =
                       new BufferedReader(fileReader);
                   try {
           			StringBuffer stringBuffer = new StringBuffer();
           			String line;
           			while ((line = bufferedReader.readLine()) != null) {
           				stringBuffer.append(line);
           				stringBuffer.append("\n");
           			}
           			fileReader.close();
           			System.out.println("Contents of file:");
           			System.out.println(stringBuffer.toString());
           			proc.setText(stringBuffer.toString());
           		} catch (IOException e) {
           			e.printStackTrace();
           		}

                   // Always close files.
                   try {
     				bufferedReader.close();
     			} catch (IOException e) {
     				// TODO Auto-generated catch block
     				e.printStackTrace();
     			}
               }
               catch(FileNotFoundException ex) {
                   System.out.println(
                       "Unable to open file '" +
                       filelist + "'");
               }
             }
             
        //     return true;
             //return false;      
   } // handle event
     return false;
   }
   public void resetGUI() {
      proc.setEditable(true);
      serv.setEditable(true);
      arriv.setEditable(true);
      fun[1].enable();
   } // allow user input
} // input Frame
