package projeto_marcel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class TelaExibirUsuarios {


    public void exibirUsuarios() {
        Stage stage = new Stage(); //janela
        stage.setTitle("Lista de Usuários");

        // titulo da pagina e status
        Label tituloLab = new Label("Lista de Usuários Cadastrados");
        tituloLab.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label statusLab = new Label("Carregando usuários...");
        statusLab.setStyle("-fx-text-fill: blue;");

        // cria a tabela
        TableView<Usuario> tabelaUsuarios = new TableView<>();

        //cria as colunas

        TableColumn<Usuario, Integer> colunaId = new TableColumn<>("ID"); // é necessario dedfinir o tipo de dado da coluna
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaId.setPrefWidth(50);

        TableColumn<Usuario, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaNome.setPrefWidth(150);

        TableColumn<Usuario, String> colunaNomeUsuario = new TableColumn<>("Usuário");
        colunaNomeUsuario.setCellValueFactory(new PropertyValueFactory<>("nomeUsuario"));
        colunaNomeUsuario.setPrefWidth(120);

        TableColumn<Usuario, String> colunaCurso = new TableColumn<>("Curso");
        colunaCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        colunaCurso.setPrefWidth(130);

        TableColumn<Usuario, String> colunaEmail = new TableColumn<>("Email");
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaEmail.setPrefWidth(180);

        TableColumn<Usuario, String> colunaSenha = new TableColumn<>("Senha");
        // Exibe senha com asteriscos
        colunaSenha.setCellValueFactory(cellData -> {
            //ele pega a senha original
            String senha = cellData.getValue().getSenha();
            // e aqui ele troca todos os carcteres por *
            return new javafx.beans.property.SimpleStringProperty("*".repeat(senha.length()));
        });
        colunaSenha.setPrefWidth(70);

        //adicionando as colunas na tabela
        tabelaUsuarios.getColumns().addAll(colunaId, colunaNome, colunaNomeUsuario,
                colunaCurso, colunaEmail, colunaSenha);

        // botao que carrega os usuários do arquivo
        Button btnCarregar = new Button("Carregar Dados");
        btnCarregar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        btnCarregar.setOnAction(e -> {
            try {
                List<Usuario> usuarios = carregarUsuariosDat(); // le o arquivo
                //consegue atualizar em tempo real
                //serve para manter os dados sincronizados
                ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList(usuarios);
                tabelaUsuarios.setItems(listaUsuarios);

                if (usuarios.isEmpty()) {
                    statusLab.setText("Nenhum usuário encontrado no arquivo.");
                    statusLab.setStyle("-fx-text-fill: orange;");
                } else {
                    statusLab.setText("Carregados " + usuarios.size() + " usuários com sucesso!");
                    statusLab.setStyle("-fx-text-fill: green;");
                }
            } catch (Exception ex) {
                statusLab.setText("Erro ao carregar dados: " + ex.getMessage());
                statusLab.setStyle("-fx-text-fill: red;");
            }
        });


        //excluir um usuario
        Button btnExcluir = new Button("Excluir");
        btnExcluir.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        btnExcluir.setOnAction(e -> {
            Usuario usuarioSelecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();

            if (usuarioSelecionado != null) {
                // Confirmação antes de excluir
                Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                confirmacao.setTitle("Confirmação");
                confirmacao.setHeaderText("Excluir Usuário");
                confirmacao.setContentText("Tem certeza que deseja excluir o usuário " + usuarioSelecionado.getNome() + "?");

                confirmacao.showAndWait().ifPresent(resposta -> {
                    if (resposta == ButtonType.OK) {
                        tabelaUsuarios.getItems().remove(usuarioSelecionado);
                        salvarUsuariosDat(tabelaUsuarios.getItems()); // salva alteraçoes
                        statusLab.setText("Usuário excluído com sucesso.");
                        statusLab.setStyle("-fx-text-fill: green;");
                    }
                });
            } else {
                // alerta se nenhum usuario estiver selecionado
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Atenção");
                alerta.setHeaderText("Nenhum usuário selecionado");
                alerta.setContentText("Selecione um usuário para excluir.");
                alerta.showAndWait();
            }
        });

        // editar um usuario
        Button btnEditar = new Button("Editar");
        btnEditar.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold;");

        btnEditar.setOnAction(e -> {
            Usuario usuarioSelecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();
            if (usuarioSelecionado != null) {
                mostrarJanelaEditar(usuarioSelecionado, tabelaUsuarios, statusLab); // abre janela de ediçao
            } else {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Atenção");
                alerta.setHeaderText("Nenhum usuário selecionado");
                alerta.setContentText("Selecione um usuário para editar.");
                alerta.showAndWait();
            }
        });
        /*Button btnNovo = new Button("Novo Usuário");
        TelaCadastro telaCadastro = new TelaCadastro();

        btnNovo.setOnAction(e ->{
            telaCadastro.cadastroUsuario();
        });*/
        // fechar a janela
        Button btnFechar = new Button("Fechar");
        btnFechar.setOnAction(e -> stage.close());

        // layout principal
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
                tituloLab,
                statusLab,
                tabelaUsuarios,
                btnCarregar,
                btnEditar,
                btnExcluir,
                //btnNovo,
                btnFechar
        );

        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
        stage.show();

        // carrega os dados automaticamente ao abrir
        btnCarregar.fire();
    }

    // abre uma nova janela para editar os dados do usuario
    private void mostrarJanelaEditar(Usuario usuario, TableView<Usuario> tabela, Label statusLab) {
        Stage janela = new Stage();
        janela.setTitle("Editar Usuário");
        //precisa obrigatoriamente interagir
        janela.initModality(Modality.APPLICATION_MODAL);

        //organiza em linhas e colunas
        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);

        grid.setAlignment(Pos.CENTER);

        // Campos de edição com valores atuais do usuário
        TextField txtNome = new TextField(usuario.getNome());
        TextField txtUsuario = new TextField(usuario.getNomeUsuario());
        TextField txtCurso = new TextField(usuario.getCurso());
        TextField txtEmail = new TextField(usuario.getEmail());
        TextField txtSenha = new TextField();
        txtSenha.setText(usuario.getSenha());

        // adiciona os campos ao layout
        grid.add(new Label("Nome:"), 0, 0);
        grid.add(txtNome, 1, 0);
        grid.add(new Label("Usuário:"), 0, 1);
        grid.add(txtUsuario, 1, 1);
        grid.add(new Label("Curso:"), 0, 2);
        grid.add(txtCurso, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(txtEmail, 1, 3);
        grid.add(new Label("Senha:"), 0, 4);
        grid.add(txtSenha, 1, 4);

        // botao para salvar alterações feitas acima
        Button btnSalvar = new Button("Salvar");
        btnSalvar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnSalvar.setOnAction(ev -> {
            // atualiza os dados do objeto
            usuario.setNome(txtNome.getText());
            usuario.setNomeUsuario(txtUsuario.getText());
            usuario.setCurso(txtCurso.getText());
            usuario.setEmail(txtEmail.getText());
            usuario.setSenha(txtSenha.getText());

            tabela.refresh(); // atualiza tabela
            salvarUsuariosDat(tabela.getItems()); // salva alteraçoes
            statusLab.setText("Usuário atualizado com sucesso.");
            statusLab.setStyle("-fx-text-fill: green;");
            janela.close();
        });

        VBox vbox = new VBox(10, grid, btnSalvar);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox, 400, 300);
        janela.setScene(scene);
        janela.showAndWait();
    }

    // le os dados dos usuarios do arquivo usuarios.dat
    private List<Usuario> carregarUsuariosDat() {
        List<Usuario> usuarios = new ArrayList<>();
        //vai ler o arquivo linha por linha
        try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.dat"))) {
            //pra cada linha ele vai colocar essa barra
            reader.lines().forEach(linha -> {
                String[] dados = linha.split("\\|"); //divide os dados com "|"

                if (dados.length == 6) {
                    try {
                        Usuario usuario = new Usuario(
                                Integer.parseInt(dados[0].trim()), //id
                                dados[1].trim(),//nome
                                dados[2].trim(),//nome de usuario
                                dados[3].trim(),//curso
                                dados[4].trim(),//email
                                dados[5].trim()//senha
                        );
                        usuarios.add(usuario);
                    } catch (NumberFormatException e) {
                        System.out.println("Erro ao converter id na linha: " + linha);
                    }
                } else {
                    System.out.println("Linha inválida: " + linha);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo usuarios.dat: " + e.getMessage());
        }

        return usuarios;
    }


    // salva os dados dos usuários no arquivo usuarios.dat
    private void salvarUsuariosDat(List<Usuario> usuarios) {
        try (PrintWriter gravador = new PrintWriter("usuarios.dat")) {
            for (Usuario u : usuarios) {
                String linha = String.format("%d|%s|%s|%s|%s|%s",
                        u.getId(), u.getNome(), u.getNomeUsuario(),
                        u.getCurso(), u.getEmail(), u.getSenha());
                gravador.println(linha);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar os dados: " + e.getMessage());
        }
    }
}
