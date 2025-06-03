public class Materie implements Comparable<Materie> {
    private int id;
    private String denumire;
    private int an;
    private int semestru;
    private Profesor profesor;

    public Materie(String denumire, int an, int semestru) {
        this.denumire = denumire;
        this.an = an;
        this.semestru = semestru;
    }

    public void setProfesor(Profesor p) {
        this.profesor = p;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public String getDenumire() {
        return this.denumire;
    }

    public int getAn() {
        return this.an;
    }

    public int getSemestru() {
        return this.semestru;
    }

    public int compareTo(Materie o) {
        return this.denumire.compareTo(o.denumire);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}