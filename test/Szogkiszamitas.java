import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Szogkiszamitas extends JFrame {

    public Szogkiszamitas() {
        setBounds(100, 100, 450, 230);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        BufferedImage img = new BufferedImage(400, 200, BufferedImage.TYPE_INT_ARGB);
        JLabel lb = new JLabel(new ImageIcon(img));
        getContentPane().add(lb);

        Graphics2D g = (Graphics2D) img.getGraphics();
        
        Point p2 = new Point(100, 0);
        Point p1 = new Point(101, 200);

        g.setColor(Color.red);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
        getContentPane().repaint();
        double arcTg = Math.atan2(
                Math.abs(p2.y - p1.y),
                Math.abs(p2.x - p1.x));

        double angle = arcTg * (180 / Math.PI);

        System.out.println(angle);
        double d = Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
        System.out.println(d);

        System.out.println((angle/Math.PI));


        System.out.println("lepes "+(1 % 1));
    }

    public static void main(String[] args) {
        new Szogkiszamitas();
    }
}