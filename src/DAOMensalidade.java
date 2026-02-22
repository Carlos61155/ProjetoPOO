import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class DAOMensalidade {
    public static void insertDataMensalidade(Connection conn, Mensalidade m) throws SQLException {
        String sql = "INSERT INTO mensalidade (mes, status_mensalidade, valor, data_pg, modalidade) values (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        Calendar cal = m.getData_pg();
        ps.setInt(1, m.getMes());
        ps.setString(2, m.getStatus());
        ps.setDouble(3, m.getValor());
        ps.setTimestamp(4, new Timestamp(cal.getTimeInMillis()));
        ps.setString(5, m.getModalidade());



        int resposta = ps.executeUpdate();
        if(resposta == 1){
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int lastId = rs.getInt(1); // Obtém o último ID inserido
                m.setId(lastId);
            }
            System.out.println("Dados inseridos com sucesso");
        }else{
            System.out.println("Problemas ao inserir os dados");
        }
    }

    public static ArrayList<Mensalidade> SelectDataMensalidade(Connection conn){
        ArrayList<Mensalidade> listUser = new ArrayList<>();
        String sql = "SELECT * FROM mensalidade";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Mensalidade m = new Mensalidade();
                m.setId(rs.getInt("id_mensalidade"));
                m.setMes((rs.getInt("mes")));
                m.setStatus(rs.getString("status_mensalidade"));
                m.setValor(rs.getDouble("valor"));
                Timestamp ts = rs.getTimestamp("data_pg");
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(ts.getTime());
                m.setData_pg(cal);
                m.setModalidade(rs.getString("modalidade"));
                listUser.add(m);
            }
            return listUser;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateDataMensalidade(Connection conn, Mensalidade m) throws SQLException {
        String sql = "UPDATE mensalidade set mes = ?, status_mensalidade = ?, valor = ?,data_pg = ?, modalidade = ? where id_mensalidade = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, m.getMes());
        ps.setString(2, m.getStatus());
        ps.setDouble(3, m.getValor());
        ps.setTimestamp(4, new Timestamp(m.getData_pg().getTimeInMillis()));
        ps.setString(5, m.getModalidade());
        ps.setInt(6, m.getId());

        int resposta = ps.executeUpdate();
        if(resposta == 1){
            System.out.println("Dados atualizados com sucesso");
        }else{
            System.out.println("Problemas ao atualizar os dados");
        }
    }

    public static void deleteDataMensalidade(Connection conn, int id) throws SQLException{
        String sql = "DELETE FROM mensalidade WHERE id_mensalidade = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Dados excluidos com sucesso!");
        }
    }
}
