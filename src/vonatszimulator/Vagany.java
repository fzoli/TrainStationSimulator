package vonatszimulator;

import java.util.ArrayList;

public class Vagany {
    private ArrayList<Sin> sin = new ArrayList<Sin>();

    public void addSin(Sin sin) {
        this.sin.add(sin);
    }

    public ArrayList<Sin> getSin() {
        return sin;
    }

}