enum Valami {
    a, b, c
}

public class Enumtest {

    public Enumtest() {
        switch (Valami.a) {
            case a: System.out.println(""); break;
        }
        Valami.valueOf("a");
    }

    public static void main(String[] args) {
        new Enumtest();
    }
}