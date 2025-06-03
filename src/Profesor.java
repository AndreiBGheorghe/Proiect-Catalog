import java.util.*;

public class Profesor extends Persoana {
    private int id;
    private String departament;
    private Set<Materie> materii = new HashSet();

    public Profesor(String nume, String prenume, String departament) {
        super(nume, prenume);
        this.departament = departament;
    }

    public void adaugaMaterie(Materie m) {
        this.materii.add(m);
    }

    public Set<Materie> getMaterii() {
        return this.materii;
    }

    public String getDepartament() {
        return this.departament;
    }

    public String getNumeComplet() {
        return super.getNumeComplet();
    }

    public String getNume() {
        return this.nume;
    }

    public String getPrenume() {
        return this.prenume;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}