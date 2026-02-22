import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Conexao {
    public static java.sql.Connection getConexaoMySql(){
        Connection connection = null;
        String servidor = "localhost";
        String banco = "ProjetoAcademia";
        String url = "jdbc:mysql://"+servidor+"/"+banco;
        String usuario = "root";
        String senha = "carlos1307";
        try{
            connection = DriverManager.getConnection(url, usuario, senha);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }
}