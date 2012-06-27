package vonatszimulator;

import java.util.ArrayList;

public class Forgalomiranyito {

    private Vagany vaganyElso;
    private Vagany vaganyMasodik;
    private Vagany vaganyHarmadik;
    private ArrayList<Vonat> vonat;
    private Sin sinBejovo;
    private Sin sinKimeno;
    private Vezerlo programVezerlo;

    public Forgalomiranyito(Vezerlo vezerlo, Vagany elso, Vagany masodik, Vagany harmadik, Sin bejovo, Sin kimeno, ArrayList<Vonat> vonatok) {
        programVezerlo = vezerlo;
        vaganyElso = elso;
        vaganyMasodik = masodik;
        vaganyHarmadik = harmadik;
        sinBejovo = bejovo;
        sinKimeno = kimeno;
        vonat = vonatok;
    }

    public void iranyit() {
        beengedhetoVonatBeengedese();
        kiengedhetoVonatKiengedese();
    }

    private void beengedhetoVonatBeengedese() {
        Vagany vagany;
        ArrayList<Vonat> bejovo = getPrioritasrendezettVonatlista(getBeerkezoVonatLista());
        for (Vonat v : bejovo) {
            vagany = getSzabadVagany();
            if (vagany != null) {
                boolean irany = v.isForditottMenetirany();
                if (!valtoKozottVanVonat()) {
                    valtasMegadottVaganyra(vagany, irany); 
                    v.getMegallo().setPiros(false);
                    break;
                }
            }
            else {
                v.getMegallo().setPiros(true);
            }
        }
    }
    
    private void valtasMegadottVaganyra(Vagany v, boolean irany) {
        if (v == vaganyElso) valtasElsoVaganyra(irany);
        if (v == vaganyMasodik) valtasMasodikVaganyra(irany);
        if (v == vaganyHarmadik) valtasHarmadikVaganyra(irany);
    }

    private void kiengedhetoVonatKiengedese() {
        ArrayList<Vonat> kimeno = getPrioritasrendezettVonatlista(getKimenniAkaroVonatLista());
        boolean kimenoSinenNincsVonat, nincsElindultVonat;
        for (Vonat v : kimeno) {
            kimenoSinenNincsVonat = !vanVonatLetrehozoSinen(!v.isForditottMenetirany());
            nincsElindultVonat = !vanMegallotElhagyottVonat(v.isForditottMenetirany());
            Megallo m = v.getMegallo();
            if (kimenoSinenNincsVonat && nincsElindultVonat) {
                if (!valtoKozottVanVonat()) {
                    if (m != null) m.setPiros(false);
                    valtasKi(v.isForditottMenetirany());
                    break;
                }
            }
            else {
                if (m != null) m.setPiros(true);
            }
        }
    }

    private void valtasKi(boolean forditottIrany) {
        if (forditottIrany) valtasBalraKi();
        else valtasJobbraKi();
    }

    private ArrayList<Vonat> getBeerkezoVonatLista() {
        ArrayList<Vonat> lista = new ArrayList<Vonat>();
        for (Vonat v : vonat) {
            if (v.getSin() == sinBejovo && !v.isForditottMenetirany()) {
                lista.add(v);
            }
            if (v.getSin() == sinKimeno && v.isForditottMenetirany()) {
                lista.add(v);
            }
        }
        return lista;
    }

    private ArrayList<Vonat> getKimenniAkaroVonatLista() {
        ArrayList<Vonat> lista = new ArrayList<Vonat>();
        for (Vonat v : vonat) {
            if (v.indulniAkar()) {
                lista.add(v);
            }
        }
        return lista;
    }

    private boolean vanKimenniAkaroVonat(boolean forditottIrany) {
        for (Vonat v : vonat) {
            if (v.indulniAkar() && v.isIrany(forditottIrany)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Vonat> getPrioritasrendezettVonatlista(ArrayList<Vonat> lista) {
        ArrayList<Vonat> rendezettLista = new ArrayList<Vonat>();
        ArrayList<Vonat> p0 = new ArrayList<Vonat>();
        ArrayList<Vonat> p1 = new ArrayList<Vonat>();
        for (Vonat v : lista) {
            if (v.getPrioritas() == 0) p0.add(v);
            if (v.getPrioritas() == 1) p1.add(v);
        }
        for (Vonat v : p1) 
            rendezettLista.add(v);
        for (Vonat v : p0) 
            rendezettLista.add(v);
        return rendezettLista;
    }

    private void uzenetVezerlonek(String uzenet, String adat) {
        programVezerlo.uzenetfogadas(uzenet, adat);
    }

    private void uzenetVezerlonek(String uzenet) {
        programVezerlo.uzenetfogadas(uzenet, null);
    }

    private boolean vonatBeFogTudniAllni(boolean forditottIrany) {
        return !vanVonatVaganyon(vaganyElso, !forditottIrany) ||
               !vanVonatVaganyon(vaganyMasodik, !forditottIrany) ||
               !vanVonatVaganyon(vaganyHarmadik, !forditottIrany);
    }

    private boolean masikLetrehozoSinenNincsEllentetesIranyuVonat(boolean forditottIrany) {
        return !vanVonatLetrehozoSinen(!forditottIrany, !forditottIrany);
    }

    public boolean isJohetVonat(boolean forditottIrany) {
        return vonatBeFogTudniAllni(forditottIrany) &&
               masikLetrehozoSinenNincsEllentetesIranyuVonat(forditottIrany) &&
               !vanKimenniAkaroVonat(!forditottIrany) &&
               !valtoKozottVanVonat();
    }

    private Sin getLetrehozoSin(boolean forditottIrany) {
        if (!forditottIrany) return sinBejovo;
        else return sinKimeno;
    }

    private boolean valtoKozottVanVonat() {
        for (Vonat v : vonat) {
            if (v.getSin() == getValtokoztiSin(true) || 
                v.getSin() == getValtokoztiSin(false) ||
                v.getHatsoKerekSin() == getValtokoztiSin(true) ||
                v.getHatsoKerekSin() == getValtokoztiSin(false))
                return true;
        }
        return false;
    }

    private Sin getValtokoztiSin(boolean forditottIrany) {
        if (forditottIrany) return vaganyMasodik.getSin().get(2);
        else return vaganyMasodik.getSin().get(0);
    }

    private boolean vanMegallotElhagyottVonat(boolean forditottIrany) {
        return vanMegallotElhagyottVonat(vaganyElso, forditottIrany) ||
               vanMegallotElhagyottVonat(vaganyMasodik, forditottIrany) ||
               vanMegallotElhagyottVonat(vaganyHarmadik, forditottIrany) ||
               vanVonatLetrehozoSinen(forditottIrany, !forditottIrany) ||
               vanVonatLetrehozoSinen(!forditottIrany, forditottIrany);
    }

    private boolean vanMegallotElhagyottVonat(Vagany vagany, boolean forditottIrany) {
        for (Sin s : vagany.getSin()) {
            for (Vonat v : vonat) {
                if (v.getSin()==s) {
                    if (v.megallotElhagyta() && v.isIrany(forditottIrany)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean vanVonatLetrehozoSinen(boolean forditottIrany) {
        return vanVonatLetrehozoSinen(forditottIrany, forditottIrany);
    }

    private boolean vanVonatLetrehozoSinen(boolean sinForditottIrany, boolean vonatForditottIrany) {
        Sin sin = getLetrehozoSin(sinForditottIrany);
        boolean van = false;
        for (Vonat v : vonat) {
            if (v.isIrany(vonatForditottIrany)) {
                van |= v.getSin() == sin;
                van |= v.isValtonVan();
            }
        }
        return van;
    }

    private void valtasBalraKi() {
        uzenetVezerlonek("VALTAS BALRA KI");
    }

    private void valtasJobbraKi() {
        uzenetVezerlonek("VALTAS JOBBRA KI");
    }

    private void valtasElsoVaganyra(boolean forditottMenetirany) {
        String irany = logikaitSzovegre(forditottMenetirany);
        uzenetVezerlonek("VALTAS ELSO VAGANYRA", irany);
    }

    private void valtasMasodikVaganyra(boolean forditottMenetirany) {
        String irany = logikaitSzovegre(forditottMenetirany);
        uzenetVezerlonek("VALTAS MASODIK VAGANYRA", irany);
    }

    private void valtasHarmadikVaganyra(boolean forditottMenetirany) {
        String irany = logikaitSzovegre(forditottMenetirany);
        uzenetVezerlonek("VALTAS HARMADIK VAGANYRA", irany);
    }

    private String logikaitSzovegre(boolean ertek) {
         return ertek ? "1" : "0";
    }

    public boolean vanVonatVaganyon(Vagany vagany, boolean forditottIrany) {
        ArrayList<Sin> sin = vagany.getSin();
        for (Vonat v : vonat) {
            if (v.isIrany(forditottIrany))
                for (Sin s : sin) {
                    boolean van = (v.getSin() == s);
                    if (van) return true;
                }
        }
        return false;
    }

    private Vagany getSzabadVagany() {
        if (vaganySzabad(vaganyMasodik)) return vaganyMasodik;
        if (vaganySzabad(vaganyElso)) return vaganyElso;
        if (vaganySzabad(vaganyHarmadik)) return vaganyHarmadik;
        return null;
    }

    private boolean vaganySzabad(Vagany vagany) {
        ArrayList<Sin> sin = vagany.getSin();
        for (Vonat v : vonat) {
            for (Sin s : sin) {
                boolean nem = (v.getSin() == s);
                if (nem) return false;
            }
        }
        return true;
    }

}