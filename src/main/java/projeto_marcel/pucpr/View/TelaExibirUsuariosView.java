package projeto_marcel.pucpr.View;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import projeto_marcel.pucpr.Controller.TelaController;
import projeto_marcel.pucpr.Model.PersistenciaUtils;
import projeto_marcel.pucpr.Model.RepUsuarios;
import projeto_marcel.pucpr.Model.Usuario;

import java.util.ArrayList;

public class TelaExibirUsuariosView {
    private Stage stage;
    private TableView<Usuario> tabelaUsuarios;
    private TelaController controller;

    public TelaExibirUsuariosView(TelaController controller) {
        this.controller = controller;
        this.stage = new Stage();
        stage.setTitle("Usuários Cadastrados");
        inicializarTabela();
    }

    private void inicializarTabela() {
        tabelaUsuarios = new TableView<>();

        // Configuração das colunas
        TableColumn<Usuario, Integer> colunaId = new TableColumn<>("ID");
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Usuario, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Usuario, String> colunaUsuario = new TableColumn<>("Nome de Usuário");
        colunaUsuario.setCellValueFactory(new PropertyValueFactory<>("nomeUsuario"));

        TableColumn<Usuario, String> colunaCurso = new TableColumn<>("Curso");
        colunaCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));

        TableColumn<Usuario, String> colunaEmail = new TableColumn<>("Email");
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Usuario, String> colunaSenha = new TableColumn<>("Senha");
        colunaSenha.setCellValueFactory(cellData -> {
            String senha = cellData.getValue().getSenha();
            return new SimpleStringProperty("*".repeat(senha.length()));
        });

        // Adiciona todas as colunas à tabela
        tabelaUsuarios.getColumns().addAll(
                colunaId,
                colunaNome,
                colunaUsuario,
                colunaCurso,
                colunaEmail,
                colunaSenha
        );

        // Define larguras preferenciais para as colunas
        colunaId.setPrefWidth(50);
        colunaNome.setPrefWidth(150);
        colunaUsuario.setPrefWidth(120);
        colunaCurso.setPrefWidth(150);
        colunaEmail.setPrefWidth(200);
        colunaSenha.setPrefWidth(100);
    }

    private void editarUsuarioSelecionado() {
        Usuario usuarioSelecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();

        if (usuarioSelecionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Selecione um usuário para editar!");
            alert.showAndWait();
            return;
        }

        // Cria um diálogo personalizado para edição
        Dialog<Usuario> dialog = new Dialog<>();
        dialog.setTitle("Editar Usuário");
        dialog.setHeaderText("Edite os dados do usuário");

        // Botões do diálogo
        ButtonType salvarButtonType = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(salvarButtonType, ButtonType.CANCEL);

        // Campos de entrada
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nomeField = new TextField(usuarioSelecionado.getNome());
        TextField nomeUsuarioField = new TextField(usuarioSelecionado.getNomeUsuario());
        TextField cursoField = new TextField(usuarioSelecionado.getCurso());
        TextField emailField = new TextField(usuarioSelecionado.getEmail());
        PasswordField senhaField = new PasswordField();
        senhaField.setText(usuarioSelecionado.getSenha());

        grid.add(new Label("Nome:"), 0, 0);
        grid.add(nomeField, 1, 0);
        grid.add(new Label("Nome de Usuário:"), 0, 1);
        grid.add(nomeUsuarioField, 1, 1);
        grid.add(new Label("Curso:"), 0, 2);
        grid.add(cursoField, 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(emailField, 1, 3);
        grid.add(new Label("Senha:"), 0, 4);
        grid.add(senhaField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        // Converter resultado quando o botão Salvar for clicado
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == salvarButtonType) {
                return usuarioSelecionado; // Retorna o usuário selecionado para indicar que foi salvo
            }
            return null;
        });

        // Processar resultado
        dialog.showAndWait().ifPresent(usuario -> {
            if (nomeField.getText().trim().isEmpty() ||
                    nomeUsuarioField.getText().trim().isEmpty() ||
                    cursoField.getText().trim().isEmpty() ||
                    emailField.getText().trim().isEmpty() ||
                    senhaField.getText().trim().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Todos os campos devem ser preenchidos!");
                alert.showAndWait();
                return;
            }

            boolean sucesso = controller.editarUsuario(
                    usuarioSelecionado.getId(),
                    nomeField.getText(),
                    nomeUsuarioField.getText(),
                    cursoField.getText(),
                    emailField.getText(),
                    senhaField.getText()
            );

            if (sucesso) {
                atualizarTabela();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText(null);
                alert.setContentText("Usuário editado com sucesso!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Erro ao editar usuário!");
                alert.showAndWait();
            }
        });
    }

    private void excluirUsuarioSelecionado() {
        Usuario usuarioSelecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();

        if (usuarioSelecionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Selecione um usuário para excluir!");
            alert.showAndWait();
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Tem certeza que deseja excluir o usuário: " + usuarioSelecionado.getNome() + "?");

        confirmacao.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean sucesso = controller.excluirUsuario(usuarioSelecionado.getId());

                if (sucesso) {
                    // Recarrega a tabela
                    atualizarTabela();

                    Alert sucesso_alert = new Alert(Alert.AlertType.INFORMATION);
                    sucesso_alert.setTitle("Sucesso");
                    sucesso_alert.setHeaderText(null);
                    sucesso_alert.setContentText("Usuário excluído com sucesso!");
                    sucesso_alert.showAndWait();
                } else {
                    Alert erro = new Alert(Alert.AlertType.ERROR);
                    erro.setTitle("Erro");
                    erro.setHeaderText(null);
                    erro.setContentText("Erro ao excluir usuário!");
                    erro.showAndWait();
                }
            }
        });
    }

    private void atualizarTabela() {
        try {
            // Recarrega os dados do arquivo antes de atualizar a tabela
            RepUsuarios.usuarios = new ArrayList<>(PersistenciaUtils.carregarUsuariosDat());

            // Debug: verifica se há usuários carregados
            System.out.println("Número de usuários carregados: " + RepUsuarios.usuarios.size());
            for (Usuario u : RepUsuarios.usuarios) {
                System.out.println("Usuário: " + u.getNome() + " - ID: " + u.getId());
            }

            // Cria uma nova ObservableList com os dados atualizados
            ObservableList<Usuario> dados = FXCollections.observableArrayList(RepUsuarios.usuarios);
            tabelaUsuarios.setItems(dados);
            tabelaUsuarios.refresh();

            // Adiciona um label de status se não houver usuários
            if (RepUsuarios.usuarios.isEmpty()) {
                System.out.println("AVISO: Nenhum usuário encontrado!");
            }

        } catch (Exception e) {
            System.err.println("Erro ao atualizar tabela: " + e.getMessage());
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao carregar dados: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void mostrar() {
        try {
            // Garante que os dados sejam carregados antes de mostrar a tela
            System.out.println("Carregando dados para exibição...");
            atualizarTabela();

            // Configuração do layout
            VBox layout = new VBox(10);
            layout.setPadding(new Insets(10));
            layout.setAlignment(Pos.CENTER);

            Label titulo = new Label("Usuários Cadastrados");
            titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            // Painel de botões
            HBox painelBotoes = new HBox(10);
            painelBotoes.setAlignment(Pos.CENTER);

            Button btnEditar = new Button("Editar Usuário");
            btnEditar.setOnAction(e -> editarUsuarioSelecionado());

            Button btnExcluir = new Button("Excluir Usuário");
            btnExcluir.setOnAction(e -> excluirUsuarioSelecionado());

            Button btnAtualizar = new Button("Atualizar Lista");
            btnAtualizar.setOnAction(e -> atualizarTabela());

            Button btnVoltar = new Button("Voltar");
            btnVoltar.setOnAction(e -> stage.close());

            // Botão de debug para verificar dados
            Button btnDebug = new Button("Debug - Verificar Dados");
            btnDebug.setOnAction(e -> {
                System.out.println("=== DEBUG ===");
                System.out.println("Tamanho da lista: " + RepUsuarios.usuarios.size());
                System.out.println("Itens na tabela: " + tabelaUsuarios.getItems().size());

                if (RepUsuarios.usuarios.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Debug");
                    alert.setHeaderText(null);
                    alert.setContentText("A lista de usuários está vazia!");
                    alert.showAndWait();
                } else {
                    StringBuilder sb = new StringBuilder("Usuários encontrados:\n");
                    for (Usuario u : RepUsuarios.usuarios) {
                        sb.append("- ").append(u.getNome()).append(" (ID: ").append(u.getId()).append(")\n");
                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Debug");
                    alert.setHeaderText("Dados carregados:");
                    alert.setContentText(sb.toString());
                    alert.showAndWait();
                }
            });

            painelBotoes.getChildren().addAll(btnEditar, btnExcluir, btnAtualizar, btnVoltar, btnDebug);

            layout.getChildren().addAll(titulo, tabelaUsuarios, painelBotoes);

            // Configuração da cena
            Scene scene = new Scene(layout, 900, 500);
            stage.setScene(scene);

            stage.show();

        } catch (Exception e) {
            System.err.println("Erro ao mostrar tela: " + e.getMessage());
            e.printStackTrace();
        }
    }
}