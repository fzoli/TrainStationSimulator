package vonatszimulator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

public abstract class LathatoElem {
    protected final Color ALAPSZIN = Color.BLACK;
    protected final int ELSIMITO_SZELESSEG = 4;
    protected final int ELSIMITO_SZELESSEG_FELE = ELSIMITO_SZELESSEG/2;
    private Graphics2D rajzterulet;
    private Color szin;
    private Shape alakzat;

    public LathatoElem(Graphics2D rajzterulet) {
        this.rajzterulet = rajzterulet;
    }

    protected Color getSzin() {
        return szin;
    }

    protected void setSzin(Color szin) {
        if (szin == null) szin = ALAPSZIN;
        this.szin = szin;
    }

    protected Shape getAlakzat() {
        return alakzat;
    }

    protected void setAlakzat(Shape alakzat) {
        this.alakzat = alakzat;
    }

    protected Graphics2D getRajzterulet() {
        return rajzterulet;
    }
    


    //az utódosztályokban kell meghívni
    protected void inicializalas() {
        setSzin();
        megjelenik();
    }

    protected void megjelenik() {
        setRajzteruletRajzolasra();
        setAlakzat();
        kirajzolodik();
    }

    protected void kirajzolodik() {
        rajzterulet.setColor(szin);
        rajzterulet.fill(alakzat);
    }

    protected abstract void setAlakzat();

    protected abstract void setSzin();

    private void setRajzteruletRajzolasra() {
        rajzterulet.setComposite(
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)
                );
    }

}