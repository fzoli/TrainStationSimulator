import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class RajzTest extends JFrame {

    public RajzTest() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container cp = getContentPane();
        setBounds(200, 200, 200, 200);
        setVisible(true);
        BufferedImage img = new BufferedImage(cp.getSize().width, cp.getSize().height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        JLabel lb = new JLabel(new ImageIcon(img));
        cp.add(lb);
        int szelesseg = 10;
        int magassag = 20;
        Point kezdopont = new Point(20,10);
        int[] x = { kezdopont.x, kezdopont.x + szelesseg,    kezdopont.x };
        int[] y = { kezdopont.y, kezdopont.y + (magassag/2), kezdopont.y + magassag };
        Polygon p = new Polygon(x, y, 3);
        g.setColor(Color.red);
        g.draw(p);
    }

    public static void main(String[] args) {
        new RajzTest();
    }

}