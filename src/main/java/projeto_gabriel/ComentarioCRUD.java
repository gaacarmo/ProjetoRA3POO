package projeto_gabriel;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class ComentarioCRUD extends Application {

    private ObservableList<Comentario> comentarios;
    private TableView<Comentario> tabela;

    @Override
    public void start(Stage stage) {
        // Dados simulados de usuário e postagem
        List<Usuario> usuarios = List.of(
                new Usuario(1, "Larissa Carmo", "larissa@email.com"),
                new Usuario(2, "Gabriel Silva", "gabriel@email.com")
        );

        List<Postagem> postagens = List.of(
                new Postagem(1, "Chave encontrada", "Chave com pingente azul", LocalDate.now().minusDays(2)),
                new Postagem(2, "Caderno perdido", "Caderno de capa preta com anotações", LocalDate.now().minusDays(1))
        );

        // Carregar comentários
        List<Comentario> listaInicial = PersistenciaComentario.carregar();
        comentarios = FXCollections.observableArrayList(listaInicial);

        // Campos de entrada
        TextField campoId = new TextField();
        campoId.setPromptText("ID");

        TextField campoConteudo = new TextField();
        campoConteudo.setPromptText("Conteúdo");

        DatePicker campoData = new DatePicker();
        campoData.setPromptText("Data");

        CheckBox checkPrivado = new CheckBox("Privado");

        ComboBox<Usuario> comboUsuario = new ComboBox<>(FXCollections.observableArrayList(usuarios));
        comboUsuario.setPromptText("Usuário");

        ComboBox<Postagem> comboPostagem = new ComboBox<>(FXCollections.observableArrayList(postagens));
        comboPostagem.setPromptText("Postagem");

        // Tabela
        tabela = new TableView<>(comentarios);
        tabela.setPrefHeight(200);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Comentario, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getId()).asObject());

        TableColumn<Comentario, String> colConteudo = new TableColumn<>("Conteúdo");
        colConteudo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getConteudo()));

        TableColumn<Comentario, LocalDate> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getData()));

        TableColumn<Comentario, Boolean> colPrivado = new TableColumn<>("Privado");
        colPrivado.setCellValueFactory(c -> new javafx.beans.property.SimpleBooleanProperty(c.getValue().isPrivado()).asObject());

        tabela.getColumns().addAll(colId, colConteudo, colData, colPrivado);

        // Botões
        Button btnAdicionar = new Button("Adicionar");
        btnAdicionar.setOnAction(e -> {
            try {
                if (
                        campoId.getText().isEmpty() ||
                                campoConteudo.getText().isEmpty() ||
                                campoData.getValue() == null ||
                                comboUsuario.getValue() == null ||
                                comboPostagem.getValue() == null
                ) {
                    mostrarErro("Preencha todos os campos obrigatórios antes de adicionar.");
                    return;
                }

                int id = Integer.parseInt(campoId.getText());

                Comentario c = new Comentario(
                        id,
                        campoConteudo.getText(),
                        campoData.getValue(),
                        checkPrivado.isSelected(),
                        comboUsuario.getValue().getId(),
                        comboPostagem.getValue().getId()
                );

                comentarios.add(c);
                PersistenciaComentario.salvar(comentarios);
                limparCampos(campoId, campoConteudo, campoData, checkPrivado, comboUsuario, comboPostagem);

            } catch (NumberFormatException ex) {
                mostrarErro("O ID deve ser um número inteiro.");
            } catch (Exception ex) {
                mostrarErro("Erro inesperado: " + ex.getMessage());
            }
        });

        Button btnRemover = new Button("Remover");
        btnRemover.setOnAction(e -> {
            Comentario selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                comentarios.remove(selecionado);
                PersistenciaComentario.salvar(comentarios);
            }
        });

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.setOnAction(e -> {
            Comentario selecionado = tabela.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                try {
                    if (
                            campoConteudo.getText().isEmpty() ||
                                    campoData.getValue() == null ||
                                    comboUsuario.getValue() == null ||
                                    comboPostagem.getValue() == null
                    ) {
                        mostrarErro("Preencha todos os campos obrigatórios antes de atualizar.");
                        return;
                    }

                    selecionado.setConteudo(campoConteudo.getText());
                    selecionado.setData(campoData.getValue());
                    selecionado.setPrivado(checkPrivado.isSelected());
                    selecionado.setIdUsuario(comboUsuario.getValue().getId());
                    selecionado.setIdPostagem(comboPostagem.getValue().getId());
                    tabela.refresh();
                    PersistenciaComentario.salvar(comentarios);
                    limparCampos(campoId, campoConteudo, campoData, checkPrivado, comboUsuario, comboPostagem);
                } catch (Exception ex) {
                    mostrarErro("Erro ao atualizar comentário.");
                }
            }
        });

        // Layout
        HBox entrada = new HBox(10, campoId, campoConteudo, campoData, checkPrivado, comboUsuario, comboPostagem);
        HBox botoes = new HBox(10, btnAdicionar, btnAtualizar, btnRemover);
        VBox layout = new VBox(10, entrada, botoes, tabela);
        layout.setPadding(new Insets(10));

        // Cena
        stage.setScene(new Scene(layout, 1100, 400));
        stage.setTitle("CRUD Comentário");
        stage.show();
    }

    private void limparCampos(TextField id, TextField conteudo, DatePicker data, CheckBox privado,
                              ComboBox<Usuario> usuario, ComboBox<Postagem> postagem) {
        id.clear();
        conteudo.clear();
        data.setValue(null);
        privado.setSelected(false);
        usuario.setValue(null);
        postagem.setValue(null);
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
