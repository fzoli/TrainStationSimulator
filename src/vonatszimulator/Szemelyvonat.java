package vonatszimulator;

import java.awt.Color;
import java.awt.Graphics2D;

public class Szemelyvonat extends Vonat {

    public Szemelyvonat(Graphics2D rajzterulet, Sin sin, int pozicio, boolean forditottMenetirany) {
        super(rajzterulet, sin, pozicio, forditottMenetirany);
    }

    public Szemelyvonat(Graphics2D rajzterulet, Sin sin, int pozicio) {
        this(rajzterulet, sin, pozicio, false);
    }

    @Override
    protected void setSzin() {
        setSzin(new Color(216, 216, 216));
    }
    
    @Override
    protected void setKeretszin() {
        setKeretszin(new Color(80, 130, 200));
    }

    @Override
    protected void setSebesseg() {
        setSebesseg(60);
    }

    @Override
    protected void setPrioritas() {
        setPrioritas(0);
    }

}