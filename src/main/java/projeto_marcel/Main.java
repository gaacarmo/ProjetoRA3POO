package projeto_marcel;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        TelaCadastro tela = new TelaCadastro();
        tela.cadastroUsuario(); // abre a tela de cadastro


        //TelaExibirUsuarios telaa = new TelaExibirUsuarios();
        //telaa.exibirUsuarios();
    }

    public static void main(String[] args) {
        launch(args); // inicia o java fx
    }
}