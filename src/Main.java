import java.util.Date;

public class Main {
    public static void main(String[] args) {
        try {
            Student student = StudentService.getInstance().getStudentById(1);
            Materie materie = MaterieService.getInstance().getMaterieById(1);
            Nota nota = new Nota(8.5, new Date(), materie, student);
            NotaService.getInstance().createNota(nota);
            System.out.println("Nota inserata cu id " + nota.getId());
            Nota n = NotaService.getInstance().getNotaById(nota.getId());
            System.out.println("Nota citita - " + n.getValoare() + ", data - " + n.getData());
            n.setValoare(9.0);
            NotaService.getInstance().updateNota(n);
            System.out.println("Nota actualizata");
            NotaService.getInstance().deleteNota(n.getId());
            System.out.println("Nota stearsa");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}