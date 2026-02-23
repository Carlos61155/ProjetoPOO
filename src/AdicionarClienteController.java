import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;

public class AdicionarClienteController {

    private double calcularValorMensalidade(int dias) {
        switch (dias) {
            case 3: return 80.0;
            case 4: return 100.0;
            case 5: return 120.0;
            default: return 0.0;
        }
    }

    @FXML private TextField txtNome;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtCpf;

    @FXML
    private ComboBox<Integer> diasTreino;

    @FXML
    private ComboBox<String> comboSexo;

    @FXML
    private ComboBox<String> comboModalidade;

    @FXML
    public void initialize() {
        comboSexo.getItems().addAll("Masculino", "Feminino");

        diasTreino.getItems().addAll(3, 4, 5);

        comboModalidade.getItems().addAll("Natação", "Judô", "JiuJitsu", "Musculação");
    }

    @FXML
    private void salvar() {
        try (Connection conn = Conexao.getConexaoMySql()) {

            int dias = diasTreino.getValue();
            double valor = calcularValorMensalidade(dias);

            Mensalidade m = new Mensalidade(valor, comboModalidade.getValue());
            DAOMensalidade.insertDataMensalidade(conn, m);

            Cliente c = new Cliente(txtNome.getText(), txtCpf.getText(), comboSexo.getValue(), txtTelefone.getText(), m);

            DAOCliente.insertDataClient(conn, c);

            mostrarAlerta("Sucesso", "Cliente cadastrado!");
            voltar();

        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao salvar.");
        }
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    private Button btnVoltar;

    @FXML
    private void voltar() throws Exception {

        Parent root = FXMLLoader.load(
                getClass().getResource("/MainView.fxml")
        );

        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}
