import java.sql.*;
import java.util.ArrayList;

public class DAOPersonal {

    public static void insertDataPersonal(Connection conn, Personal p) throws SQLException {
        String sql = "INSERT INTO personal (nome_personal, cpf, sexo, telefone, especialidade) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, p.getNome());
        ps.setString(2, p.getCpf());
        ps.setString(3, p.getSexo());
        ps.setString(4, p.getTelefone());
        ps.setString(5, p.getEspecialidade());

        int resposta = ps.executeUpdate();

        if (resposta == 1) {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int lastId = rs.getInt(1);
                p.setId(lastId);
            }
            System.out.println("Personal inserido com sucesso");
        } else {
            System.out.println("Problemas ao inserir o personal");
        }
    }

    public static ArrayList<Personal> selectDataPersonal(Connection conn) {
        ArrayList<Personal> listaPersonal = new ArrayList<>();

        String sql = "SELECT * FROM personal";

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Personal p = new Personal();

                p.setId(rs.getInt("id_personal"));
                p.setNome(rs.getString("nome_personal"));
                p.setCpf(rs.getString("cpf"));
                p.setSexo(rs.getString("sexo"));
                p.setTelefone(rs.getString("telefone"));
                p.setEspecialidade(rs.getString("especialidade"));

                listaPersonal.add(p);
            }

            return listaPersonal;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateDataPersonal(Connection conn, Personal p) throws SQLException {
        String sql = "UPDATE personal SET nome_personal = ?, cpf = ?, sexo = ?, telefone = ?, especialidade = ? WHERE id_personal = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, p.getNome());
        ps.setString(2, p.getCpf());
        ps.setString(3, p.getSexo());
        ps.setString(4, p.getTelefone());
        ps.setString(5, p.getEspecialidade());
        ps.setInt(6, p.getId());

        int resposta = ps.executeUpdate();

        if (resposta == 1) {
            System.out.println("Personal atualizado com sucesso");
        } else {
            System.out.println("Problemas ao atualizar o personal");
        }
    }

    public static void deleteDataPersonal(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM personal WHERE id_personal = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Personal excluído com sucesso!");
        }
    }
}