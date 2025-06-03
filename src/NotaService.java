import java.sql.*;

public class NotaService {
    private static NotaService instance;

    private NotaService() {}

    public static NotaService getInstance() {
        if (instance == null) {
            instance = new NotaService();
        }
        return instance;
    }

    public void createNota(Nota n) throws SQLException {
        Connection con = DbConnection.getConnection();
        String sql = "INSERT INTO nota (valoare, data, student_id, materie_id) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, n.getValoare());
        ps.setDate(2, new java.sql.Date(n.getData().getTime()));
        ps.setInt(3, n.getStudent().getId());
        ps.setInt(4, n.getMaterie().getId());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            n.setId(rs.getInt(1));
        }
        rs.close();
        ps.close();
        AuditService.getInstance().logAction("createNota");
    }

    public Nota getNotaById(int id) throws SQLException {
        Connection con = DbConnection.getConnection();
        String sql = "SELECT valoare, data, student_id, materie_id FROM nota WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Nota n = null;
        if (rs.next()) {
            double val = rs.getDouble("valoare");
            java.util.Date data = rs.getDate("data");
            int studId = rs.getInt("student_id");
            int matId = rs.getInt("materie_id");
            Student s = StudentService.getInstance().getStudentById(studId);
            Materie m = MaterieService.getInstance().getMaterieById(matId);
            n = new Nota(val, data, m, s);
            n.setId(id);
        }
        rs.close();
        ps.close();
        AuditService.getInstance().logAction("getNotaById");
        return n;
    }

    public void updateNota(Nota n) throws SQLException {
        Connection con = DbConnection.getConnection();
        String sql = "UPDATE nota SET valoare = ?, data = ?, student_id = ?, materie_id = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDouble(1, n.getValoare());
        ps.setDate(2, new java.sql.Date(n.getData().getTime()));
        ps.setInt(3, n.getStudent().getId());
        ps.setInt(4, n.getMaterie().getId());
        ps.setInt(5, n.getId());
        ps.executeUpdate();
        ps.close();
        AuditService.getInstance().logAction("updateNota");
    }

    public void deleteNota(int id) throws SQLException {
        Connection con = DbConnection.getConnection();
        String sql = "DELETE FROM nota WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        AuditService.getInstance().logAction("deleteNota");
    }
}