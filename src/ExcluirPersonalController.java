import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;

public class ExcluirPersonalController {

    @FXML private TextField txtId;
    @FXML private Button btnVoltar;

    @FXML
    private void excluir() {

        try (Connection conn = Conexao.getConexaoMySql()) {

            int id = Integer.parseInt(txtId.getText());

            DAOPersonal.deleteDataPersonal(conn, id);

            mostrarAlerta("Sucesso", "Personal excluído com sucesso!");
            txtId.clear();

        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Informe um ID válido.");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Falha ao excluir personal.");
        }
    }

    @FXML
    private void voltar() throws Exception {

        Parent root = FXMLLoader.load(
                getClass().getResource("/MainView.fxml")
        );

        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}