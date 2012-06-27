package vonatszimulator;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

public abstract class Sin extends LathatoElem {
    private final float SINVASTAGSAG = 3;
    private BasicStroke sinRajzoloStroke = new BasicStroke(SINVASTAGSAG);
    private Graphics2D rajzterulet = getRajzterulet();
    private Megallo[] megallok = new Megallo[2];
    private Sinveg[] sinvegek = new Sinveg[2];
    private int[] palya;
    private int[] szelessegek;

    public Sin(Graphics2D rajzterulet, Sinveg sv1, Sinveg sv2) {
        super(rajzterulet);
        sinvegek[0] = sv1;
        sinvegek[1] = sv2;
        inicializalas();
    }

    public void addMegallo(Megallo megallo, boolean forditottMenetirany) {
        int index = (forditottMenetirany) ? 1 : 0;
        megallok[index] = megallo;
    }

    public Megallo getMegallo(boolean forditottMenetirany) {
        int index = (forditottMenetirany) ? 1 : 0;
        return megallok[index];
    }

    public boolean isVanMegalloja() {
        return megallok.length > 0;
    }

    protected Dimension createMeret() {
        Dimension meret = new Dimension();
        Point svP1 = sinvegek[0].getPozicio();
        Point svP2 = sinvegek[1].getPozicio();
        int szelesseg, magassag;
        szelesseg = Math.abs(svP1.x - svP2.x);
        magassag = Math.abs(svP1.y - svP2.y);
        meret.setSize(szelesseg, magassag);
        return meret;
    }

    protected boolean isCsokkenoMagassag() {
        int svM1 = sinvegek[0].getPozicio().y;
        int svM2 = sinvegek[1].getPozicio().y;
        return (svM2 < svM1);
    }

    @Override
    protected void inicializalas() {
        sinvegSorrendEldontes();
        setSzelessegek();
        setPalya();
        sinvegek[0].addSin(this);
        sinvegek[1].addSin(this);
        super.inicializalas();
    }

    public Sin getKovetkezoSin(int index) {
        if (!isTulindexelt(index)) return this;
        Sin kovetkezoSin;
        if (index < 0) 
            kovetkezoSin = sinvegek[0].getKovetkezoSin(true);
        else 
            kovetkezoSin = sinvegek[1].getKovetkezoSin();
        return kovetkezoSin;
    }

    public int getKovetkezoSinIndex(int index) {
        if (!isTulindexelt(index)) return index;
        else {
            Sin kovetkezoSin = getKovetkezoSin(index);
            if (kovetkezoSin == null) return 0;
            if (index < 0) {
                int kovetkezoSinPontokSzama = kovetkezoSin.getHossz();
                return index + kovetkezoSinPontokSzama;
            }
            else {
                int aktualisSinPontokSzama = getHossz();
                return index - aktualisSinPontokSzama;
            }
        }
    }

    public int getHossz() {
        return palya.length;
    }

    protected Point getPont(int index) {
        if (!isTulindexelt(index))
            return new Point(szelessegek[index], palya[index]);
        else {
            Sin kovetkezoSin = getKovetkezoSin(index);
            if (kovetkezoSin == null) return null;
            int ujIndex = getKovetkezoSinIndex(index);
            return kovetkezoSin.getPont(ujIndex);
        }
    }

    protected boolean isTulindexelt(int index) {
        return ! (( index < getHossz() ) && ( index >= 0 ));
    }

    private void setSzelessegek() {
        int kezdoSzelesseg = sinvegek[0].getPozicio().x;
        Dimension meret = createMeret();
        szelessegek = new int[meret.width + 1];
        for (int i=0; i < szelessegek.length; i++)
            szelessegek[i] = i + kezdoSzelesseg;
    }

    private void sinvegSorrendEldontes() {
        //csere ha az első szélesség poz nagyobb mint a második poz
        if (sinvegek[0].getPozicio().x > sinvegek[1].getPozicio().x)
            sinvegCsere();
    }

    private void sinvegCsere() {
        Sinveg csere = sinvegek[0];
        sinvegek[0] = sinvegek[1];
        sinvegek[1] = csere;
    }

    @Override
    protected void kirajzolodik() {
        rajzterulet.setColor(getSzin());
        rajzterulet.fill(sinRajzoloStroke.createStrokedShape(getAlakzat()));
    }

    public Sinveg getSinveg() {
        return getSinveg(false);
    }

    public Sinveg getSinveg(boolean forditottMenetirany) {
        return (forditottMenetirany) ? sinvegek[0] : sinvegek[1];
    }

    protected Sinveg getSinveg(int index) {
        return sinvegek[index];
    }

    @Override
    protected void setSzin() {
        setSzin(ALAPSZIN);
    }

    protected int[] getSzelessegek() {
        return szelessegek;
    }

    protected int[] getPalya() {
        return palya;
    }

    protected void setPalya(int[] palya) {
        this.palya = palya;
    }

    protected abstract void setPalya();

}