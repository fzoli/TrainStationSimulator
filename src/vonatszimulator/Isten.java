package vonatszimulator;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

public class Isten {
    private Image ikon = Toolkit.getDefaultToolkit().getImage(getClass().
                         getResource("img/ikon.png"));

    public static Image getIkon() {
        return new Isten().ikon;
    }

    public static boolean isOs(String os) {
        String osName = System.getProperty("os.name");
        os = os.toLowerCase();
        osName = osName.toLowerCase();
        return osName.lastIndexOf(os) > -1;
    }

    public static Dimension getKepernyomeret() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public static void main(String[] args) {
        new Vezerlo();
    }

}