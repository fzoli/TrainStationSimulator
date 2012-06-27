package vonatszimulator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Date;

public class Gyorsvonat extends Vonat {

    public Gyorsvonat(Graphics2D rajzterulet, Sin sin, int pozicio, boolean forditottMenetirany) {
        super(rajzterulet, sin, pozicio, forditottMenetirany);
    }

    public Gyorsvonat(Graphics2D rajzterulet, Sin sin, int pozicio) {
        this(rajzterulet, sin, pozicio, false);
    }

    @Override
    protected void setSzin() {
        setSzin(new Color(155, 187, 89));
    }

    @Override
    protected void setKeretszin() {
        setKeretszin(new Color(103, 129, 48));
    }
    
    @Override
    protected void setSebesseg() {
        setSebesseg(90);
    }

    @Override
    protected void setPrioritas() {
        setPrioritas(1);
    }

    @Override
    protected void indulasIdobeallitas() {
        setIndulasIdeje(new Date());
    }
    
/*
    @Override
    public void megall() {
        setMegy(false);
    }
*/
}