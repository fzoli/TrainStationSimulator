

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

/**
 * Létrehozza a főablakot és beállítja, létrehozza a VágányAlkatrészeket (EgyenesSín, ÖsszekötőSín, Váltó, Sínlezáró) és összekapcsolja őket.
 * Figyeli az eseményeket (ablak átméretezés, billentyűlenyomás, váltóra kattintás, vezérlőelemek módosulása.
 * Esemény hatására létrehoz vonatot (balranyíl ill. jobbra nyíl) aminek beállítja a típusát (személy, gyors) és menetirányát, és váltót kapcsol (ha váltóra kattintottak és a manuális kezelés be van pipálva).
 * A vonatokat mozgatja sebességük alapján (de lehet hogy a vonat magát fogja mozgatni), megsemmisíti őket, ha kell (vágány vége vagy váltó miatt való kisiklás)
 */
public class Programvezerlo {
    private Ablak ablak = new Ablak();
/*
    static Valto val3;
    static OsszekotoSin osin4;
    static EgyenesSin esin6;
    static EgyenesSin esin5;
*/
    /*
    public Programvezerlo() {
        Container cp = ablak.getContentPane();
        JLayeredPane lp = new JDesktopPane();
        lp.setOpaque(false);
        cp.setBackground(Color.white);
        //BufferedImage img = new BufferedImage(cp.getSize().width, cp.getSize().height, BufferedImage.TYPE_INT_ARGB);
        BufferedImage img = new BufferedImage(790, 200, BufferedImage.TYPE_INT_ARGB);
        BufferedImage imgVonatnak = new BufferedImage(790, 200, BufferedImage.TYPE_INT_ARGB);
        JLabel lb = new JLabel(new ImageIcon(img));
        JLabel lbV = new JLabel(new ImageIcon(imgVonatnak));
        lp.add(lbV,JLayeredPane.POPUP_LAYER);
        lp.add(lb,JLayeredPane.DEFAULT_LAYER);
        cp.add(lp);
        lb.setBounds(0, 0, 790, 200);
        lbV.setBounds(0, 0, 790, 200);
        Graphics2D g = (Graphics2D) img.getGraphics();
        Graphics2D gV = (Graphics2D) imgVonatnak.getGraphics();
        g.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        //cp-hez hozzáadni egy layeredpane-t amiben alsó rétegen is meg felső rétegen is 2 különböző kép van
        //alsó rétegre kirajzolni egy nagyobb téglalapot, felsőre meg egy kisebbet
        //megnézni, hogy látszik-e mindkettő, és a kisebb takarja a nagyobbat

        Sinlezaro sinl1 = new Sinlezaro(g, new Point(-40, 100));
        Valto val1 = new Valto(g, new Point(200, 100));
        final Valto val2 = new Valto(g, new Point(300, 100));
        final Valto val3 = new Valto(g, new Point(740, 100), true);
        SinOsszekoto so1 = new SinOsszekoto(g, new Point(400, 16));
        SinOsszekoto so2 = new SinOsszekoto(g, new Point(500, 180));
        SinOsszekoto sinl2 = new SinOsszekoto(g, new Point(550, 16));
        Sinlezaro sinl3 = new Sinlezaro(g, new Point(860, 180));
        Sinlezaro sinl5 = new Sinlezaro(g, new Point(870, 100));

        final EgyenesSin esin1 = new EgyenesSin(g, sinl1, val1);
        EgyenesSin esin2 = new EgyenesSin(g, val1, val2);

        OsszekotoSin osin2 = new OsszekotoSin(g, val1, so1);
        final OsszekotoSin osin3 = new OsszekotoSin(g, val2, so2);


        EgyenesSin esin3 = new EgyenesSin(g, so1, sinl2);
        EgyenesSin esin4 = new EgyenesSin(g, so2, sinl3);
        EgyenesSin esin6 = new EgyenesSin(g, val3, sinl5);
        final OsszekotoSin osin4 = new OsszekotoSin(g, sinl2, val3);
        EgyenesSin esin5 = new EgyenesSin(g, val2, val3);

        sinl1.megjelenik();
        sinl2.megjelenik();
        sinl3.megjelenik();
        sinl5.megjelenik();
        val1.megjelenik();
        val2.megjelenik();
        val3.megjelenik();
        Megallo m = new Megallo(g, esin1, 50, true);
        Megallo m2 = new Megallo(g, esin1, esin1.getSzelessegek().length-50);

        

        int i;
   

        //HOGY ÁLLJON A 3 VÁLTÓ?
        //val1.atvalt();
        val2.atvalt();
        val3.atvalt();

        //MELYIK SÍNTŐL SZERETNÉD A PÁLYÁT KÖVETNI?
        //lehetséges: esin1, esin2, esin3, esin4, esin5, esin6, osin2, osin3
        Sin s = esin3;


        //MILYEN MENETIRÁNY SZERINT?
        boolean forditottMenetirany = true;

     
/*
        if (!forditottMenetirany)
        {
            i = 0;
            Point p;
            while((p = s.getPont(i)) != null) {
                //System.out.println(i+": "+p);
                g.setColor(Color.ORANGE);
                g.drawLine(p.x, p.y, p.x, p.y);
                i++;
            }
        }
        else { 
            i = s.getPalya().length-1;
            Point p;
            while((p = s.getPont(i)) != null) {
                //System.out.println(i+": "+p);
                g.setColor(Color.ORANGE);
                g.drawLine(p.x, p.y, p.x, p.y);
                i--;
            }

        }
*/
    /*
        final Vonat[] v = {
            new Gyorsvonat(gV, esin6, 70, true),
            new Szemelyvonat(gV, esin6, 120, true),
            new Szemelyvonat(gV, esin1, 0),
            new Gyorsvonat(gV, esin1, 110),
            new Gyorsvonat(gV, esin1, 50)
        };

        ActionListener al = new ActionListener() {
            int db = 0;
            public void actionPerformed(ActionEvent e) {
                boolean nincs = true;
                for (Vonat vonat : v)
                    nincs &= vonat.isEltunt();
                if (!nincs) {
                    for (Vonat vonat : v)
                        vonat.mozdul();
                    if (v[1].getHatsoKerekSin() == osin4 && db == 0) {
                        db++;
                        val3.atvalt();
                    }
                    if (v[3].getHatsoKerekSin() == osin3 && db == 1) {
                        db++;
                        val2.atvalt();
                    }

                    for (Vonat vonat : v)
                        if (vonat.isKisiklott() || vonat.isMegallonVan()) {
                            vonat.megall();
                            vonat.halvanyodik();
                        }
                    ablak.repaint();
                }
            }
        };

        Timer t = new Timer(15, al);
        t.start();

    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Programvezerlo();
            }
        });
    }
    */
}