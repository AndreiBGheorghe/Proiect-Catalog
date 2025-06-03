import java.util.*;

public class Grupa {
    private String nume;
    private int an;
    private List<Student> studenti = new ArrayList();

    public Grupa(String nume, int an) {
        this.nume = nume;
        this.an = an;
    }

    public String getNume() {
        return this.nume;
    }

    public void adaugaStudent(Student s) {
        this.studenti.add(s);
    }

    public void stergeStudent(Student s) {
        this.studenti.remove(s);
    }

    public List<Student> getStudenti() {
        return this.studenti;
    }
}