import javax.swing.*;
public class ProgressBar  extends JFrame
{

	 JProgressBar jb;
    int i=0,num=0;

    ProgressBar(){
    jb=new JProgressBar(100,2000);
    JLabel l=new JLabel("CPU Scheduling Simultor");
    l.setBounds(60,10,150,60);
    //jb.setString("");
    jb.setBounds(30,60,200,30);

    jb.setValue(0);
    jb.setStringPainted(true);
    add(l);

    add(jb);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(300,150);
    setLayout(null);
    }

    public void iterate(){
    while(i<=2000){
      jb.setValue(i);
      i=i+100;
      try{Thread.sleep(150);}catch(Exception e){}

    }
    }
    public static void main(String[] args) {
        ProgressBar m=new ProgressBar();
        m.setVisible(true);
        m.iterate();
        
        Hello n = new Hello();
        n.setVisible(true);
        m.setVisible(false);

    }

}
