import java.util.*;

public class Facultate {
    private String denumire;
    private List<Grupa> grupe = new ArrayList();
    private List<Profesor> profesori = new ArrayList();
    private List<ProgramStudii> programe = new ArrayList();

    public Facultate(String denumire) {
        this.denumire = denumire;
    }

    public void adaugaGrupa(Grupa g) {
        this.grupe.add(g);
    }

    public void adaugaProfesor(Profesor p) {
        this.profesori.add(p);
    }

    public void adaugaProgramStudii(ProgramStudii p) {
        this.programe.add(p);
    }

    public List<Grupa> getGrupe() {
        return this.grupe;
    }

    public List<Profesor> getProfesori() {
        return this.profesori;
    }

    public List<ProgramStudii> getPrograme() {
        return this.programe;
    }
}