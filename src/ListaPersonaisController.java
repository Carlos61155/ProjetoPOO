import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;

public class ListaPersonaisController {

    @FXML private TableView<Personal> tabelaPersonal;
    @FXML private TableColumn<Personal, Integer> colId;
    @FXML private TableColumn<Personal, String> colNome;
    @FXML private TableColumn<Personal, String> colCpf;
    @FXML private TableColumn<Personal, String> colTelefone;
    @FXML private TableColumn<Personal, String> colEspecialidade;

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEspecialidade.setCellValueFactory(new PropertyValueFactory<>("especialidade"));

        carregar();
    }

    private void carregar() {
        try (Connection conn = Conexao.getConexaoMySql()) {

            ObservableList<Personal> lista =
                    FXCollections.observableArrayList(
                            DAOPersonal.selectDataPersonal(conn)
                    );

            tabelaPersonal.setItems(lista);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editarPersonal() {

        try {

            Personal selecionado =
                    tabelaPersonal.getSelectionModel().getSelectedItem();

            if (selecionado == null) {
                mostrarAlerta("Erro", "Selecione um personal.");
                return;
            }

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/EditarPersonalView.fxml"));

            Parent root = loader.load();

            EditarPersonalController controller =
                    loader.getController();

            controller.setPersonal(selecionado);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            carregar(); // atualiza tabela

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void voltar() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/MainView.fxml"));
        Stage stage = (Stage) tabelaPersonal.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}