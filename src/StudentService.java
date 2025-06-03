import java.sql.*;
import java.util.*;

public class StudentService {
    private static StudentService instance;

    private StudentService() {}

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }

    public void createStudent(Student s) throws SQLException {
        Connection con = DbConnection.getConnection();
        String sqlPers = "INSERT INTO persoana (nume, prenume) VALUES (?, ?)";
        PreparedStatement psPers = con.prepareStatement(sqlPers, Statement.RETURN_GENERATED_KEYS);
        psPers.setString(1, s.getNume());
        psPers.setString(2, s.getPrenume());
        psPers.executeUpdate();
        ResultSet rs = psPers.getGeneratedKeys();
        int persoanaId = -1;
        if (rs.next()) persoanaId = rs.getInt(1);
        s.setId(persoanaId);
        psPers.close();
        String sqlStud = "INSERT INTO student (id, cnp, grupa) VALUES (?, ?, ?)";
        PreparedStatement psStud = con.prepareStatement(sqlStud);
        psStud.setInt(1, persoanaId);
        psStud.setString(2, s.getCnp());
        psStud.setString(3, s.getGrupa() != null ? s.getGrupa().getNume() : null);
        psStud.executeUpdate();
        psStud.close();
        AuditService.getInstance().logAction("createStudent");
    }

    public Student getStudentById(int id) throws SQLException {
        Connection con = DbConnection.getConnection();
        String sql = "SELECT p.nume, p.prenume, s.cnp, s.grupa FROM persoana p JOIN student s ON p.id = s.id WHERE p.id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Student s = null;
        if (rs.next()) {
            s = new Student(rs.getString("nume"), rs.getString("prenume"), rs.getString("cnp"));
            s.setId(id);
            String numeGrupa = rs.getString("grupa");
            if (numeGrupa != null) s.setGrupa(new Grupa(numeGrupa, 0));
        }
        rs.close();
        ps.close();
        AuditService.getInstance().logAction("getStudentById");
        return s;
    }

    public void updateStudent(Student s) throws SQLException {
        Connection con = DbConnection.getConnection();
        String sqlPers = "UPDATE persoana SET nume = ?, prenume = ? WHERE id = ?";
        PreparedStatement psPers = con.prepareStatement(sqlPers);
        psPers.setString(1, s.getNume());
        psPers.setString(2, s.getPrenume());
        psPers.setInt(3, s.getId());
        psPers.executeUpdate();
        psPers.close();
        String sqlStud = "UPDATE student SET cnp = ?, grupa = ? WHERE id = ?";
        PreparedStatement psStud = con.prepareStatement(sqlStud);
        psStud.setString(1, s.getCnp());
        psStud.setString(2, s.getGrupa() != null ? s.getGrupa().getNume() : null);
        psStud.setInt(3, s.getId());
        psStud.executeUpdate();
        psStud.close();
        AuditService.getInstance().logAction("updateStudent");
    }

    public void deleteStudent(int id) throws SQLException {
        Connection con = DbConnection.getConnection();
        String sqlStud = "DELETE FROM student WHERE id = ?";
        PreparedStatement psStud = con.prepareStatement(sqlStud);
        psStud.setInt(1, id);
        psStud.executeUpdate();
        psStud.close();
        String sqlPers = "DELETE FROM persoana WHERE id = ?";
        PreparedStatement psPers = con.prepareStatement(sqlPers);
        psPers.setInt(1, id);
        psPers.executeUpdate();
        psPers.close();
        AuditService.getInstance().logAction("deleteStudent");
    }
}