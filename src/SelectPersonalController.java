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

public class SelectPersonalController {

    @FXML private TableView<Personal> tabelaPersonal;

    @FXML
    private TableColumn<Personal, Integer> colId;
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
            ObservableList<Personal> lista = FXCollections.observableArrayList(DAOPersonal.selectDataPersonal(conn));

            tabelaPersonal.setItems(lista);
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