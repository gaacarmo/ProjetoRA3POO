package projeto_marcel.pucpr.View;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import projeto_marcel.pucpr.Controller.TelaController;

public class TelaCadastroView {
    private TelaController controller;
    private Stage stage;


    public TelaCadastroView(TelaController controller) {
        this.controller = controller;
        this.stage = new Stage();
        stage.setTitle("Cadastro do Usuário");
    }

    public void mostrar() {
        Label clienteLab = new Label("Cadastro do Usuário");
        Label nomeLab = new Label("Digite seu nome: ");
        Label nomeUsuarioLab = new Label("Digite seu nome de usuário: ");
        Label cursoLab = new Label("Digite seu curso: ");
        Label emailLab = new Label("Digite seu email: ");
        Label senhaLab = new Label("Digite sua senha: ");

        TextField nome = new TextField();
        TextField nomeUsuario = new TextField();
        TextField curso = new TextField();
        TextField email = new TextField();
        PasswordField senha = new PasswordField();

        Button cadastrar = new Button("Cadastrar");
        cadastrar.setOnAction(e -> {
            controller.cadastrarUsuario(
                    nome.getText(),
                    nomeUsuario.getText(),
                    curso.getText(),
                    email.getText(),
                    senha.getText()
            );

            System.out.println("Cadastro feito com Sucesso!");
            controller.abrirTelaExibirUsuarios();
            stage.close();
        });

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
                clienteLab,
                nomeLab, nome,
                nomeUsuarioLab, nomeUsuario,
                cursoLab, curso,
                emailLab, email,
                senhaLab, senha,
                cadastrar
        );

        Scene scene = new Scene(layout, 400, 500);
        stage.setScene(scene);
        stage.show();
    }
}
