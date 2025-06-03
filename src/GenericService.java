import java.sql.*;

public class GenericService<T, ID> {
    private static GenericService instance;
    private Connection connection;

    private GenericService() throws SQLException {
        connection = DbConnection.getConnection();
    }

    public static synchronized <T, ID> GenericService<T, ID> getInstance() throws SQLException {
        if (instance == null) {
            instance = new GenericService<>();
        }
        return instance;
    }

    public void create(String sql, SqlSetter setter) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setter.set(ps);
            ps.executeUpdate();
        }
    }

    public <R> R read(String sql, SqlSetter setter, ResultSetExtractor<R> extractor) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setter.set(ps);
            try (ResultSet rs = ps.executeQuery()) {
                return extractor.extract(rs);
            }
        }
    }

    public void update(String sql, SqlSetter setter) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setter.set(ps);
            ps.executeUpdate();
        }
    }

    public void delete(String sql, SqlSetter setter) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            setter.set(ps);
            ps.executeUpdate();
        }
    }

    @FunctionalInterface
    public interface SqlSetter {
        void set(PreparedStatement ps) throws SQLException;
    }

    @FunctionalInterface
    public interface ResultSetExtractor<R> {
        R extract(ResultSet rs) throws SQLException;
    }
}