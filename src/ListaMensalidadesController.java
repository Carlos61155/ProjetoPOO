import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;

public class ListaMensalidadesController {

    @FXML
    private TableView<Mensalidade> tabelaMensalidades;

    @FXML private TableColumn<Mensalidade, Integer> colId;
    @FXML private TableColumn<Mensalidade, Integer> colMes;
    @FXML private TableColumn<Mensalidade, String> colStatus;
    @FXML private TableColumn<Mensalidade, Double> colValor;
    @FXML private TableColumn<Mensalidade, String> colModalidade;
    @FXML private TableColumn<Mensalidade, String> colDataPg;

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMes.setCellValueFactory(new PropertyValueFactory<>("mes"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colModalidade.setCellValueFactory(new PropertyValueFactory<>("modalidade"));

        // Converter Calendar → String para mostrar na tabela
        colDataPg.setCellValueFactory(cellData -> {
            if (cellData.getValue().getData_pg() != null) {
                return new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getData_pg().getTime().toString()
                );
            }
            return null;
        });

        carregarMensalidades();
    }

    private void carregarMensalidades() {
        try (Connection conn = Conexao.getConexaoMySql()) {

            ObservableList<Mensalidade> lista =
                    FXCollections.observableArrayList(
                            DAOMensalidade.SelectDataMensalidade(conn)
                    );

            tabelaMensalidades.setItems(lista);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== VOLTAR =====
    @FXML private Button btnVoltar;

    @FXML
    private void voltar() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/MainView.fxml"));
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void editarMensalidade() {

        try {
            Mensalidade selecionada =
                    tabelaMensalidades.getSelectionModel().getSelectedItem();

            if (selecionada == null) {
                mostrarAlerta("Erro", "Selecione uma mensalidade.");
                return;
            }

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/EditarMensalidadeView.fxml"));

            Parent root = loader.load();

            EditarMensalidadeController controller = loader.getController();
            controller.setMensalidade(selecionada);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            carregarMensalidades();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String msg) {
        javafx.scene.control.Alert alert =
                new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}
