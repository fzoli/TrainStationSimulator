package vonatszimulator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.util.Date;
import vonatszimulator.alakzat.ForgathatoPolygon;

public abstract class Vonat extends MozgathatoElem {
    private int prioritas;
    private Date indulasIdeje;
    private boolean kisiklott = false;
    private double meretarany = 3;
    private int pixelertek = 60;
    private double kepernyoarany = 1;
    private boolean forditottMenetirany;
    private Vonatkerek[] kerekek = new Vonatkerek[2];
    private int sebesseg;
    private boolean megy;

    private int magassag = 8;
    private int kerektavolsag = 10;
    private int vegpozicio_hatsokerekTavolsag =  2;
    private int elsokerek_vonatElejeTavolsag  =  4;

    public Vonat(Graphics2D rajzterulet, Sin sin,
            int pozicio, boolean forditottMenetirany) {
        super(rajzterulet);
        this.forditottMenetirany = forditottMenetirany;
        kerekInicializalas(sin, pozicio);
        inicializalas();
    }

    @Override
    protected void inicializalas() {
        setSebesseg();
        setPrioritas();
        elindul();
        super.inicializalas();
    }

    @Override
    protected void setKeretvastagsag() {
        setKeretvastagsag(2);
    }

    protected void setKepernyoarany(double arany) {
        kepernyoarany = arany;
    }

    protected void kisiklik() {
        kisiklott = true;
    }

    public Megallo getMegallo() {
        return getSin().getMegallo(forditottMenetirany);
    }

    public boolean isMegallonVan() {
        Megallo megallo = getMegallo();
        if (megallo == null) return false;
        if (getSin() != getHatsoKerekSin()) return false;
        int megalloPozicio = megallo.getPozicio();
        int elsoPozicio = getVonatElejePozicio();
        int hatsoPozicio = getHatsoKerek().getPozicio();
        int min = Math.min(elsoPozicio, hatsoPozicio);
        int max = Math.max(elsoPozicio, hatsoPozicio);
        for (int i=min; i<=max; i++) {
            if (i == megalloPozicio) 
                return true;
        }
        return false;
    }

    public boolean isIrany(boolean irany) {
        return !(isForditottMenetirany() ^ irany);
    }

    public Sin getSin() {
        return getElsoKerek().getSin();
    }

    protected Sin getHatsoKerekSin() { //private
        return getHatsoKerek().getSin();
    }

    private boolean isElsoKerekAlul() {
        return getElsoKerek().getPozicioPont().y
                >
               getHatsoKerek().getPozicioPont().y;
    }

    private boolean isElsoKerekFelul() {
        return getElsoKerek().getPozicioPont().y
                <
               getHatsoKerek().getPozicioPont().y;
    }

    private int getMagassag() {
        return (int)Math.round(magassag * meretarany);
    }

    private int getMagassagFele() {
        return getMagassag() / 2;
    }

    private int getKerektavolsag() {
        return (int)Math.round(kerektavolsag * meretarany);
    }

    private int getElsokerekVonatElejeTavolsag() {
        return (int)Math.round(elsokerek_vonatElejeTavolsag * meretarany);
    }

    private int getVegpozicioElsokerekTavolsag() {
        return getKerektavolsag() + getVegpozicioHatsokerekTavolsag();
    }

    private int getVegpozicioHatsokerekTavolsag() {
        return (int)Math.round(vegpozicio_hatsokerekTavolsag * meretarany);
    }

    private int getKerektavolsagFele() {
        return getKerektavolsag() / 2;
    }

    private Vonatkerek getElsoKerek() {
        return (forditottMenetirany) ? kerekek[0] : kerekek[1];
    }

    private Vonatkerek getHatsoKerek() {
        return (forditottMenetirany) ? kerekek[1] : kerekek[0];
    }

    public boolean isForditottMenetirany() {
        return forditottMenetirany;
    }

    @Override
    protected Shape createTorloAlakzat() {
        meretarany++;
        setAlakzat();
        Shape alakzat = getAlakzat();
        meretarany--;
        setAlakzat();
        return alakzat;
    }

    private Point getKerekpont(Vonatkerek kerek) {
        return kerek.getPozicioPont();
    }

    public boolean megy() {
        return megy;
    }

    public boolean megallotElhagyta() { //csak a nem váltó megállókra jó
        return (megy) && (indulasIdeje != null);
    }

    public boolean isKisiklott() {
        boolean kerekekSinen =
                getElsoKerek().isSinenVan()
                    &&
                getHatsoKerek().isSinenVan();
        if (!kerekekSinen) return true;
        kisiklott |= !isKerekekAzonosSinen();
        return kisiklott;
    }

    private boolean isKerekekElteroSinen() {
        return getElsoKerek().getSin() != getHatsoKerek().getSin();
    }

    private boolean isValtoKovetkezik() {
        return getHatsoKerekSin().getSinveg(forditottMenetirany) instanceof Valto;
    }

    public boolean isValtonVan() {
        return isKerekekElteroSinen() && isValtoKovetkezik();
    }

    protected Point getElsoKerekPont() {
        return getKerekpont(getElsoKerek());
    }


    protected Point getHatsoKerekPont() {
        return getKerekpont(getHatsoKerek());
    }

    private boolean isKerekekAzonosSinen() {
        int kerektavolsag = getKerektavolsag();
        int irany = (isForditottMenetirany()) ? -1 : 1;
        kerektavolsag *= irany;
        Sin hatsoKerekSine = getHatsoKerekSin();
        int hatsoKerekPozicioja = getHatsoKerek().getPozicio();
        Point elsoKerekPontja =
               getElsoKerekPont();
        Point hatsoKerekPontja =
               hatsoKerekSine.getPont(hatsoKerekPozicioja + kerektavolsag);
        if (hatsoKerekPontja == null) return false; //van tovább sín
        double tavolsag = elsoKerekPontja.distance(hatsoKerekPontja);
        return (tavolsag == 0);
    }

    private Point createKozeppont() {
        Point p1 = getKerekpont(getElsoKerek());
        Point p2 = getKerekpont(getHatsoKerek());
        return new Point(
                ((p1.x - p2.x) / 2) + p2.x,
                ((p1.y - p2.y) / 2) + p2.y);
    }

    public Point createVonatElejePont() {
        int tavolsagKozepponttol =
                getKerektavolsagFele() + getElsokerekVonatElejeTavolsag();
        int irany = (isForditottMenetirany()) ? -1 : 1;
        tavolsagKozepponttol *= irany;
        Point pont = createKozeppont();
        pont.x += tavolsagKozepponttol;
        return pont;
    }

    private int getVonatElejePozicio() {
        int pozicio = getElsoKerek().getPozicio();
        int tavolsag = (isForditottMenetirany()) ? -1 : 1;
        tavolsag *= getElsokerekVonatElejeTavolsag();
        return pozicio + tavolsag;
    }

    private double createFok() {
        Point p1 = getKerekpont(getElsoKerek());
        Point p2 = getKerekpont(getHatsoKerek());
        double arcTg = Math.atan2(
                Math.abs(p2.y - p1.y),
                Math.abs(p2.x - p1.x));
        double fok = arcTg * (180 / Math.PI);
        if (isElsoKerekAlul()  &&  forditottMenetirany) fok = 360 - fok;
        if (isElsoKerekFelul() && !forditottMenetirany) fok = 360 - fok;
        return fok;
    }

    @Override
    public void mozdul() {
        megallofigyeles();
        if (megy) {
            eltunik();
            getElsoKerek().gordul();
            getHatsoKerek().gordul();
            megjelenik();
        }
    }

    private void megallofigyeles() {
        if (isMegallonVan()) {
            if (megalloValtomegallo()) { //ha váltómegállón van
                if (getMegallo().piros()) {
                    if (megy) megall();
                }
                else {
                    if (!megy) elindul();
                }
            }
            else { //ha nem váltó megállón van
                if (indulasIdeje==null) {
                    indulasIdobeallitas();
                    megall();
                }
                if (indulniAkar() && !getMegallo().piros() && !megy) {
                    elindul();
                }
            }
        }
    }

    public boolean indulniAkar() {
        if (indulasIdeje == null) return false;
        else {
            Date most = new Date();
            return most.after(indulasIdeje);
        }
    }

    protected void indulasIdobeallitas() {
        Date most = new Date();
        int random = (int)(Math.random() * 5000) + 5000;
        indulasIdeje = new Date(most.getTime() + random);
    }

    protected void setIndulasIdeje(Date ido) {
        indulasIdeje = ido;
    }

    private boolean megalloValtomegallo() {
        return getMegallo().valtoMegallo();
    }

    public void elindul() {
        megy = true;
    }

    public void megall() {
        megy = false;
    }

    @Override
    protected void setAlakzat() {
        Point[] p = createAlakzatpontok();
        int[] x = {p[0].x, p[1].x, p[2].x, p[3].x, p[4].x};
        int[] y = {p[0].y, p[1].y, p[2].y, p[3].y, p[4].y};
        ForgathatoPolygon polygon = new ForgathatoPolygon(x, y, 5);
        polygon.rotate(createFok(), createKozeppont());
        setAlakzat(polygon);
    }

    private Point[] createAlakzatpontok() {
        Point p1,p2,p3,p4;
        Point kozeppont = createKozeppont();
        Point p5 = createVonatElejePont();
        int magassag = getMagassag();
        int kerektavFele = getKerektavolsagFele();
        int magassagpont = kozeppont.y - getMagassagFele();
        int tavolsag = getKerektavolsagFele() +
                       getVegpozicioHatsokerekTavolsag();
        if (isForditottMenetirany()) {
            p1 = new Point(kozeppont.x - kerektavFele, magassagpont);
            p2 = new Point(kozeppont.x + tavolsag,     magassagpont);
        }
        else {
            p1 = new Point(kozeppont.x + kerektavFele, magassagpont);
            p2 = new Point(kozeppont.x - tavolsag,     magassagpont);
        }
        p3 = new Point(p2.x, p2.y + magassag);
        p4 = new Point(p1.x, p1.y + magassag);
        Point[] pontok = {p1, p2, p3, p4, p5};
        return pontok;
    }

    protected double getLepestav() {
        return (double)sebesseg / (pixelertek / kepernyoarany);
    }

    protected abstract void setSebesseg();

    protected void setSebesseg(int sebesseg) {
        this.sebesseg = sebesseg;
    }

    private int getHatsokerekKezdopozicio(int vegpozicio) {
        int tavolsag = getVegpozicioHatsokerekTavolsag();
        if (forditottMenetirany) tavolsag *= -1;
        return vegpozicio + tavolsag;
    }

    private int getElsokerekKezdopozicio(int vegpozicio) {
        int tavolsag = getVegpozicioElsokerekTavolsag();
        if (forditottMenetirany) tavolsag *= -1;
        return vegpozicio + tavolsag;
    }

    private void kerekInicializalas(Sin sin, int vegpozicio) {
        int hatsokerekPozicio = getHatsokerekKezdopozicio(vegpozicio);
        int elsokerekPozicio  = getElsokerekKezdopozicio(vegpozicio);
        if (forditottMenetirany) {
            kerekek[0] = new Vonatkerek(this, sin, elsokerekPozicio);
            kerekek[1] = new Vonatkerek(this, sin, hatsokerekPozicio);
        }
        else {
            kerekek[0] = new Vonatkerek(this, sin, hatsokerekPozicio);
            kerekek[1] = new Vonatkerek(this, sin, elsokerekPozicio);
        }
    }

    private int atlatszosag = 255;

    private Color createHalvanyodottSzin(Color szin) {
        int r = szin.getRed();
        int g = szin.getGreen();
        int b = szin.getBlue();
        return new Color(r, g, b, atlatszosag);
    }

    public void halvanyodik() {
        if (atlatszosag > 0) {
            atlatszosag -= 3;
            setSzin(createHalvanyodottSzin(getSzin()));
            setKeretszin(createHalvanyodottSzin(getKeretszin()));
            eltunik();
            megjelenik();
        }
    }

    public boolean isEltunt() {
        return atlatszosag == 0;
    }

    protected abstract void setPrioritas();
    
    protected void setPrioritas(int prioritas) {
        this.prioritas = prioritas;
    }

    public int getPrioritas() {
        return prioritas;
    }

}