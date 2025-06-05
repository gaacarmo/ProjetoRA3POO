package RA3.projeto_igor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox; // import de todos pacotes de estilo
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.List;

public class Plataforma extends Application {
    // Declarei como variáveis de instância para acesso em todos os métodos
    private Moderador mod1;
    private Usuario usuario1;
    private Usuario usuario2;
    private Postagem postagem1;
    private Postagem postagem2;
    private BorderPane root;
    private List<Postagem> todasAsPostagens;
    private List<Usuario> todosUsuarios;

    @Override
    public void start(Stage primaryStage) {
        // Estilo CSS para toda a aplicação
        String estiloCSS = """
            .root {
                -fx-background-color: #f5f5f5;
                -fx-font-family: 'Segoe UI', Arial, sans-serif;
            }
            .label-titulo {
                -fx-font-size: 18px;
                -fx-font-weight: bold;
                -fx-text-fill: #333333;
                -fx-padding: 0 0 10 0;
            }
            .botao-primario {
                -fx-background-color: #4a6baf;
                -fx-text-fill: white;
                -fx-font-weight: bold;
                -fx-padding: 8 16;
                -fx-background-radius: 4;
            }
            .botao-primario:hover {
                -fx-background-color: #3a5a9f;
            }
            .botao-secundario {
                -fx-background-color: #6c757d;
                -fx-text-fill: white;
                -fx-padding: 8 16;
                -fx-background-radius: 4;
            }
            .botao-secundario:hover {
                -fx-background-color: #5c656d;
            }
            .botao-atencao {
                -fx-background-color: #dc3545;
                -fx-text-fill: white;
                -fx-padding: 8 16;
                -fx-background-radius: 4;
            }
            .botao-atencao:hover {
                -fx-background-color: #c82333;
            }
            .card {
                -fx-background-color: white;
                -fx-border-color: #dddddd;
                -fx-border-radius: 4;
                -fx-background-radius: 4;
                -fx-padding: 15;
                -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);
            }
            .card-titulo {
                -fx-font-weight: bold;
                -fx-text-fill: #444444;
            }
        """;

        todasAsPostagens = Persistencia.carregarPostagens();// Carrega todas postagens salvas
        todosUsuarios = Persistencia.carregarUsuarios(); // carrega todos usuários salvos

        // Inicialização do moderador
        mod1 = new Moderador(1, "Igor", "123");

        // Cria usuários artificialmente caso não possua nenhum
        usuario1 = new Usuario("Jorge Souza");
        usuario2 = new Usuario("Henrique Sampaio");

        // cria postagens artificias caso não tenha nada salvo
        postagem1 = new Postagem("Perdi minha caneta BIC",
                "Perdi ela no bloco verde e não consigo encontrar, a caneta é muito importante para mim",
                usuario1);
        postagem2 = new Postagem("Perdi algo",
                "Perdi ela no bloco azul",
                usuario2);

        root = new BorderPane();
        root.setStyle("-fx-padding: 20;");

        // Configuração inicial
        root.setCenter(createPaginaInicial());
        Scene scene = new Scene(root, 800, 600); // Tamanho um pouco maior
        scene.getStylesheets().add("data:text/css;charset=utf-8," + estiloCSS);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Painel do Moderador");

        // Impede que a janela seja minimizada
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private VBox createPaginaInicial() { // página inicial do CRUD de moderador, pensei não ser necessário login para completar o CRUD
        Label titulo = new Label("Página inicial");
        titulo.getStyleClass().add("label-titulo");

        Button acessarPostagens = new Button("Postagens não verificadas");
        Button listaDeUsuarios = new Button("Lista de usuários");
        Button acessarEstatisticas = new Button("Acessar estatísticas");

        // Aplica estilos aos botões
        acessarPostagens.getStyleClass().add("botao-primario");
        listaDeUsuarios.getStyleClass().add("botao-primario");
        acessarEstatisticas.getStyleClass().add("botao-primario");

        acessarPostagens.setOnAction(e -> root.setCenter(createVerificarPostagem()));
        listaDeUsuarios.setOnAction(e -> root.setCenter(createListaUsuarios()));
        acessarEstatisticas.setOnAction(e -> root.setCenter(createVisualizarEstatisticas()));

        VBox vbox = new VBox(15, titulo, acessarPostagens, listaDeUsuarios, acessarEstatisticas);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20));
        return vbox;
    }

    private VBox createVerificarPostagem() { // cria página de verificar postagem
        VBox vboxPrincipal = new VBox(15);
        vboxPrincipal.setPadding(new Insets(20));

        Label titulo = new Label("Postagens não verificadas:");
        titulo.getStyleClass().add("label-titulo");
        vboxPrincipal.getChildren().add(titulo);

        // Verifica se há postagens não verificadas e adiciona artificialmente se não tiver
        if (todasAsPostagens.isEmpty()) {
            todasAsPostagens.add(postagem1);
            todasAsPostagens.add(postagem2);
            Persistencia.salvarPostagens(todasAsPostagens);
        } else {
            // Percorre sobre cada postagem não verificada
            for (Postagem postagem : todasAsPostagens) {
                if(postagem.isPostagemAtiva() && !postagem.isVerificado()){
                    // Cria um painel para cada postagem
                    VBox postagemBox = new VBox(10);
                    postagemBox.getStyleClass().add("card");

                    // Exibe os detalhes da postagem
                    Label tituloLabel = new Label("Título: " + postagem.getTitulo());
                    tituloLabel.getStyleClass().add("card-titulo");
                    Label descricaoLabel = new Label("Descrição: " + postagem.getDescricao());
                    Label autorLabel = new Label("Autor: " + postagem.getUsuario().getNomeUsuario());

                    // Cria botões para a postagem atual
                    Button btnAdicionarVerificado = new Button("Aprovar Postagem");
                    Button btnExcluirPostagem = new Button("Excluir Postagem");

                    // Aplica estilos aos botões
                    btnAdicionarVerificado.getStyleClass().add("botao-secundario");
                    btnExcluirPostagem.getStyleClass().add("botao-atencao");

                    // Configura ações dos botões
                    btnAdicionarVerificado.setOnAction(e -> {
                        try {
                            mod1.adicionarVerificado(postagem); // Marca como verificada
                            Persistencia.salvarPostagens(todasAsPostagens);
                            root.setCenter(createVerificarPostagem()); // Atualiza a tela
                        } catch (Exception ex) {
                            System.out.println("Erro ao adicionar verificado: " + ex.getMessage());
                        }
                    });

                    btnExcluirPostagem.setOnAction(e -> {
                        try {
                            mod1.excluirPostagem(postagem); // Remove a postagem
                            Persistencia.salvarPostagens(todasAsPostagens);
                            root.setCenter(createVerificarPostagem()); // Atualiza a tela
                        } catch (Exception ex) {
                            System.out.println("Erro ao excluir postagem: " + ex.getMessage());
                        }
                    });

                    // Adiciona os elementos ao painel da postagem
                    HBox botoesBox = new HBox(10, btnAdicionarVerificado, btnExcluirPostagem);
                    botoesBox.setAlignment(Pos.CENTER_RIGHT);
                    postagemBox.getChildren().addAll(tituloLabel, descricaoLabel, autorLabel, botoesBox);

                    // Adiciona o painel da postagem ao VBox principal
                    vboxPrincipal.getChildren().add(postagemBox);
                }
            }
        }

        Button reativarPostagens = new Button("Reativar postagens excluídas");
        Button retirarVerificado = new Button("Retirar verificado");
        reativarPostagens.getStyleClass().add("botao-secundario");
        retirarVerificado.getStyleClass().add("botao-secundario");

        reativarPostagens.setOnAction(e -> root.setCenter(createPostagensExcluidas()));
        retirarVerificado.setOnAction(e -> root.setCenter(createPostagensVerificadas()));
        // Botão para voltar à página inicial
        Button voltar = new Button("Voltar para página inicial");
        voltar.getStyleClass().add("botao-secundario");
        voltar.setOnAction(e -> root.setCenter(createPaginaInicial()));

        HBox botoesAdicionais = new HBox(10, reativarPostagens, retirarVerificado);

        VBox container = new VBox(15, vboxPrincipal, botoesAdicionais, voltar);
        container.setPadding(new Insets(20));
        return container;
    }

    private VBox createListaUsuarios() { // cria página para visualizar a lista de usuários da plataforma
        VBox vboxPrincipal = new VBox(15);
        vboxPrincipal.setPadding(new Insets(20));

        Label titulo = new Label("Lista de usuários");
        titulo.getStyleClass().add("label-titulo");
        vboxPrincipal.getChildren().add(titulo);

        if (todosUsuarios.isEmpty()) { //verifica existência de dados e adiciona artificialmente se não tiver
            todosUsuarios.add(usuario1);
            todosUsuarios.add(usuario2);
            Persistencia.salvarUsuario(todosUsuarios);
        } else {
            for (Usuario usuario : todosUsuarios) { // percoree a lista carregada de usuários
                if (usuario.isUsuarioAtivo()) {
                    VBox usuariosBox = new VBox(10);
                    usuariosBox.getStyleClass().add("card");

                    // dados do usuário
                    Label nomeLabel = new Label("Nome do usuário: " + usuario.getNomeUsuario());
                    nomeLabel.getStyleClass().add("card-titulo");
                    Label ativoLabel = new Label("Status: " + (usuario.isUsuarioAtivo() ? "Ativo" : "Inativo"));

                    Button btnExcluirUsuario = new Button("Desativar usuário"); // botão para fazer o soft delete
                    btnExcluirUsuario.getStyleClass().add("botao-atencao");

                    btnExcluirUsuario.setOnAction(e -> {
                        try {
                            mod1.excluirUsuario(usuario); // marca como inativo
                            Persistencia.salvarUsuario(todosUsuarios);
                            root.setCenter(createListaUsuarios()); // atualiza a tela
                        } catch (Exception ex) {
                            System.out.println("Erro ao inativar usuário: " + ex.getMessage());
                        }
                    });

                    usuariosBox.getChildren().addAll(nomeLabel, ativoLabel, btnExcluirUsuario);
                    vboxPrincipal.getChildren().add(usuariosBox);
                }
            }
        }

        Button reativarUsuario = new Button("Reativar usuários");
        reativarUsuario.getStyleClass().add("botao-secundario");
        reativarUsuario.setOnAction(e-> root.setCenter(createUsuariosInativos()));

        Button voltar = new Button("Voltar para página inicial");
        voltar.getStyleClass().add("botao-secundario");
        voltar.setOnAction(e -> root.setCenter(createPaginaInicial()));

        VBox container = new VBox(15, vboxPrincipal, reativarUsuario, voltar);
        container.setPadding(new Insets(20));
        return container;
    }

    private VBox createVisualizarEstatisticas() { //página para visualizar algumas estatísitcas da plataforma textualmente, porque não sabia fazer gráficos em java
        // configuração inicial
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));

        Label titulo = new Label("Estatísticas da Plataforma");
        titulo.getStyleClass().add("label-titulo");

        // lê informações das postagens e usuários carregados anteriormente
        int totalPostagens = todasAsPostagens.size();
        long postagensAtivas = todasAsPostagens.stream().filter(Postagem::isPostagemAtiva).count(); // filter para aplicar filtros e selcionar dados específicos
        long postagensVerificadas = todasAsPostagens.stream().filter(Postagem::isVerificado).count();
        long postagensExcluidas = totalPostagens - postagensAtivas;

        int totalUsuarios = todosUsuarios.size();
        long usuariosAtivos = todosUsuarios.stream().filter(Usuario::isUsuarioAtivo).count();
        long usuariosInativos = totalUsuarios - usuariosAtivos;

        Label lblTotalPostagens = new Label("Total de postagens: " + totalPostagens);
        Label lblPostagensAtivas = new Label("Postagens ativas: " + postagensAtivas);
        Label lblPostagensVerificadas = new Label("Postagens verificadas: " + postagensVerificadas);
        Label lblPostagensExcluidas = new Label("Postagens excluídas: " + postagensExcluidas);

        Label lblTotalUsuarios = new Label("Total de usuários: " + totalUsuarios);
        Label lblUsuariosAtivos = new Label("Usuários ativos: " + usuariosAtivos);
        Label lblUsuariosInativos = new Label("Usuários inativos: " + usuariosInativos);

        Button voltar = new Button("Voltar para a página inicial");
        voltar.getStyleClass().add("botao-secundario");
        voltar.setOnAction(e -> root.setCenter(createPaginaInicial()));

        vbox.getChildren().addAll(titulo, lblTotalPostagens, lblPostagensAtivas, lblPostagensVerificadas,
                lblPostagensExcluidas, lblTotalUsuarios, lblUsuariosAtivos, lblUsuariosInativos, voltar);

        return vbox;
    }

    private VBox createUsuariosInativos(){
        VBox vboxPrincipal = new VBox(15);
        vboxPrincipal.setPadding(new Insets(20));

        Label titulo = new Label("Lista de usu;arios inativos");
        titulo.getStyleClass().add("label-titulo");
        vboxPrincipal.getChildren().add(titulo);

        for(Usuario usuario : todosUsuarios){
            if(!usuario.isUsuarioAtivo()){
                VBox usuarioBox = new VBox(10);
                usuarioBox.getStyleClass().add("card");

                Label nomeLabel = new Label("Nome do usuário: " + usuario.getNomeUsuario());
                nomeLabel.getStyleClass().add("card-titulo");
                Label ativoLabel = new Label("Status: " + (usuario.isUsuarioAtivo() ? "Ativo" : "Inativo"));

                Button btnReativarUsuario = new Button("Reativar usuário");
                btnReativarUsuario.getStyleClass().add("botao-secundario");

                btnReativarUsuario.setOnAction(e ->{
                    try{
                        mod1.reativarUsuario(usuario);
                        Persistencia.salvarUsuario(todosUsuarios);
                        todosUsuarios = Persistencia.carregarUsuarios(); // Recarrega os dados
                        root.setCenter(createUsuariosInativos());
                    } catch (Exception ex){
                        System.out.println("Erro ao tentar reativar usuário: " + ex.getMessage());
                    };
                });
                usuarioBox.getChildren().addAll(nomeLabel, ativoLabel, btnReativarUsuario);
                vboxPrincipal.getChildren().add(usuarioBox);
            }
        }

        Button voltar = new Button("Voltar");
        voltar.getStyleClass().add("botao-secundario");

        vboxPrincipal.getChildren().add(voltar);
        voltar.setOnAction(e -> root.setCenter(createVerificarPostagem()));

        return vboxPrincipal;
    }

    private VBox createPostagensVerificadas(){
        VBox vboxPrincipal = new VBox();
        vboxPrincipal.setPadding(new Insets(20));

        Label titulo = new Label("Página de postagens já verificadas");
        titulo.getStyleClass().add("label-titulo");
        vboxPrincipal.getChildren().add(titulo);

        for(Postagem postagem : todasAsPostagens){
            if(postagem.isVerificado()) {
                VBox postagemBox = new VBox(10);
                postagemBox.getStyleClass().add("card");

                Label tituloLabel = new Label("Título: " + postagem.getTitulo());
                tituloLabel.getStyleClass().add("card-titulo");
                Label descricaoLabel = new Label("Descricao: " + postagem.getDescricao());
                Label autorLabel = new Label("Autor: " + postagem.getUsuario().getNomeUsuario());

                Button btnRetirarVerificado = new Button("Retirar verficado");
                btnRetirarVerificado.getStyleClass().add("botao-secundario");

                btnRetirarVerificado.setOnAction(e -> {
                    try {
                        mod1.retirarVerificado(postagem);
                        Persistencia.salvarPostagens(todasAsPostagens);
                        todasAsPostagens = Persistencia.carregarPostagens(); // Recarrega os dados
                        root.setCenter(createPostagensVerificadas());
                    } catch (Exception ex) {
                        System.out.println("Erro ao retirar selo de verificado: " + ex.getMessage());
                    }
                });

                postagemBox.getChildren().addAll(tituloLabel, descricaoLabel, autorLabel, btnRetirarVerificado);
                vboxPrincipal.getChildren().add(postagemBox);
            }
        }

        Button voltar = new Button("Voltar");
        voltar.getStyleClass().add("botao-secundario");

        vboxPrincipal.getChildren().add(voltar);
        voltar.setOnAction(e -> root.setCenter(createVerificarPostagem()));

        return vboxPrincipal;
    }

    private VBox createPostagensExcluidas(){
        VBox vboxPrincipal = new VBox();
        vboxPrincipal.setPadding(new Insets(20));

        Label titulo = new Label("Postagens excluídas");
        titulo.getStyleClass().add("label-titulo");
        vboxPrincipal.getChildren().add(titulo);

        for(Postagem postagem : todasAsPostagens){
            if(!postagem.isPostagemAtiva()){
                VBox postagemBox = new VBox(10);
                postagemBox.getStyleClass().add("card");

                Label tituloLabel = new Label("Título: " + postagem.getTitulo());
                tituloLabel.getStyleClass().add("card-titulo");
                Label descricaoLabel = new Label("Descricao: " + postagem.getDescricao());
                Label autorLabel = new Label("Autor: " + postagem.getUsuario().getNomeUsuario());

                Button btnReativarPostagem = new Button("Reativar postagem");
                btnReativarPostagem.getStyleClass().add("botao-secundario");

                btnReativarPostagem.setOnAction(e -> {
                    try{
                        mod1.reativarPostagem(postagem);
                        Persistencia.salvarPostagens(todasAsPostagens);
                        todasAsPostagens = Persistencia.carregarPostagens(); // Recarrega os dados
                        root.setCenter(createPostagensExcluidas());
                    } catch(Exception ex){
                        System.out.println("Erro ao tentar reativar postagem: " + ex.getMessage());
                    }

                });

                postagemBox.getChildren().addAll(descricaoLabel, autorLabel, btnReativarPostagem);
                vboxPrincipal.getChildren().add(postagemBox);
            }
        }

        Button voltar = new Button("Voltar");
        voltar.getStyleClass().add("botao-secundario");

        vboxPrincipal.getChildren().add(voltar);
        voltar.setOnAction(e -> root.setCenter(createVerificarPostagem()));

        return vboxPrincipal;
    }

    public static void main(String[] args) {
        launch(args);
    }
}