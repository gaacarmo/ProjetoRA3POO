package projeto_igor;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

// CLasse para testar se as alterações feitas na interface estão funcionando
public class Teste {
    public static void main(String[] args) {
        List<Postagem> postagens = Persistencia.carregarPostagens();
        for (Postagem p : postagens) { // imprime todas as postagens salvas
            System.out.println("Título: " + p.getTitulo());
            System.out.println("Selo de verificado: " + p.isVerificado());
            System.out.println("Postagem ativa: : " + p.getDescricao());
            System.out.println("Postagem ativa: : " + p.isPostagemAtiva());
            System.out.println("Autor: " + p.getUsuario().getNomeUsuario());
            System.out.println("------");
        }

        List<Usuario> usuarios = Persistencia.carregarUsuarios();
        for (Usuario u : usuarios) { // Imprime todas os usuários salvos
            System.out.println("Nome do usuário: " + u.getNomeUsuario());
            System.out.println("Usuário ativo: " + u.isUsuarioAtivo());
            System.out.println("------");
        }
    }
}

