import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;

public class EditarClienteController {

    private Cliente clienteEdicao;

    public void setCliente(Cliente c) {
        this.clienteEdicao = c;

        txtNome.setText(c.getNome());
        txtCpf.setText(c.getCpf());
        txtTelefone.setText(c.getTelefone());
        comboSexo.setValue(c.getSexo());
    }

    @FXML private TextField txtNome;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtCpf;
    @FXML private ComboBox<String> comboSexo;

    @FXML
    public void initialize() {
        comboSexo.getItems().addAll("Masculino", "Feminino");
    }



    @FXML
    private void salvar() {
        try (Connection conn = Conexao.getConexaoMySql()) {

            if (clienteEdicao == null) {
                mostrarAlerta("Erro", "Cliente não carregado para edição.");
                return;
            }

            clienteEdicao.setNome(txtNome.getText());
            clienteEdicao.setCpf(txtCpf.getText());
            clienteEdicao.setSexo(comboSexo.getValue());
            clienteEdicao.setTelefone(txtTelefone.getText());

            DAOCliente.updateDataClient(conn, clienteEdicao);

            mostrarAlerta("Sucesso", "Cliente atualizado!");
            fecharJanela();

        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao atualizar.");
        }
    }

    @FXML
    private void cancelar() {
        fecharJanela();
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void fecharJanela() {
        txtNome.getScene().getWindow().hide();
    }
}