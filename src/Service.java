import java.io.PrintStream;
import java.util.*;

public class Service {
    private List<Grupa> grupe = new ArrayList();

    public Service() {
    }

    public void adaugaGrupa(Grupa g) {
        this.grupe.add(g);
    }

    public void adaugaStudentInGrupa(Grupa g, Student s) {
        g.adaugaStudent(s);
    }

    private Student cautaStudentInGrupa(Grupa g, String cnpStudent) {
        return (Student)g.getStudenti().stream().filter((s) -> s.getCnp().equals(cnpStudent)).findFirst().orElse((Student)null);
    }

    public List<Student> studentiRestanti() {
        List<Student> restanti = new ArrayList();
        for(Grupa g : this.grupe) {
            for(Student s : g.getStudenti()) {
                if (s.areRestante()) {
                    restanti.add(s);
                }
            }
        }
        return restanti;
    }

    public void afiseazaStudentiSortatiDupaMedie(Grupa g) {
        List<Student> studenti = g.getStudenti();
        studenti.stream()
                .sorted((s1, s2) -> Double.compare(
                        calculareMedieStudent(s2.getCnp()),
                        calculareMedieStudent(s1.getCnp())))
                .forEach(s ->
                        System.out.println(s.getNumeComplet() + ": " + calculareMedieStudent(s.getCnp())));
    }

    public double calculareMedieStudent(String cnpStudent) {
        for(Grupa g : this.grupe) {
            for(Student s : g.getStudenti()) {
                if (s.getCnp().equals(cnpStudent)) {
                    double suma = (double)0.0F;
                    for(Nota n : s.getNote()) {
                        suma += n.getValoare();
                    }
                    return suma / (double)s.getNote().size();
                }
            }
        }
        return (double)0.0F;
    }

    public void transferaStudent(Grupa grupaVeche, Grupa grupaNoua, String cnpStudent) {
        Student studentDeTransferat = this.cautaStudentInGrupa(grupaVeche, cnpStudent);
        if (studentDeTransferat != null) {
            grupaVeche.stergeStudent(studentDeTransferat);
            grupaNoua.adaugaStudent(studentDeTransferat);
            studentDeTransferat.setGrupa(grupaNoua);
        }
    }

    public void stergeStudentDinGrupa(Grupa g, String cnpStudent) {
        Student studentDeSters = this.cautaStudentInGrupa(g, cnpStudent);
        if (studentDeSters != null) {
            g.stergeStudent(studentDeSters);
        }
    }

    public void afiseazaMateriiProfesor(Profesor profesor) {
        for(Materie m : profesor.getMaterii()) {
            System.out.println(m.getDenumire());
        }
    }

    public List<Student> studentiFaraNota() {
        List<Student> faraNota = new ArrayList();
        for(Grupa g : this.grupe) {
            for(Student s : g.getStudenti()) {
                if (s.getNote().isEmpty()) {
                    faraNota.add(s);
                }
            }
        }
        return faraNota;
    }

    public List<Student> studentiRestantiLaMaterie(Materie materie) {
        List<Student> restanti = new ArrayList();
        for(Grupa g : this.grupe) {
            for(Student s : g.getStudenti()) {
                if (s.getNote().stream().anyMatch((n) -> n.getMaterie().equals(materie) && n.getValoare() < (double)5.0F)) {
                    restanti.add(s);
                }
            }
        }
        return restanti;
    }

    public void afiseazaMedieGeneralaPeGrupa(Grupa g) {
        for (Student s : g.getStudenti()) {
            double medie = this.calculareMedieStudent(s.getCnp());
            System.out.println(s.getNumeComplet() + ": " + medie);
        }
    }

    public List<Student> studentiCuMediePestePrag(Grupa g, double prag) {
        List<Student> studentiFiltrati = new ArrayList();
        for(Student s : g.getStudenti()) {
            double medie = this.calculareMedieStudent(s.getCnp());
            if (medie > prag) {
                studentiFiltrati.add(s);
            }
        }
        return studentiFiltrati;
    }
}