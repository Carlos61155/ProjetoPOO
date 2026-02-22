import java.sql.*;
import java.util.ArrayList;

public class DAOCliente {
    public static void insertDataClient(Connection conn, Cliente c) throws SQLException {
        String sql = "INSERT INTO cliente (nome_cliente, telefone, cpf, sexo, id_mensalidade) values (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, c.getNome());
        ps.setString(2, c.getTelefone());
        ps.setString(3, c.getCpf());
        ps.setString(4, c.getSexo());
        ps.setInt(5, c.getMensalidade().getId());

        int resposta = ps.executeUpdate();
        if(resposta == 1){
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int lastId = rs.getInt(1); // Obtém o último ID inserido
                c.setId(lastId);
            }
            System.out.println("Dados inseridos com sucesso");
        }else{
            System.out.println("Problemas ao inserir os dados");
        }
    }

    public static ArrayList<Cliente> SelectDataClient(Connection conn){
        ArrayList<Cliente> listUser = new ArrayList<>();

        String sql = """
        SELECT c.*, m.id_mensalidade
        FROM cliente c
        LEFT JOIN mensalidade m
            ON c.id_mensalidade = m.id_mensalidade
    """;

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Cliente c = new Cliente();
                c.setId(rs.getInt("id_cliente"));
                c.setNome((rs.getString("nome_cliente")));
                c.setCpf(rs.getString("cpf"));
                c.setSexo(rs.getString("sexo"));
                c.setTelefone(rs.getString("telefone"));

                int idMensalidade = rs.getInt("id_mensalidade");
                if (!rs.wasNull()) {
                    Mensalidade m = new Mensalidade();
                    m.setId(idMensalidade);
                    c.setMensalidade(m);
                }

                listUser.add(c);
            }
            return listUser;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateDataClient(Connection conn, Cliente c) throws SQLException {
        String sql = "UPDATE cliente set nome_cliente = ?, cpf = ?, telefone = ?, sexo = ? where id_cliente = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, c.getNome());
        ps.setString(2, c.getCpf());
        ps.setString(3, c.getTelefone());
        ps.setString(4, c.getSexo());
        ps.setInt(5, c.getId());
        int resposta = ps.executeUpdate();
        if(resposta == 1){
            System.out.println("Dados atualizados com sucesso");
        }else{
            System.out.println("Problemas ao atualizar os dados");
        }
    }

    public static void deleteDataClient(Connection conn, int id) throws SQLException{
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Dados excluidos com sucesso!");
        }
    }

}
