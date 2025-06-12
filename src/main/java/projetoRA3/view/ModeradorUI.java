package projetoRA3.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import projetoRA3.controller.ModeradorController;
import projetoRA3.model.Moderador;

import java.util.List;

public class ModeradorUI {
    private ModeradorController controller = new ModeradorController();
    private ObservableList<Moderador> moderadores;
    private TableView<Moderador> tabela;

    private TextField campoId = new TextField();
    private TextField campoNome = new TextField();
    private TextField campoEmail = new TextField();
    private TextField campoTelefone = new TextField();
    private PasswordField campoSenha = new PasswordField();

    private Button btnAdicionar = new Button("Adicionar");
    private Button btnAtualizar = new Button("Atualizar");
    private Button btnRemover = new Button("Remover");

    private VBox layout;

    public ModeradorUI() {
        moderadores = FXCollections.observableArrayList(controller.listarModeradores());
        construirInterface();
    }

    private void construirInterface() {
        campoId.setPromptText("ID");
        campoNome.setPromptText("Nome");
        campoEmail.setPromptText("Email");
        campoTelefone.setPromptText("Telefone");
        campoSenha.setPromptText("Senha");

        tabela = new TableView<>(moderadores);
        configurarTabela();

        btnAdicionar.setOnAction(e -> adicionarModerador());
        btnAtualizar.setOnAction(e -> atualizarModerador());
        btnRemover.setOnAction(e -> removerModerador());

        GridPane gridEntrada = new GridPane();
        gridEntrada.setHgap(10);
        gridEntrada.setVgap(10);
        gridEntrada.setPadding(new Insets(10));

        gridEntrada.add(new Label("ID:"), 0, 0);
        gridEntrada.add(campoId, 1, 0);
        gridEntrada.add(new Label("Nome:"), 0, 1);
        gridEntrada.add(campoNome, 1, 1);
        gridEntrada.add(new Label("Email:"), 0, 2);
        gridEntrada.add(campoEmail, 1, 2);
        gridEntrada.add(new Label("Telefone:"), 0, 3);
        gridEntrada.add(campoTelefone, 1, 3);
        gridEntrada.add(new Label("Senha:"), 0, 4);
        gridEntrada.add(campoSenha, 1, 4);

        HBox botoes = new HBox(10, btnAdicionar, btnAtualizar, btnRemover);
        botoes.setPadding(new Insets(10));

        layout = new VBox(10, gridEntrada, botoes, tabela);
        layout.setPadding(new Insets(10));

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                campoId.setText(String.valueOf(newSel.getId()));
                campoNome.setText(newSel.getNome());
                campoEmail.setText(newSel.getEmail());
                campoTelefone.setText(newSel.getTelefone());
                campoSenha.setText(newSel.getSenha());
                campoId.setDisable(true);
            }
        });
    }

    private void configurarTabela() {
        tabela.setPrefHeight(200);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Moderador, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getId()).asObject());

        TableColumn<Moderador, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));

        TableColumn<Moderador, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEmail()));

        TableColumn<Moderador, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTelefone()));

        TableColumn<Moderador, String> colSenha = new TableColumn<>("Senha");
        colSenha.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getSenha()));

        tabela.getColumns().addAll(colId, colNome, colEmail, colTelefone, colSenha);
    }

    private void adicionarModerador() {
        try {
            if (validarCampos(campoId, campoNome, campoEmail, campoTelefone, campoSenha)) return;

            Moderador novo = new Moderador(
                    Integer.parseInt(campoId.getText()),
                    campoNome.getText(),
                    campoEmail.getText(),
                    campoTelefone.getText(),
                    campoSenha.getText()
            );

            controller.adicionarModerador(novo);
            moderadores.setAll(controller.listarModeradores());
            limparCampos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "ID deve ser um número válido");
        }
    }

    private void atualizarModerador() {
        Moderador selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            if (validarCampos(null, campoNome, campoEmail, campoTelefone, campoSenha)) return;

            selecionado.setNome(campoNome.getText());
            selecionado.setEmail(campoEmail.getText());
            selecionado.setTelefone(campoTelefone.getText());
            selecionado.setSenha(campoSenha.getText());

            controller.atualizarModerador(selecionado);
            tabela.refresh();
            limparCampos();
        }
    }

    private void removerModerador() {
        Moderador selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            controller.removerModerador(selecionado.getId());
            moderadores.remove(selecionado);
            limparCampos();
        }
    }

    private boolean validarCampos(TextField id, TextField nome, TextField email, TextField telefone, TextField senha) {
        if ((id != null && id.getText().isEmpty()) || nome.getText().isEmpty() || email.getText().isEmpty() ||
                telefone.getText().isEmpty() || senha.getText().isEmpty()) {
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
        campoTelefone.clear();
        campoSenha.clear();
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
