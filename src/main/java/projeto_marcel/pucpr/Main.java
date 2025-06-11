package projeto_marcel.pucpr;


import javafx.application.Application;
import javafx.stage.Stage;
import projeto_marcel.pucpr.Controller.TelaController;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        TelaController controller = new TelaController();
        controller.abrirTelaCadastro();
    }

    public static void main(String[] args) {
        launch(args);
    }
}