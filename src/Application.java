
import java.awt.EventQueue;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Application extends JFrame {

    public Application() {
        
        initUI();
        
    }
    
    private void initUI() {
        
        add(new Board());
        
        setSize(1280, 640);
        setResizable(true);
        
        setTitle("Chess");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Application ex = new Application();
                ex.setVisible(true);
            }
        });
    }
}