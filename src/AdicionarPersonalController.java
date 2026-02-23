import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;

public class AdicionarPersonalController {

    @FXML
    private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtTelefone;

    @FXML private ComboBox<String> comboSexo;
    @FXML private ComboBox<String> comboEspecialidade;

    @FXML
    public void initialize() {

        comboSexo.getItems().addAll("Masculino", "Feminino");

        comboEspecialidade.getItems().addAll("Musculação", "Judô", "Natação", "Jiu Jitsu");
    }

    @FXML
    private void salvar() {

        try (Connection conn = Conexao.getConexaoMySql()) {

            Personal p = new Personal(txtNome.getText(), txtCpf.getText(), comboSexo.getValue(), txtTelefone.getText(), comboEspecialidade.getValue());

            DAOPersonal.insertDataPersonal(conn, p);

            mostrarAlerta("Sucesso", "Personal cadastrado!");
            voltar();

        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao cadastrar.");
        }
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    private void voltar() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/MainView.fxml"));
        Stage stage = (Stage) txtNome.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}