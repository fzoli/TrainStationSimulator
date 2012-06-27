package vonatszimulator;

import vonatszimulator.alakzat.Polyline;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class OsszekotoSin extends Sin {

    public OsszekotoSin(Graphics2D rajzterulet, Sinveg sv1, Sinveg sv2) {
        super(rajzterulet, sv1, sv2);
    }

    @Override
    protected void setAlakzat() {
        int[] szelessegPontok = getSzelessegek();
        int[] magassagPontok = getPalya();
        int meret = magassagPontok.length;
        setAlakzat(new Polyline(szelessegPontok, magassagPontok, meret));
    }

    @Override
    protected void setPalya() {
        Dimension meret = createMeret();
        int szelesseg = meret.width;
        double hosszusagFele = meret.height / 2;
        int kezdoMagassag = getSinveg(0).getPozicio().y;
        int vegMagassag = getSinveg(1).getPozicio().y;
        boolean csokkenoMagassag = isCsokkenoMagassag();
        double piPsz = Math.PI / szelesseg;
        int[] palya = new int[szelesseg];
        double x, y;
        for (int i = 0; i < szelesseg; i++) {
            x = i * piPsz;
            y = Math.cos(x) * hosszusagFele + hosszusagFele;
            if (csokkenoMagassag) palya[i] = ((int)Math.round(y))+ vegMagassag;
            else palya[i] = ((int)Math.round(y))+ kezdoMagassag;
        }
        if (!csokkenoMagassag) palya = createInverzTomb(palya);
        setPalya(palya);
    }

    private int[] createInverzTomb(int[] tomb) {
        int hossz = tomb.length;
        int[] inverzTomb = new int[hossz];
        for (int i=hossz-1; i>=0; i--)
            inverzTomb[hossz-1-i] = tomb[i];
        return inverzTomb;
    }

}