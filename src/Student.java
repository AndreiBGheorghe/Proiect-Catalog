import java.util.*;

public class Student extends Persoana {
    private int id;
    private String cnp;
    private Grupa grupa;
    private List<Nota> note = new ArrayList();

    public Student(String nume, String prenume, String cnp) {
        super(nume, prenume);
        this.cnp = cnp;
    }

    public void setGrupa(Grupa grupa) {
        this.grupa = grupa;
    }

    public void adaugaNota(Nota nota) {
        this.note.add(nota);
    }

    public List<Nota> getNote() {
        return this.note;
    }

    public String getCnp() {
        return this.cnp;
    }

    public boolean areRestante() {
        return this.note.stream().anyMatch((n) -> n.getValoare() < (double)5.0F);
    }

    public String getNume() {
        return this.nume;
    }

    public String getPrenume() {
        return this.prenume;
    }

    public Grupa getGrupa() {
        return this.grupa;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}