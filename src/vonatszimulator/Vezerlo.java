package vonatszimulator;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

public class Vezerlo {
    private boolean jatekmod;
    private boolean sinlezaroLatszik;
    private final Dimension ALAP_TERULETMERET;
    private final Dimension EGYSEGMERET = new Dimension(15, 4);
    private Foablak ablak = new Foablak(this);
    private Forgalomiranyito forgalomiranyito;
    private Sinlezaro[] sinlezaro = new Sinlezaro[2];
    private Valto[] valto = new Valto[4];
    private SinOsszekoto[] sinOsszekoto = new SinOsszekoto[4];
    private Sin[] sin = new Sin[11];
    private ArrayList<Vonat> vonat = new ArrayList<Vonat>();

    private ActionListener kepernyofrissitesEsemenykezelo = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            kepernyofrissites();
        }

    };

    private Timer kepernyofrissitesUtemezo =
            new Timer(15, kepernyofrissitesEsemenykezelo);

    public Vezerlo() {
        ALAP_TERULETMERET = ablak.getTeruletMeret();
        inicializalas();
    }

    private void inicializalas() {
        ablakKozepre();
        setAblakIkon();
        jatekmod = ablak.isJatekmodBe();
        sinlezaroLatszik = ablak.isSinlezaroMutat();
        createVaganyelemek();
        kepernyofrissitesUtemezo.start();
    }

    private boolean vanVonat() {
        return vonat.size() > 0;
    }

    private void kepernyofrissites() {
        vonatKezeles();
        forgalomiranyitas();
        ablak.repaint();
    }

    private void forgalomiranyitas() {
        if (vanVonat() && !jatekmod)
            forgalomiranyito.iranyit();
    }

    private int getEgysegSzelesseg(double egyseg) {
        return getEgysegPozicio(egyseg, true);
    }

    private int getEgysegMagassag(double egyseg) {
        return getEgysegPozicio(egyseg, false);
    }

    private int getEgysegPozicio(double egyseg, boolean szelesseg) {
        Dimension teruletmeret = ablak.getTeruletMeret();
        Dimension egysegmeret = new Dimension(
                teruletmeret.width  / EGYSEGMERET.width,
                teruletmeret.height / EGYSEGMERET.height);
        int kerdes = szelesseg ? egysegmeret.width : egysegmeret.height;
        return (int)(egyseg * kerdes);
    }

    private double getKeparany() {
        int alapmeret = ALAP_TERULETMERET.width;
        int meret = ablak.getTeruletMeret().width;
        return (double) meret / alapmeret;
    }

    private void createSin() {
        Graphics2D rajzterulet = ablak.getSinterulet();
        sin[0] = new EgyenesSin(rajzterulet, sinlezaro[0], valto[0]);
        sin[1] = new EgyenesSin(rajzterulet, valto[0], valto[1]);
        sin[2] = new EgyenesSin(rajzterulet, valto[1], valto[2]);
        sin[3] = new EgyenesSin(rajzterulet, valto[2], valto[3]);
        sin[4] = new EgyenesSin(rajzterulet, valto[3], sinlezaro[1]);
        sin[5] = new OsszekotoSin(rajzterulet, valto[0], sinOsszekoto[0]);
        sin[6] = new EgyenesSin(rajzterulet, sinOsszekoto[0], sinOsszekoto[2]);
        sin[7] = new OsszekotoSin(rajzterulet, sinOsszekoto[2], valto[2]);
        sin[8] = new OsszekotoSin(rajzterulet, valto[1], sinOsszekoto[1]);
        sin[9] = new EgyenesSin(rajzterulet, sinOsszekoto[1], sinOsszekoto[3]);
        sin[10] = new OsszekotoSin(rajzterulet, sinOsszekoto[3], valto[3]);
    }

    private void createMegallo() {
        //csak akkor kellenek ha nem játékmód van
        if (!ablak.isJatekmodBe()) {
            Graphics2D rajzterulet = ablak.getSinvegTerulet();
            int felEgyseg = getEgysegSzelesseg(0.5);
            int egyseg = getEgysegSzelesseg(1.5);
            new Megallo(rajzterulet, sin[0], sin[0].getHossz() - felEgyseg, false, true);
            new Megallo(rajzterulet, sin[4], felEgyseg, true, true);
            new Megallo(rajzterulet, sin[2], sin[2].getHossz() - egyseg);
            new Megallo(rajzterulet, sin[2], egyseg, true);
            new Megallo(rajzterulet, sin[6], sin[6].getHossz() - felEgyseg);
            new Megallo(rajzterulet, sin[6], felEgyseg, true);
            new Megallo(rajzterulet, sin[9], sin[9].getHossz() - felEgyseg);
            new Megallo(rajzterulet, sin[9], felEgyseg, true);
        }
    }

    private void createSinOsszekoto() {
        int meret = EGYSEGMERET.height;
        Graphics2D rajzterulet = ablak.getSinvegTerulet();
        sinOsszekoto[0]=
                new SinOsszekoto(
                rajzterulet,
                new Point(getEgysegSzelesseg(5), getEgysegMagassag(1.3)));
        sinOsszekoto[1]=
                new SinOsszekoto(
                rajzterulet,
                new Point(getEgysegSzelesseg(6), getEgysegMagassag(meret - 1.3)));
        sinOsszekoto[2]=
                new SinOsszekoto(
                rajzterulet,
                new Point(getEgysegSzelesseg(9), getEgysegMagassag(1.3)));
        sinOsszekoto[3]=
                new SinOsszekoto(
                rajzterulet,
                new Point(getEgysegSzelesseg(10), getEgysegMagassag(meret - 1.3)));
    }

    private void createValto() {
        Graphics2D rajzterulet = ablak.getSinvegTerulet();
        int szelesseg = EGYSEGMERET.width;
        int magassagFele = getEgysegMagassag(EGYSEGMERET.height / 2);
        valto[0] = new Valto(
                rajzterulet,
                new Point(getEgysegSzelesseg(2), magassagFele));
        valto[1] = new Valto(
                rajzterulet,
                new Point(getEgysegSzelesseg(3), magassagFele));
        valto[2] = new Valto(
                rajzterulet,
                new Point(getEgysegSzelesseg(szelesseg-3), magassagFele), true);
        valto[3] = new Valto(
                rajzterulet,
                new Point(getEgysegSzelesseg(szelesseg-2), magassagFele), true);

    }

    private void createSinlezaro() {
        int szelesseg = EGYSEGMERET.width;
        int magassagFele = getEgysegMagassag(EGYSEGMERET.height / 2);
        Graphics2D rajzterulet = ablak.getSinvegTerulet();
        Sinlezaro sinlezaro1, sinlezaro2;
        if (sinlezaroLatszik) {
            sinlezaro1 =
                new Sinlezaro(
                    rajzterulet,
                    new Point(
                        getEgysegSzelesseg(0.5),
                        magassagFele
                    ));
            sinlezaro2 =
                new Sinlezaro(
                    rajzterulet,
                    new Point(
                        getEgysegSzelesseg(szelesseg - 0.5),
                        magassagFele
                    ));
        }
        else {
            sinlezaro1 =
                new Sinlezaro(
                    rajzterulet,
                    new Point(
                        getEgysegSzelesseg(-2),
                        magassagFele
                    ));
            sinlezaro2 =
                new Sinlezaro(
                    rajzterulet,
                    new Point(
                        getEgysegSzelesseg(szelesseg + 2),
                        magassagFele
                    ));
        }
        sinlezaro[0] = sinlezaro1;
        sinlezaro[1] = sinlezaro2;
    }

    private void ablakKozepre() {
        Dimension kepernyomeret = Isten.getKepernyomeret();
        int x, y;
        int szelesseg = ablak.getSize().width;
        int magassag = ablak.getSize().height;
        x = (kepernyomeret.width/2) - (szelesseg/2);
        y = (kepernyomeret.height/2) - (magassag/2);
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x + szelesseg > kepernyomeret.width) x = kepernyomeret.width - szelesseg;
        if (y + magassag > kepernyomeret.height) y = kepernyomeret.height - magassag;
        ablak.setBounds(x, y, szelesseg, magassag);
    }

    private void setAblakIkon() {
        ablak.setIconImage(Isten.getIkon());
    }

    public void uzenetfogadas(String uzenet) {
        uzenetfogadas(uzenet, null);
    }

    public void uzenetfogadas(String uzenet, String adat) {
        if (uzenet.equals("ABLAKMERET VALTOZAS"))
            ablakmeretValtozas();
        
        if (uzenet.equals("GOMBNYOMAS")) 
            gombnyomas(adat, false);
        
        if (uzenet.equals("CTRL GOMBNYOMAS")) 
            gombnyomas(adat, true);
        
        if (uzenet.equals("CHECKBOX KATTINTAS")) 
            checkboxKattintas();

        if (uzenet.equals("VALTAS ELSO VAGANYRA"))
            valtasElsoVaganyra(adat);

        if (uzenet.equals("VALTAS MASODIK VAGANYRA"))
            valtasMasodikVaganyra(adat);

        if (uzenet.equals("VALTAS HARMADIK VAGANYRA"))
            valtasHarmadikVaganyra(adat);

        if (uzenet.equals("VALTAS BALRA KI"))
            valtasBalraKi();

        if (uzenet.equals("VALTAS JOBBRA KI"))
            valtasJobbraKi();
    }

    private boolean szovegetLogikaira(String ertek) {
        return ertek.equals("1");
    }

    private void valtasBalraKi() {
        if (valto[0].getAtvaltva()) valto[0].atvalt();
        if (valto[1].getAtvaltva()) valto[1].atvalt();
    }

    private void valtasJobbraKi() {
        if (valto[2].getAtvaltva()) valto[2].atvalt();
        if (valto[3].getAtvaltva()) valto[3].atvalt();
    }

    private void valtasElsoVaganyra(String forditottMenetirany) {
        boolean irany = szovegetLogikaira(forditottMenetirany);
        if (!irany) {
            if (!valto[0].getAtvaltva()) valto[0].atvalt();
        }
        else {
            if (valto[3].getAtvaltva()) valto[3].atvalt();
            if (!valto[2].getAtvaltva()) valto[2].atvalt();
        }
    }

    private void valtasMasodikVaganyra(String forditottMenetirany) {
        boolean irany = szovegetLogikaira(forditottMenetirany);
        if (!irany) {
            valtasBalraKi();
        }
        else {
            valtasJobbraKi();
        }
    }

    private void valtasHarmadikVaganyra(String forditottMenetirany) {
        boolean irany = szovegetLogikaira(forditottMenetirany);
        if (!irany) {
            if (valto[0].getAtvaltva()) valto[0].atvalt();
            if (!valto[1].getAtvaltva()) valto[1].atvalt();
        }
        else {
            if (!valto[3].getAtvaltva()) valto[3].atvalt();
        }
    }

    private void ablakmeretValtozas() {
        try {
            Dimension meret = ablak.getTeruletMeret();
            if (meret.height > 200 && meret.width > 300) {
                ujrakezdes();
            }
        }
        catch(NullPointerException ex) {}
    }

    private void ujrakezdes() {
        ablak.setTeruletek();
        createVaganyelemek();
        vonat.clear();
    }

    private void createVaganyelemek() {
        createSinlezaro();
        createValto();
        createSinOsszekoto();
        createSin();
        createMegallo();
        createForgalomiranyito();
    }

    private void createForgalomiranyito() {
        forgalomiranyito = new Forgalomiranyito(
                this,
                createElsoVagany(),
                createMasodikVagany(),
                createHarmadikVagany(),
                sin[0],
                sin[4],
                vonat);
    }

    private Vagany createElsoVagany() {
        Vagany v = new Vagany();
        v.addSin(sin[5]);
        v.addSin(sin[6]);
        v.addSin(sin[7]);
        v.addSin(sin[3]);
        return v;
    }

    private Vagany createMasodikVagany() {
        Vagany v = new Vagany();
        v.addSin(sin[1]);
        v.addSin(sin[2]);
        v.addSin(sin[3]);
        return v;
    }

    private Vagany createHarmadikVagany() {
        Vagany v = new Vagany();
        v.addSin(sin[1]);
        v.addSin(sin[8]);
        v.addSin(sin[9]);
        v.addSin(sin[10]);
        return v;
    }

    private void gombnyomas(String gomb, boolean isCtrl) {
        int kod = Integer.parseInt(gomb);
        switch(kod) {
            case 37: balGombnyomas(isCtrl); break;
            case 39: jobbGombnyomas(isCtrl); break;
            case 38: felGombnyomas(isCtrl); break;
            case 40: leGombnyomas(isCtrl); break;
        }
    }

    private void vonatKezeles() {
        if (vanVonat()) {
            vonatUjrarajzolas();
            eltuntVonatTorlese();
        }
    }

    private void vonatUjrarajzolas() {
        for (Vonat v : vonat) {
            if (v.isKisiklott())
                v.halvanyodik();
            if (vonatOsszeutkozott(v)) {
                v.kisiklik();
                v.megall();
            }
            v.mozdul();
        }
    }

    private void eltuntVonatTorlese() {
        for (int i = 0; i<vonat.size(); i++)
            if(vonat.get(i).isEltunt())
                vonat.remove(i);
    }

    private boolean vonatOsszeutkozott(Vonat v) {
        Point p1 = v.createVonatElejePont();
        Point p2 = v.getHatsoKerekPont();
        Point p3, p4;
        boolean igen = false;
        for (Vonat v2 : vonat) {
            if (v2!=v) {
                p3 = v2.getElsoKerekPont();
                p4 = v2.getHatsoKerekPont();
                igen |= szakaszokMetszikEgymast(p1, p2, p3, p4);
                if (igen) return true;
                igen |= szakaszokFedikEgymast(p1, p2, p3, p4);
                if (igen) return true;
            }
        }
        return igen;
    }

    private boolean szakaszokFedikEgymast(Point x1, Point x2, Point y1, Point y2) {
        int xMin = Math.min(x1.x, x2.x);
        int xMax = Math.max(x1.x, x2.x);
        int yMin = Math.min(y1.x, y2.x);
        int yMax = Math.max(y1.x, y2.x);
        boolean hosszusagEgyezik = (x1.y == y1.y);
        for (int i = xMin; i <= xMax; i++)
            for (int j = yMin; j <= yMax; j++)
                if (i == j) return hosszusagEgyezik;
        return false;
    }

    private boolean szakaszokMetszikEgymast(Point x1, Point x2, Point y1, Point y2) {
        Point p = (lineIntersectLine(x1, x2, y1, y2));
        return p!=null;
    }

    private Point lineIntersectLine(Point A, Point B, Point E, Point F) {
        return lineIntersectLine(A, B, E, F, true);
    }

    private Point lineIntersectLine(Point A, Point B, Point E, Point F, boolean as_seg) {
        Point ip;
        int a1,a2,b1,b2,c1,c2;
        a1= B.y-A.y;
        b1= A.x-B.x;
        c1= B.x*A.y - A.x*B.y;
        a2= F.y-E.y;
        b2= E.x-F.x;
        c2= F.x*E.y - E.x*F.y;
        int denom = a1*b2 - a2*b1;
        if (denom == 0)
            return null;
        ip = new Point();
        ip.x = (int)((b1*c2 - b2*c1)/denom);
        ip.y = (int)((a2*c1 - a1*c2)/denom);
        if(as_seg){
            if(Math.pow(ip.x - B.x, 2) + Math.pow(ip.y - B.y, 2) > Math.pow(A.x - B.x, 2) + Math.pow(A.y - B.y, 2))
                return null;
            if(Math.pow(ip.x - A.x, 2) + Math.pow(ip.y - A.y, 2) > Math.pow(A.x - B.x, 2) + Math.pow(A.y - B.y, 2))
                return null;
            if(Math.pow(ip.x - F.x, 2) + Math.pow(ip.y - F.y, 2) > Math.pow(E.x - F.x, 2) + Math.pow(E.y - F.y, 2))
                return null;
            if(Math.pow(ip.x - E.x, 2) + Math.pow(ip.y - E.y, 2) > Math.pow(E.x - F.x, 2) + Math.pow(E.y - F.y, 2))
                return null;
        }
        return ip;
    }

    private void addVonat(boolean gyorsvonat, boolean forditottIrany) {
        //hogy ne legyen torlódás csak akkor jön létre ha nincs a sinen senki
        if (!forgalomiranyito.vanVonatLetrehozoSinen(forditottIrany) &&
                (jatekmod || forgalomiranyito.isJohetVonat(forditottIrany))) {
        Graphics2D terulet = ablak.getVonatTerulet();
        Sin s;
        int pozicio;
        if (!forditottIrany) {
            s = sin[0];
            pozicio = 0;
        }
        else {
            s = sin[4];
            pozicio = s.getHossz();
        }
        if (!gyorsvonat)
            vonat.add(new Szemelyvonat(terulet, s, pozicio, forditottIrany));
        else
            vonat.add(new Gyorsvonat(terulet, s, pozicio, forditottIrany));
        getUtolsoVonat().setKepernyoarany(getKeparany());
        }
    }

    private Vonat getUtolsoVonat() {
        return vonat.get(vonat.size()-1);
    }

    private void balGombnyomas(boolean isCtrl) {
            addVonat(isCtrl, true);
    }

    private void jobbGombnyomas(boolean isCtrl) {
            addVonat(isCtrl, false);
    }

    private void felGombnyomas(boolean isCtrl) {
        valtotKapcsol(true, isCtrl);
    }

    private void leGombnyomas(boolean isCtrl) {
        valtotKapcsol(false, isCtrl);
    }

    private void valtotKapcsol(boolean fel, boolean forditottIrany) {
        if (jatekmod)
            if (forditottIrany)
                if (fel) valto[2].atvalt();
                else valto[3].atvalt();
            else
                if (fel) valto[0].atvalt();
                else valto[1].atvalt();
    }

    private void checkboxKattintas() {
        sinlezaroLatszik = ablak.isSinlezaroMutat();
        jatekmod = ablak.isJatekmodBe();
        ujrakezdes();
    }

}