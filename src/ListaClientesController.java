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

public class ListaClientesController {


    @FXML
    private TableView<Cliente> tabelaClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNome;
    @FXML private TableColumn<Cliente, String> colTelefone;
    @FXML private TableColumn<Cliente, String> colCpf;

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));


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
    private void editarCliente() {
        try {
            Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();

            if (selecionado == null) {
                mostrarAlerta("Erro", "Selecione um cliente.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditarClienteView.fxml"));//prepara para carregar a tela

            Parent root = loader.load();//o controller é criado

            EditarClienteController controller = loader.getController();// pega o controller que foi criado
            controller.setCliente(selecionado);//passa o cliente que deve ser editado para a tela

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            carregarClientes();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String msg) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
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
