public class Persoana {
    protected String nume;
    protected String prenume;

    public Persoana(String nume, String prenume) {
        this.nume = nume;
        this.prenume = prenume;
    }

    public String getNumeComplet() {
        return this.nume + " " + this.prenume;
    }
}