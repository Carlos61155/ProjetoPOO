import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane contentArea;

    public void editarClientes() throws IOException {
        carregarTela("/ListaClientesView.fxml");
    }

    public void adicionarClientes() throws IOException {
        carregarTela("/AdicionarClienteView.fxml");
    }

    public void excluirClientes() throws IOException {
        carregarTela("/ExcluirClienteView.fxml");
    }

    public void listarClientes() throws IOException {
        carregarTela("/SelectClientesView.fxml");
    }

    public void editarMensalidades() throws IOException {
        carregarTela("/ListaMensalidadesView.fxml");
    }

    public void listarMensalidades() throws IOException {
        carregarTela("/SelectMensalidadesView.fxml");
    }

    public void adicionarPersonais() throws IOException {
        carregarTela("/AdicionarPersonalView.fxml");
    }

    public void listarPersonais() throws IOException {
        carregarTela("/SelectPersonalView.fxml");
    }

    public void excluirPersonais() throws IOException {
        carregarTela("/ExcluirPersonalView.fxml");
    }

    public void editarPersonais() throws IOException {
        carregarTela("/ListaPersonaisView.fxml");
    }

    private void carregarTela(String caminho) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource(caminho));
        contentArea.getChildren().setAll(pane);
    }
}