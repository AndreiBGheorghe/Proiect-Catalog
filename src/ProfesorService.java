import java.sql.*;

public class ProfesorService {
    private static ProfesorService instance;

    private ProfesorService() {}

    public static ProfesorService getInstance() {
        if (instance == null) {
            instance = new ProfesorService();
        }
        return instance;
    }

    public void createProfesor(Profesor prof) throws SQLException {
        Connection con = DbConnection.getConnection();
        String sqlPers = "INSERT INTO persoana (nume, prenume) VALUES (?, ?)";
        PreparedStatement psPers = con.prepareStatement(sqlPers, Statement.RETURN_GENERATED_KEYS);
        psPers.setString(1, prof.getNume());
        psPers.setString(2, prof.getPrenume());
        psPers.executeUpdate();
        ResultSet rs = psPers.getGeneratedKeys();
        int id = -1;
        if (rs.next()) id = rs.getInt(1);
        prof.setId(id);
        psPers.close();
        String sqlProf = "INSERT INTO profesor (id, departament) VALUES (?, ?)";
        PreparedStatement psProf = con.prepareStatement(sqlProf);
        psProf.setInt(1, id);
        psProf.setString(2, prof.getDepartament());
        psProf.executeUpdate();
        psProf.close();
        AuditService.getInstance().logAction("createProfesor");
    }

    public Profesor getProfesorById(int id) throws SQLException {
        Connection con = DbConnection.getConnection();
        String sql = "SELECT p.nume, p.prenume, pr.departament FROM persoana p JOIN profesor pr ON p.id = pr.id WHERE p.id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Profesor prof = null;
        if (rs.next()) {
            prof = new Profesor(rs.getString("nume"), rs.getString("prenume"), rs.getString("departament"));
            prof.setId(id);
        }
        rs.close();
        ps.close();
        AuditService.getInstance().logAction("getProfesorById");
        return prof;
    }

    public void updateProfesor(Profesor prof) throws SQLException {
        Connection con = DbConnection.getConnection();
        String sqlPers = "UPDATE persoana SET nume = ?, prenume = ? WHERE id = ?";
        PreparedStatement psPers = con.prepareStatement(sqlPers);
        psPers.setString(1, prof.getNume());
        psPers.setString(2, prof.getPrenume());
        psPers.setInt(3, prof.getId());
        psPers.executeUpdate();
        psPers.close();
        String sqlProf = "UPDATE profesor SET departament = ? WHERE id = ?";
        PreparedStatement psProf = con.prepareStatement(sqlProf);
        psProf.setString(1, prof.getDepartament());
        psProf.setInt(2, prof.getId());
        psProf.executeUpdate();
        psProf.close();
        AuditService.getInstance().logAction("updateProfesor");
    }

    public void deleteProfesor(int id) throws SQLException {
        Connection con = DbConnection.getConnection();
        String sqlProf = "DELETE FROM profesor WHERE id = ?";
        PreparedStatement psProf = con.prepareStatement(sqlProf);
        psProf.setInt(1, id);
        psProf.executeUpdate();
        psProf.close();
        String sqlPers = "DELETE FROM persoana WHERE id = ?";
        PreparedStatement psPers = con.prepareStatement(sqlPers);
        psPers.setInt(1, id);
        psPers.executeUpdate();
        psPers.close();
        AuditService.getInstance().logAction("deleteProfesor");
    }
}