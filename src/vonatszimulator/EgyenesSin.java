package vonatszimulator;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class EgyenesSin extends Sin {

    public EgyenesSin(Graphics2D rajzterulet, Sinveg sv1, Sinveg sv2) {
        super(rajzterulet, sv1, sv2);
    }

    @Override
    protected void setAlakzat() {
        setAlakzat(
            new Line2D.Float(
                getSinveg(0).getPozicio(),
                getSinveg(1).getPozicio()));
    }

    @Override
    protected void setPalya() {
        Dimension meret = createMeret();
        double arany = (double)meret.height / meret.width;
        boolean csokkenoMagassag = isCsokkenoMagassag();
        int kezdoMagassag = getSinveg(0).getPozicio().y;
        int[] palya = new int[meret.width + 1];
        int pozicio;
        for (int i = 0; i < palya.length; i++) {
            pozicio = (int) Math.round(i * arany);
            if (csokkenoMagassag) palya[i] = kezdoMagassag - pozicio;
            else palya[i] = kezdoMagassag + pozicio;
        }
        setPalya(palya);
    }

}