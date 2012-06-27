import java.awt.*;
import javax.swing.*;

public class LayerTest extends JFrame {

    Container cp;
    JLayeredPane lp = new JDesktopPane();

    public LayerTest() {
        //két gomb létrehozása és pozíció + méret beállítása
        JButton bt = new JButton("Első");
        bt.setBounds(100, 100, 100,100);
        JButton bt2 = new JButton("Második");
        bt2.setBounds(150, 150, 100, 100);

        //az ablak panelének lekérése
        cp = getContentPane();
        //ablak panel háttérszínének beállítása
        cp.setBackground(Color.BLACK);

        //többrétegű panel átlátszóvá tétele
        lp.setOpaque(false);
        //felső réteghez (int 300) hozzáadás
        lp.add(bt, JLayeredPane.POPUP_LAYER);
        //alapértelmezett réteghez (int 0) hozzáadás
        lp.add(bt2, JLayeredPane.DEFAULT_LAYER);
        //többrétegű réteg hozzáadása az ablak paneljéhez
        cp.add(lp);

        //ablakbeállítások
        setSize(400,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LayerTest();
    }

}