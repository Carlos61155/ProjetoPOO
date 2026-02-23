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

public class SelectMensalidadesController {

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

        //Converte calendar para String
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

            ObservableList<Mensalidade> lista = FXCollections.observableArrayList(DAOMensalidade.SelectDataMensalidade(conn));

            tabelaMensalidades.setItems(lista);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private Button btnVoltar;

    @FXML
    private void voltar() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/MainView.fxml"));
        Stage stage = (Stage) btnVoltar.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}