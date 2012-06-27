package vonatszimulator.alakzat;

import java.awt.geom.Path2D;

public class Polyline extends Path2D.Float {

    public Polyline(int[] xpoints, int[] ypoints, int npoints) {
        super(WIND_EVEN_ODD, npoints);
        moveTo(xpoints[0], ypoints[0]);
        for (int index = 1; index < npoints; index++)
            lineTo(xpoints[index], ypoints[index]);
    }

}