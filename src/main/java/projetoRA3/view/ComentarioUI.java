package projetoRA3.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import projetoRA3.controller.ComentarioController;
import projetoRA3.model.Comentario;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ComentarioUI {
    private ComentarioController controller = new ComentarioController();
    private ObservableList<Comentario> comentarios;
    private TableView<Comentario> tabela;

    private TextField campoId = new TextField();
    private TextField campoTexto = new TextField();
    private TextField campoData = new TextField();
    private CheckBox campoAprovado = new CheckBox("Aprovado");
    private TextField campoIdUsuario = new TextField();
    private TextField campoIdPostagem = new TextField();

    private Button btnAdicionar = new Button("Adicionar");
    private Button btnAtualizar = new Button("Atualizar");
    private Button btnRemover = new Button("Remover");

    private VBox layout;

    public ComentarioUI() {
        comentarios = FXCollections.observableArrayList(controller.listarComentarios());
        construirInterface();
    }

    private void construirInterface() {
        campoId.setPromptText("ID");
        campoTexto.setPromptText("Texto");
        campoData.setPromptText("Data (yyyy-mm-dd)");
        campoIdUsuario.setPromptText("ID Usuário");
        campoIdPostagem.setPromptText("ID Postagem");

        tabela = new TableView<>(comentarios);
        configurarTabela();

        btnAdicionar.setOnAction(e -> adicionarComentario());
        btnAtualizar.setOnAction(e -> atualizarComentario());
        btnRemover.setOnAction(e -> removerComentario());

        HBox entrada1 = new HBox(10, campoId, campoTexto, campoData);
        HBox entrada2 = new HBox(10, campoAprovado, campoIdUsuario, campoIdPostagem);
        HBox botoes = new HBox(10, btnAdicionar, btnAtualizar, btnRemover);

        layout = new VBox(10, entrada1, entrada2, botoes, tabela);
        layout.setPadding(new Insets(10));

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                campoId.setText(String.valueOf(newSel.getId()));
                campoTexto.setText(newSel.getTexto());
                campoData.setText(newSel.getData().toString());
                campoAprovado.setSelected(newSel.isAprovado());
                campoIdUsuario.setText(String.valueOf(newSel.getIdUsuario()));
                campoIdPostagem.setText(String.valueOf(newSel.getIdPostagem()));
                campoId.setDisable(true);
            }
        });
    }

    private void configurarTabela() {
        tabela.setPrefHeight(200);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Comentario, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getId()).asObject());

        TableColumn<Comentario, String> colTexto = new TableColumn<>("Texto");
        colTexto.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTexto()));

        TableColumn<Comentario, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getData().toString()));

        TableColumn<Comentario, Boolean> colAprovado = new TableColumn<>("Aprovado");
        colAprovado.setCellValueFactory(c -> new javafx.beans.property.SimpleBooleanProperty(c.getValue().isAprovado()).asObject());

        TableColumn<Comentario, Integer> colIdUsuario = new TableColumn<>("ID Usuário");
        colIdUsuario.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getIdUsuario()).asObject());

        TableColumn<Comentario, Integer> colIdPostagem = new TableColumn<>("ID Postagem");
        colIdPostagem.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getIdPostagem()).asObject());

        tabela.getColumns().addAll(colId, colTexto, colData, colAprovado, colIdUsuario, colIdPostagem);
    }

    private void adicionarComentario() {
        try {
            if (validarCamposAdicionar()) return;

            int id = Integer.parseInt(campoId.getText());
            String texto = campoTexto.getText();
            LocalDate data = LocalDate.parse(campoData.getText());
            boolean aprovado = campoAprovado.isSelected();
            int idUsuario = Integer.parseInt(campoIdUsuario.getText());
            int idPostagem = Integer.parseInt(campoIdPostagem.getText());

            Comentario novo = new Comentario(id, texto, data, aprovado, idUsuario, idPostagem);

            controller.adicionarComentario(novo);
            comentarios.setAll(controller.listarComentarios());
            limparCampos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "IDs devem ser números válidos.");
        } catch (DateTimeParseException e) {
            mostrarAlerta("Erro", "Data deve estar no formato yyyy-mm-dd.");
        }
    }

    private void atualizarComentario() {
        Comentario selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            try {
                if (validarCamposAtualizar()) return;

                selecionado.setTexto(campoTexto.getText());
                selecionado.setData(LocalDate.parse(campoData.getText()));
                selecionado.setAprovado(campoAprovado.isSelected());
                selecionado.setIdUsuario(Integer.parseInt(campoIdUsuario.getText()));
                selecionado.setIdPostagem(Integer.parseInt(campoIdPostagem.getText()));

                controller.atualizarComentario(selecionado);
                tabela.refresh();
                limparCampos();
            } catch (NumberFormatException e) {
                mostrarAlerta("Erro", "IDs devem ser números válidos.");
            } catch (DateTimeParseException e) {
                mostrarAlerta("Erro", "Data deve estar no formato yyyy-mm-dd.");
            }
        }
    }

    private void removerComentario() {
        Comentario selecionado = tabela.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            controller.removerComentario(selecionado.getId());
            comentarios.remove(selecionado);
            limparCampos();
        }
    }

    private boolean validarCamposAdicionar() {
        if (campoId.getText().isEmpty() || campoTexto.getText().isEmpty() || campoData.getText().isEmpty() ||
                campoIdUsuario.getText().isEmpty() || campoIdPostagem.getText().isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos obrigatórios para adicionar.");
            return true;
        }
        return false;
    }

    private boolean validarCamposAtualizar() {
        if (campoTexto.getText().isEmpty() || campoData.getText().isEmpty() ||
                campoIdUsuario.getText().isEmpty() || campoIdPostagem.getText().isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos obrigatórios para atualizar.");
            return true;
        }
        return false;
    }

    private void limparCampos() {
        campoId.clear();
        campoId.setDisable(false);
        campoTexto.clear();
        campoData.clear();
        campoAprovado.setSelected(false);
        campoIdUsuario.clear();
        campoIdPostagem.clear();
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