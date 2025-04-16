import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class Window extends JFrame{
    JFrame jframe;

    public Window(Panel panel) {
        jframe = new JFrame();
    
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(panel);
        jframe.setLocationRelativeTo(null);    
        jframe.setResizable(false);
        jframe.pack();
        jframe.setVisible(true);
    }
    
}
