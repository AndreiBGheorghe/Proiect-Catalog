import java.util.*;

public class Nota {
    private int id;
    private double valoare;
    private Date data;
    private Materie materie;
    private Student student;

    public Nota(double valoare, Date data, Materie materie , Student student) {
        this.valoare = valoare;
        this.data = data;
        this.materie = materie;
        this.student = student;
    }

    public double getValoare() {
        return this.valoare;
    }

    public void setValoare(double valoare) {
        this.valoare = valoare;
    }

    public Materie getMaterie() {
        return this.materie;
    }

    public Date getData() {
        return this.data;
    }

    public Student getStudent() {
        return this.student;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}