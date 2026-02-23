import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.util.Calendar;

public class EditarMensalidadeController {

    private Mensalidade mensalidadeEdicao;

    @FXML private ComboBox<String> comboStatus;
    @FXML private ComboBox<Integer> comboDias;
    @FXML private ComboBox<String> comboModalidade;
    @FXML private TextField txtValor;

    // ===== RECEBE A MENSALIDADE SELECIONADA =====
    public void setMensalidade(Mensalidade m) {
        this.mensalidadeEdicao = m;

        comboStatus.setValue(m.getStatus());
        comboModalidade.setValue(m.getModalidade());
        txtValor.setText(String.valueOf(m.getValor()));
    }

    // ===== INICIALIZA COMBOBOX =====
    @FXML
    public void initialize() {

        comboStatus.getItems().addAll("Ativo", "Inativo");
        comboDias.getItems().addAll(3, 4, 5);
        comboModalidade.getItems().addAll("Natação", "Judô", "JiuJitsu", "Musculação");


        comboDias.setOnAction(e -> recalcularValor());
    }


    private double calcularValor(int dias) {
        switch (dias) {
            case 3: return 80.0;
            case 4: return 100.0;
            case 5: return 120.0;
            default: return 0.0;
        }
    }

    private void recalcularValor() {
        if (comboDias.getValue() != null) {
            double valor = calcularValor(comboDias.getValue());
            txtValor.setText(String.valueOf(valor));
        }
    }

    @FXML
    private void salvar() {

        try (Connection conn = Conexao.getConexaoMySql()) {

            mensalidadeEdicao.setStatus(comboStatus.getValue());
            mensalidadeEdicao.setModalidade(comboModalidade.getValue());
            mensalidadeEdicao.setValor(Double.parseDouble(txtValor.getText()));

            if ("Ativo".equals(comboStatus.getValue())) {

                int novoMes = Calendar.getInstance().get(Calendar.MONTH) + 1;
                mensalidadeEdicao.setMes(novoMes);

                Calendar novaData = Calendar.getInstance();
                novaData.add(Calendar.MONTH, 1);
                mensalidadeEdicao.setData_pg(novaData);
            }

            DAOMensalidade.updateDataMensalidade(conn, mensalidadeEdicao);

            mostrarAlerta("Sucesso", "Mensalidade atualizada!");
            fecharJanela();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Falha ao atualizar.");
        }
    }

    // ===== UTIL =====
    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void fecharJanela() {
        comboStatus.getScene().getWindow().hide();
    }
}