

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class Ablak extends JFrame {
    private JToolBar ui;
    private Container cp;
    private JPanel rajzpanel;
    private JLayeredPane lp = new JDesktopPane();
    private Image ikon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/ikon.png"));

    public Ablak() {
        inicializalas();
        /*
        JButton bt = new JButton("A");
        bt.setBounds(10, 10, 70, 20);
        lp.add(bt,JLayeredPane.POPUP_LAYER); //teszt sorok
        
        ui.add(new JLabel("Vezérlő felület")); //teszt sor

        Dimension s = cp.getSize();
        BufferedImage fixImg = new BufferedImage(s.width, s.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D fixFelulet = (Graphics2D) fixImg.getGraphics();
        BufferedImage mozgoImg = new BufferedImage(s.width, s.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D mozgoFelulet = (Graphics2D) mozgoImg.getGraphics();
        JLabel fixLabel = new JLabel(new ImageIcon(fixImg));
        fixLabel.setOpaque(false);
        JLabel mozgoLabel = new JLabel(new ImageIcon(mozgoImg));
        mozgoLabel.setOpaque(false);
        lp.add(fixLabel, JLayeredPane.DEFAULT_LAYER);
        lp.add(mozgoLabel, JLayeredPane.POPUP_LAYER);

        fixFelulet.setColor(Color.red);
        mozgoFelulet.setColor(Color.blue);
        fixFelulet.drawLine(0, 0, 100, 100);
        mozgoFelulet.drawLine(100, 100, 200, 200);
        */
    }

    private void inicializalas() {
        setSize(810, 240);
        setAblakKozepre();
        setTitle("Vonatállomás szimulátor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(ikon);
        setLAF();
        setVisible(true);
        //setCp();
        //setRajzpanel();
        //setVezerloFelulet();
    }

    private void setVezerloFelulet() {
        ui = new JToolBar(JToolBar.HORIZONTAL);
        ui.setBorder(new BevelBorder(BevelBorder.RAISED));
        cp.add(ui, BorderLayout.NORTH);
    }

    private void setVaganyTerulet() {
    }

    private void setRajzpanel() {
        rajzpanel = new JPanel(new GridLayout());
        rajzpanel.setOpaque(false);
        lp.setOpaque(false);
        cp.add(rajzpanel, BorderLayout.CENTER);
        rajzpanel.add(lp);
    }

    private void setCp() {
        cp = getContentPane();
        cp.setBackground(Color.WHITE);
    }

    private void setLAF() {
        try {
            if (isOs("linux"))
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            else if(isOs("windows"))
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            else if (isOs("mac"))
                UIManager.setLookAndFeel("javax.swing.plaf.mac.MacLookAndFeel");
            else UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex) {}
    }

    private boolean isOs(String os) {
        String osName = System.getProperty("os.name");
        os = os.toLowerCase();
        osName = osName.toLowerCase();
        return osName.lastIndexOf(os) > -1;
    }

    private void setAblakKozepre() {
        int x, y;
        int width = getSize().width;
        int height = getSize().height;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        x = (screenSize.width/2) - (width/2);
        y = (screenSize.height/2) - (height/2);
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x + width > screenSize.width) x = screenSize.width - width;
        if (y + height > screenSize.height) y = screenSize.height - height;
        setBounds(x, y, width, height);
    }

}