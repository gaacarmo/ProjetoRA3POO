package projeto_marcel;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaCadastro {
    public void cadastroUsuario() {
        Stage stage = new Stage(); // cria a janela
        stage.setTitle("Cadastro do Usuário");

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

        // botao de cadastro
        Button cadastrar = new Button("Cadastrar");
        cadastrar.setOnAction(e -> {
            Usuario novoUsuario = new Usuario(
                    //acresenta automaticamente o id
                    RepUsuarios.usuarios.size() + 1,
                    nome.getText(),
                    nomeUsuario.getText(),
                    curso.getText(),
                    email.getText(),
                    senha.getText()
            );
            RepUsuarios.usuarios.add(novoUsuario);
            PersistenciaUtils.salvarUsuarioDat();

            System.out.println("Cadastro feito com Sucesso!");
            //muda para o outra tela
            TelaExibirUsuarios telaExibir = new TelaExibirUsuarios();
            telaExibir.exibirUsuarios();
            stage.close();

        });

        // layout
        VBox layout = new VBox(10); // espaçamento entre elementos
        layout.setAlignment(Pos.CENTER);
        //ta adicionando os elementos dentro da vbox
        layout.getChildren().addAll(
                clienteLab,
                nomeLab, nome,
                nomeUsuarioLab, nomeUsuario,
                cursoLab, curso,
                emailLab, email,
                senhaLab, senha,
                cadastrar
        );

        // cena
        Scene scene = new Scene(layout, 400, 500);
        stage.setScene(scene);
        stage.show();







































































    }
}
