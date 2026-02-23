import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;

public class EditarPersonalController {

    private Personal personalEdicao;

    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtTelefone;
    @FXML private ComboBox<String> comboSexo;
    @FXML private ComboBox<String> comboEspecialidade;

    public void setPersonal(Personal p) {
        this.personalEdicao = p;

        txtNome.setText(p.getNome());
        txtCpf.setText(p.getCpf());
        txtTelefone.setText(p.getTelefone());
        comboSexo.setValue(p.getSexo());
        comboEspecialidade.setValue(p.getEspecialidade());
    }

    @FXML
    public void initialize() {
        comboSexo.getItems().addAll("Masculino", "Feminino");
        comboEspecialidade.getItems().addAll(
                "Musculação",
                "Judô",
                "Natação",
                "Jiu Jitsu"
        );
    }

    @FXML
    private void salvar() {

        try (Connection conn = Conexao.getConexaoMySql()) {

            personalEdicao.setNome(txtNome.getText());
            personalEdicao.setCpf(txtCpf.getText());
            personalEdicao.setSexo(comboSexo.getValue());
            personalEdicao.setTelefone(txtTelefone.getText());
            personalEdicao.setEspecialidade(comboEspecialidade.getValue());

            DAOPersonal.updateDataPersonal(conn, personalEdicao);

            mostrarAlerta("Sucesso", "Personal atualizado!");
            fecharJanela();

        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao atualizar.");
        }
    }

    private void fecharJanela() {
        txtNome.getScene().getWindow().hide();
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}