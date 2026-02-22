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

    private void carregarTela(String caminho) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource(caminho));
        contentArea.getChildren().setAll(pane);
    }
}