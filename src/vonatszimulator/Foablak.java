package vonatszimulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Foablak extends JFrame {
    private Vezerlo vezerlo;
    private final Dimension ALAP_ABLAKMERET = new Dimension(732, 453);
    private Graphics2D sinTerulet;
    private Graphics2D sinvegTerulet;
    private Graphics2D vonatTerulet;
    private JLayeredPane rajzterulet;
    private JPanel vezerlopanel;
    private JCheckBox cbJatekMod;
    private JCheckBox cbRejtettMod;

    private ActionListener checkboxEsemeny = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            checkboxKattintasTortent();
        }

    };

    private ComponentAdapter ablakmeretEsemeny = new ComponentAdapter() {

        @Override
        public void componentResized(ComponentEvent e) {
            super.componentResized(e);
            ablakmeretValtozas();
        }

    };

    private KeyAdapter gombnyomasEsemeny = new KeyAdapter() {

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            gombnyomasTortent(e);
        }

    };

    public Foablak(Vezerlo vezerlo) {
        this.vezerlo=vezerlo;
        inicializalas();
    }

    private void inicializalas() {
        setLAF();
        setTitle("Vonatállomás-szimulátor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setAblakMeret();
        setVisible(true); //FONTOS, HOGY ITT LEGYEN
        setTeruletek();
        setAblakmeretFigyelo();
        setGombnyomasFigyelo();
        setVezerlofelulet();
    }

    private void setVezerlofelulet() {
        vezerlopanel = new JPanel();
        add(vezerlopanel,BorderLayout.SOUTH);
        vezerlopanel.setOpaque(false);
        cbJatekMod = createCb("Játékmód");
        cbRejtettMod = createCb("Sínvég mutatása");
    }

    private JCheckBox createCb(String cimke) {
        JCheckBox cb = new JCheckBox(cimke);
        cb.setOpaque(false);
        cb.setFocusable(false);
        cb.addActionListener(checkboxEsemeny);
        vezerlopanel.add(cb);
        repaint();
        vezerlopanel.setVisible(false);
        vezerlopanel.setVisible(true);
        return cb;
    }

    public void setTeruletek() {
        setRajzterulet();
        setSinterulet();
        setSinvegTerulet();
        setVonatTerulet();
    }

    private void checkboxKattintasTortent() {
        uzenetVezerlonek("CHECKBOX KATTINTAS");
    }

    private void ablakmeretValtozas() {
        uzenetVezerlonek("ABLAKMERET VALTOZAS");
    }

    private void gombnyomasTortent(KeyEvent e) {
        if (e.isControlDown())
            uzenetVezerlonek("CTRL GOMBNYOMAS", Integer.toString(e.getKeyCode()));
        else
            uzenetVezerlonek("GOMBNYOMAS", Integer.toString(e.getKeyCode()));
    }

    private void setAblakMeret() {
        setSize(ALAP_ABLAKMERET);
    }

    public Dimension getTeruletMeret() {
        return getContentPane().getSize();
    }

    public boolean isSinlezaroMutat() {
        return cbRejtettMod.isSelected();
    }

    public boolean isJatekmodBe() {
        return cbJatekMod.isSelected();
    }

    public Graphics2D getSinterulet() {
        return sinTerulet;
    }

    public Graphics2D getSinvegTerulet() {
        return sinvegTerulet;
    }

    public Graphics2D getVonatTerulet() {
        return vonatTerulet;
    }

    private void setRajzterulet() {
        if (rajzterulet != null)
            rajzterulet.setBounds(0, 0, 0, 0);
        rajzterulet = new JDesktopPane();
        rajzterulet.removeAll();
        getContentPane().setBackground(Color.WHITE);
        rajzterulet.setOpaque(false);
        getContentPane().add(rajzterulet);
        setAblakKitoltesre(rajzterulet);
    }

    private Graphics2D setTerulet(int retegindex) {
        Dimension meret = getTeruletMeret();
        BufferedImage img = new BufferedImage(meret.width, meret.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.addRenderingHints(
                new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON));
        JLabel lb = new JLabel(new ImageIcon(img));
        setAblakKitoltesre(lb);
        rajzterulet.add(lb, retegindex);
        return g;
    }

    private void setAblakKitoltesre(Component c) {
        Dimension meret = getTeruletMeret();
        c.setBounds(0, 0, meret.width, meret.height);
    }

    private void setSinterulet() {
        sinTerulet = setTerulet(JLayeredPane.DEFAULT_LAYER);
    }

    private void setSinvegTerulet() {
        sinvegTerulet = setTerulet(JLayeredPane.DEFAULT_LAYER);
    }

    private void setVonatTerulet() {
        vonatTerulet = setTerulet(0);
    }

    private void setAblakmeretFigyelo() {
        addComponentListener(ablakmeretEsemeny);
    }

    private void uzenetVezerlonek(String uzenet) {
        uzenetVezerlonek(uzenet, null);
    }

    private void uzenetVezerlonek(String uzenet, String adat) {
        vezerlo.uzenetfogadas(uzenet, adat);
    }

    private void setGombnyomasFigyelo() {
        addKeyListener(gombnyomasEsemeny);
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
        return Isten.isOs(os);
    }

}
