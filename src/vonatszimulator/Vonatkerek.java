package vonatszimulator;

import java.awt.Point;

public class Vonatkerek {

    private Vonat vonat;
    private Sin sin;
    private Sin elozoSin;
    private int pozicio;
    private double maradekUt = 0;
    private int elozoLepestav = 0;

    public Vonatkerek(Vonat vonat, Sin kezdosin, int kezdopozicio) {
        this.vonat = vonat;
        this.sin = kezdosin;
        this.pozicio = kezdopozicio;
        elhelyezkedik();
    }

    protected Point getPozicioPont() {
        return getSin().getPont(getPozicio());
    }

    private boolean isForditottMenetirany() {
        return vonat.isForditottMenetirany();
    }

    private double getLepestav() {
        return vonat.getLepestav();
    }

    protected Sin getSin() {
        return (isSinenVan()) ? sin : elozoSin;
    }

    protected int getPozicio() {
        return (isSinenVan()) ? pozicio : getElozoPozicio();
    }

    private int getElozoPozicio() {
        int elozoPozicio = elozoLepestav;
        elozoPozicio *= -1;
        elozoPozicio += pozicio;
        return elozoPozicio;
    }

    private void elhelyezkedik() {
        if (isSinenVan() && sin.isTulindexelt(pozicio)) {
            Sin kovetkezoSin = sin.getKovetkezoSin(pozicio);
            int ujPozicio = sin.getKovetkezoSinIndex(pozicio);
            if (kovetkezoSin == null)
                elozoSin = sin;
            else
                pozicio = ujPozicio;
            sin = kovetkezoSin;
        }
    }

    protected boolean isSinenVan() {
        return (sin != null);
    }

    private void poziciotAllit() {
        double lepestav = getLepestav();
        if (isForditottMenetirany()) lepestav *= -1;
        maradekUt += lepestav % 1;
        lepestav = (int)lepestav;
        int aktualisLepestav = (int)lepestav + (int)maradekUt;
        pozicio += aktualisLepestav;
        elozoLepestav = aktualisLepestav;
        maradekUt = maradekUt % 1;
    }

    protected void gordul() {
        if (isSinenVan()) {
            poziciotAllit();
            elhelyezkedik();
        }
    }

}