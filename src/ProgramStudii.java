import java.util.*;

public class ProgramStudii {
    private String tip;
    private int durata;
    private Map<Integer, Semestru> semestre = new HashMap();

    public ProgramStudii(String tip, int durata) {
        this.tip = tip;
        this.durata = durata;
    }

    public void adaugaMaterieInSemestru(int nr, Materie m) {
        this.semestre.putIfAbsent(nr, new Semestru(nr));
        ((Semestru)this.semestre.get(nr)).adaugaMaterie(m);
    }

    public Collection<Semestru> getSemestre() {
        return this.semestre.values();
    }
}