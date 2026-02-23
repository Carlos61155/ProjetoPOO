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

    private Cliente clienteEdicao = null;

    public void setCliente(Cliente c) {
        this.clienteEdicao = c;

        txtNome.setText(c.getNome());
        txtCpf.setText(c.getCpf());
        txtTelefone.setText(c.getTelefone());
        comboSexo.setValue(c.getSexo());

        if (c.getMensalidade() != null) {
            comboModalidade.setValue(c.getMensalidade().getModalidade());
        }
    }

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
        comboSexo.getItems().addAll(
                "Masculino",
                "Feminino"
        );

        diasTreino.getItems().addAll(
                3,
                4,
                5
        );

        comboModalidade.getItems().addAll(
                "Natação",
                "Judô",
                "JiuJitsu",
                "Musculação"
        );
    }

    @FXML
    private void salvar() {
        try (Connection conn = Conexao.getConexaoMySql()) {

            int dias = diasTreino.getValue();
            double valor = calcularValorMensalidade(dias);

            // ===== CADASTRO =====
            if (clienteEdicao == null) {

                Mensalidade m = new Mensalidade(valor, comboModalidade.getValue());
                DAOMensalidade.insertDataMensalidade(conn, m);

                Cliente c = new Cliente(
                        txtNome.getText(),
                        txtCpf.getText(),
                        comboSexo.getValue(),
                        txtTelefone.getText(),
                        m
                );

                DAOCliente.insertDataClient(conn, c);
            }

            // ===== EDIÇÃO =====
            else {
                clienteEdicao.setNome(txtNome.getText());
                clienteEdicao.setCpf(txtCpf.getText());
                clienteEdicao.setSexo(comboSexo.getValue());
                clienteEdicao.setTelefone(txtTelefone.getText());

                DAOCliente.updateDataClient(conn, clienteEdicao);
            }

            mostrarAlerta("Sucesso", "Operação realizada!");
            voltar();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Falha ao salvar.");
        }
    }

    @FXML
    private void cancelar() {
        fecharJanela();
    }



    // ===== Utilidades =====
    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void fecharJanela() {
        txtNome.getScene().getWindow().hide();
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
