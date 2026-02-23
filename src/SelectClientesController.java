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

public class SelectClientesController {

    @FXML
    private TableView<Cliente> tabelaClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNome;
    @FXML private TableColumn<Cliente, String> colTelefone;
    @FXML private TableColumn<Cliente, String> colCpf;
    @FXML private TableColumn<Cliente, Integer> colMensalidade;

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colMensalidade.setCellValueFactory(
                new PropertyValueFactory<>("mensalidadeId")
        );

        carregarClientes();
    }

    private void carregarClientes() {
        try (Connection conn = Conexao.getConexaoMySql()) {

            ObservableList<Cliente> lista = FXCollections.observableArrayList(DAOCliente.SelectDataClient(conn));

            tabelaClientes.setItems(lista);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
