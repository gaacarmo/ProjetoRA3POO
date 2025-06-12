package projetoRA3.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import projetoRA3.controller.UsuarioController;
import projetoRA3.model.Usuario;

import java.util.List;

public class UsuarioUI {
    private UsuarioController controller = new UsuarioController();
    private ObservableList<Usuario> usuarios;
    private TableView<Usuario> tabela;

    // Campos de entrada como atributos para uso nos handlers
    private TextField campoId = new TextField();
    private TextField campoNome = new TextField();
    private TextField campoEmail = new TextField();
    private Button btnAdicionar = new Button("Adicionar");
    private Button btnAtualizar = new Button("Atualizar");
    private Button btnRemover = new Button("Remover");

    private VBox layout;

    public UsuarioUI() {
        usuarios = FXCollections.observableArrayList(controller.listarUsuarios());
        construirInterface();
    }

    private void construirInterface() {
        campoId.setPromptText("ID");
        campoNome.setPromptText("Nome");
        campoEmail.setPromptText("Email");

        tabela = new TableView<>(usuarios);
        configurarTabela();

        btnAdicionar.setOnAction(e -> adicionarUsuario());
        btnAtualizar.setOnAction(e -> atualizarUsuario());
        btnRemover.setOnAction(e -> removerUsuario());

        HBox entrada = new HBox(10, campoId, campoNome, campoEmail);
        HBox botoes = new HBox(10, btnAdicionar, btnAtualizar, btnRemover);
        layout = new VBox(10, entrada, botoes, tabela);
        layout.setPadding(new Insets(10));

        // Seleção da tabela preenche os campos
        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                campoId.setText(String.valueOf(newSel.getId()));
                campoNome.setText(newSel.getNome());
                campoEmail.setText(newSel.getEmail());
                campoId.setDisable(true); // ID não pode mudar na atualização
            }
        });
    }

    private void configurarTabela() {
        tabela.setPrefHeight(200);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Usuario, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getId()).asObject());

        TableColumn<Usuario, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));

        TableColumn<Usuario, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEmail()));

        tabela.getColumns().addAll(colId, colNome, colEmail);
    }

    // Métodos dos botões

    private void adicionarUsuario() {
        try {
            if (validarCampos(campoId, campoNome, campoEmail)) return;

            Usuario novo = new Usuario(
                    Integer.parseInt(campoId.getText()),
                    campoNome.getText(),
                    campoEmail.getText()
            );

            controller.adicionarUsuario(novo);
            usuarios.setAll(controller.listarUsuarios());
            limparCampos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "ID deve ser um número válido");
        }
    }

    private void atualizarUsuario() {
        Usuario selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            if (validarCampos(null, campoNome, campoEmail)) return;

            selecionado.setNome(campoNome.getText());
            selecionado.setEmail(campoEmail.getText());

            controller.atualizarUsuario(selecionado);
            tabela.refresh();
            limparCampos();
        }
    }

    private void removerUsuario() {
        Usuario selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            controller.removerUsuario(selecionado.getId());
            usuarios.remove(selecionado);
            limparCampos();
        }
    }

    private boolean validarCampos(TextField id, TextField nome, TextField email) {
        if ((id != null && id.getText().isEmpty()) || nome.getText().isEmpty() || email.getText().isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos obrigatórios");
            return true;
        }
        return false;
    }

    private void limparCampos() {
        campoId.clear();
        campoId.setDisable(false);
        campoNome.clear();
        campoEmail.clear();
        tabela.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public VBox getLayout() {
        return layout;
    }
}

