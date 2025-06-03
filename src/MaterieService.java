import java.sql.*;

public class MaterieService {
    private static MaterieService instance;

    private MaterieService() {}

    public static MaterieService getInstance() {
        if (instance == null) {
            instance = new MaterieService();
        }
        return instance;
    }

    public void createMaterie(Materie m) throws SQLException {
        Connection con = DbConnection.getConnection();
        String sql = "INSERT INTO materie (denumire, an, semestru, profesor_id) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, m.getDenumire());
        ps.setInt(2, m.getAn());
        ps.setInt(3, m.getSemestru());
        if (m.getProfesor() != null) ps.setInt(4, m.getProfesor().getId());
        else ps.setNull(4, java.sql.Types.INTEGER);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            m.setId(rs.getInt(1));
        }
        rs.close();
        ps.close();
        AuditService.getInstance().logAction("createMaterie");
    }

    public Materie getMaterieById(int id) throws SQLException {
        Connection con = DbConnection.getConnection();
        String sql = "SELECT denumire, an, semestru, profesor_id FROM materie WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Materie m = null;
        if (rs.next()) {
            m = new Materie(rs.getString("denumire"), rs.getInt("an"), rs.getInt("semestru"));
            m.setId(id);
            int profId = rs.getInt("profesor_id");
            if (!rs.wasNull()) {
                Profesor prof = ProfesorService.getInstance().getProfesorById(profId);
                m.setProfesor(prof);
            }
        }
        rs.close();
        ps.close();
        AuditService.getInstance().logAction("getMaterieById");
        return m;
    }

    public void updateMaterie(Materie m) throws SQLException {
        Connection con = DbConnection.getConnection();
        String sql = "UPDATE materie SET denumire = ?, an = ?, semestru = ?, profesor_id = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, m.getDenumire());
        ps.setInt(2, m.getAn());
        ps.setInt(3, m.getSemestru());
        if (m.getProfesor() != null) ps.setInt(4, m.getProfesor().getId());
        else ps.setNull(4, java.sql.Types.INTEGER);
        ps.setInt(5, m.getId());
        ps.executeUpdate();
        ps.close();
        AuditService.getInstance().logAction("updateMaterie");
    }

    public void deleteMaterie(int id) throws SQLException {
        Connection con = DbConnection.getConnection();
        String sql = "DELETE FROM materie WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        AuditService.getInstance().logAction("deleteMaterie");
    }
}