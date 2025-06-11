package projeto_gabriel.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import projeto_gabriel.controller.ComentarioController;
import projeto_gabriel.model.Comentario;
import projeto_gabriel.model.Postagem;
import projeto_gabriel.model.Usuario;
import java.time.LocalDate;
import java.util.List;

public class ComentarioCRUD extends Application {
    private ComentarioController controller = new ComentarioController();
    private ObservableList<Comentario> comentarios;
    private TableView<Comentario> tabela;

    @Override
    public void start(Stage stage) {
        // Dados simulados
        List<Usuario> usuarios = List.of(
                new Usuario(1, "Larissa Carmo", "larissa@email.com"),
                new Usuario(2, "Gabriel Silva", "gabriel@email.com")
        );

        List<Postagem> postagens = List.of(
                new Postagem(1, "Chave encontrada", "Chave com pingente azul", LocalDate.now().minusDays(2)),
                new Postagem(2, "Caderno perdido", "Caderno de capa preta com anotações", LocalDate.now().minusDays(1))
        );

        // Carregar comentários
        comentarios = FXCollections.observableArrayList(controller.listarComentarios());

        // Configuração da interface
        configurarInterface(stage, usuarios, postagens);
    }

    private void configurarInterface(Stage stage, List<Usuario> usuarios, List<Postagem> postagens) {
        // Componentes da interface
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

        // Configuração da tabela
        tabela = new TableView<>(comentarios);
        configurarTabela();

        // Botões
        Button btnAdicionar = new Button("Adicionar");
        btnAdicionar.setOnAction(e -> adicionarComentario(campoId, campoConteudo, campoData, checkPrivado, comboUsuario, comboPostagem));

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.setOnAction(e -> atualizarComentario(campoConteudo, campoData, checkPrivado, comboUsuario, comboPostagem));

        Button btnRemover = new Button("Remover");
        btnRemover.setOnAction(e -> removerComentario());

        // Layout
        HBox entrada = new HBox(10, campoId, campoConteudo, campoData, checkPrivado, comboUsuario, comboPostagem);
        HBox botoes = new HBox(10, btnAdicionar, btnAtualizar, btnRemover);
        VBox layout = new VBox(10, entrada, botoes, tabela);
        layout.setPadding(new Insets(10));

        // Configuração da seleção na tabela
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                preencherCampos(newSelection, campoId, campoConteudo, campoData, checkPrivado, comboUsuario, comboPostagem);
            }
        });

        // Cena
        stage.setScene(new Scene(layout, 1100, 400));
        stage.setTitle("CRUD Comentário");
        stage.show();
    }

    private void configurarTabela() {
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
    }

    private void adicionarComentario(TextField id, TextField conteudo, DatePicker data, CheckBox privado,
                                     ComboBox<Usuario> usuario, ComboBox<Postagem> postagem) {
        try {
            if (validarCampos(conteudo, data, usuario, postagem)) return;

            Comentario novo = new Comentario(
                    Integer.parseInt(id.getText()),
                    conteudo.getText(),
                    data.getValue(),
                    privado.isSelected(),
                    usuario.getValue().getId(),
                    postagem.getValue().getId()
            );

            controller.adicionarComentario(novo);
            comentarios.setAll(controller.listarComentarios());
            limparCampos(id, conteudo, data, privado, usuario, postagem);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "ID deve ser um número válido");
        }
    }

    private void atualizarComentario(TextField conteudo, DatePicker data, CheckBox privado,
                                     ComboBox<Usuario> usuario, ComboBox<Postagem> postagem) {
        Comentario selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            if (validarCampos(conteudo, data, usuario, postagem)) return;

            selecionado.setConteudo(conteudo.getText());
            selecionado.setData(data.getValue());
            selecionado.setPrivado(privado.isSelected());
            selecionado.setIdUsuario(usuario.getValue().getId());
            selecionado.setIdPostagem(postagem.getValue().getId());

            controller.atualizarComentario(selecionado);
            tabela.refresh();
            limparCampos(null, conteudo, data, privado, usuario, postagem);
        }
    }

    private void removerComentario() {
        Comentario selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            controller.removerComentario(selecionado.getId());
            comentarios.remove(selecionado);
        }
    }

    private boolean validarCampos(TextField conteudo, DatePicker data,
                                  ComboBox<Usuario> usuario, ComboBox<Postagem> postagem) {
        if (conteudo.getText().isEmpty() || data.getValue() == null ||
                usuario.getValue() == null || postagem.getValue() == null) {
            mostrarAlerta("Erro", "Preencha todos os campos obrigatórios");
            return true;
        }
        return false;
    }

    private void preencherCampos(Comentario comentario, TextField id, TextField conteudo,
                                 DatePicker data, CheckBox privado, ComboBox<Usuario> usuario,
                                 ComboBox<Postagem> postagem) {
        if (id != null) id.setText(String.valueOf(comentario.getId()));
        conteudo.setText(comentario.getConteudo());
        data.setValue(comentario.getData());
        privado.setSelected(comentario.isPrivado());
        usuario.getSelectionModel().select(new Usuario(comentario.getIdUsuario(), "", ""));
        postagem.getSelectionModel().select(new Postagem(comentario.getIdPostagem(), "", "", null));
    }

    private void limparCampos(TextField id, TextField conteudo, DatePicker data,
                              CheckBox privado, ComboBox<Usuario> usuario, ComboBox<Postagem> postagem) {
        if (id != null) id.clear();
        conteudo.clear();
        data.setValue(null);
        privado.setSelected(false);
        usuario.getSelectionModel().clearSelection();
        postagem.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}