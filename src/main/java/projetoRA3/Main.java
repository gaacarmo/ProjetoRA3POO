package projetoRA3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import projetoRA3.view.*;

public class Main extends Application {

    private BorderPane root;
    private UsuarioUI usuarioUI = new UsuarioUI();
    private ComentarioUI comentarioUI = new ComentarioUI();
    private PostagemUI postagemUI = new PostagemUI();
    private ModeradorUI moderadorUI = new ModeradorUI();

    @Override
    public void start(Stage stage) {
        root = new BorderPane();

        VBox menuLateral = new VBox(10);
        menuLateral.setPadding(new Insets(10));
        menuLateral.setPrefWidth(150);

        Button btnUsuario = new Button("Usuários");
        Button btnComentario = new Button("Comentários");
        Button btnPostagem = new Button("Postagens");
        Button btnModerador = new Button("Moderadores");

        btnUsuario.setMaxWidth(Double.MAX_VALUE);
        btnComentario.setMaxWidth(Double.MAX_VALUE);
        btnPostagem.setMaxWidth(Double.MAX_VALUE);
        btnModerador.setMaxWidth(Double.MAX_VALUE);

        btnUsuario.setOnAction(e -> root.setCenter(usuarioUI.getLayout()));
        btnComentario.setOnAction(e -> root.setCenter(comentarioUI.getLayout()));
        btnPostagem.setOnAction(e -> root.setCenter(postagemUI.getLayout()));
        btnModerador.setOnAction(e -> root.setCenter(moderadorUI.getLayout()));

        menuLateral.getChildren().addAll(btnUsuario, btnComentario, btnPostagem, btnModerador);

        root.setLeft(menuLateral);
        root.setCenter(usuarioUI.getLayout());  // view inicial

        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Projeto RA3 - CRUD");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
