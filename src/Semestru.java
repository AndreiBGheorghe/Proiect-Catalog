import java.util.*;

public class Semestru {
    private int numar;
    private SortedSet<Materie> materii = new TreeSet();

    public Semestru(int numar) {
        this.numar = numar;
    }

    public int getNumar() {
        return this.numar;
    }

    public void adaugaMaterie(Materie m) {
        this.materii.add(m);
    }

    public SortedSet<Materie> getMaterii() {
        return this.materii;
    }
}