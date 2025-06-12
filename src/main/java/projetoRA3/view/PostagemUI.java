package projetoRA3.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import projetoRA3.controller.PostagemController;
import projetoRA3.model.Postagem;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class PostagemUI {
    private PostagemController controller = new PostagemController();
    private ObservableList<Postagem> postagens;
    private TableView<Postagem> tabela;

    private TextField campoId = new TextField();
    private TextField campoNome = new TextField();
    private TextField campoDescricao = new TextField();
    private TextField campoLocal = new TextField();
    private TextField campoCor = new TextField();
    private TextField campoCategoria = new TextField();
    private TextField campoData = new TextField();
    private TextField campoImagem = new TextField();
    private TextField campoTipoUsuario = new TextField();
    private TextField campoIdUsuario = new TextField();
    private TextField campoIdAdmin = new TextField();

    private Button btnAdicionar = new Button("Adicionar");
    private Button btnAtualizar = new Button("Atualizar");
    private Button btnRemover = new Button("Remover");

    private VBox layout;

    public PostagemUI() {
        postagens = FXCollections.observableArrayList(controller.listarPostagens());
        construirInterface();
    }

    private void construirInterface() {
        campoId.setPromptText("ID");
        campoNome.setPromptText("Nome");
        campoDescricao.setPromptText("Descrição");
        campoLocal.setPromptText("Local");
        campoCor.setPromptText("Cor");
        campoCategoria.setPromptText("Categoria");
        campoData.setPromptText("Data (yyyy-mm-dd)");
        campoImagem.setPromptText("Imagem");
        campoTipoUsuario.setPromptText("Tipo Usuário");
        campoIdUsuario.setPromptText("ID Usuário");
        campoIdAdmin.setPromptText("ID Admin");

        tabela = new TableView<>(postagens);
        configurarTabela();

        btnAdicionar.setOnAction(e -> adicionarPostagem());
        btnAtualizar.setOnAction(e -> atualizarPostagem());
        btnRemover.setOnAction(e -> removerPostagem());

        GridPane gridEntrada = new GridPane();
        gridEntrada.setHgap(10);
        gridEntrada.setVgap(10);
        gridEntrada.add(new Label("ID:"), 0, 0);
        gridEntrada.add(campoId, 1, 0);
        gridEntrada.add(new Label("Nome:"), 0, 1);
        gridEntrada.add(campoNome, 1, 1);
        gridEntrada.add(new Label("Descrição:"), 0, 2);
        gridEntrada.add(campoDescricao, 1, 2);
        gridEntrada.add(new Label("Local:"), 0, 3);
        gridEntrada.add(campoLocal, 1, 3);
        gridEntrada.add(new Label("Cor:"), 0, 4);
        gridEntrada.add(campoCor, 1, 4);
        gridEntrada.add(new Label("Categoria:"), 0, 5);
        gridEntrada.add(campoCategoria, 1, 5);
        gridEntrada.add(new Label("Data:"), 0, 6);
        gridEntrada.add(campoData, 1, 6);
        gridEntrada.add(new Label("Imagem:"), 0, 7);
        gridEntrada.add(campoImagem, 1, 7);
        gridEntrada.add(new Label("Tipo Usuário:"), 0, 8);
        gridEntrada.add(campoTipoUsuario, 1, 8);
        gridEntrada.add(new Label("ID Usuário:"), 0, 9);
        gridEntrada.add(campoIdUsuario, 1, 9);
        gridEntrada.add(new Label("ID Admin:"), 0, 10);
        gridEntrada.add(campoIdAdmin, 1, 10);

        HBox botoes = new HBox(10, btnAdicionar, btnAtualizar, btnRemover);

        layout = new VBox(10, gridEntrada, botoes, tabela);
        layout.setPadding(new Insets(10));

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                campoId.setText(String.valueOf(newSel.getId()));
                campoNome.setText(newSel.getNome());
                campoDescricao.setText(newSel.getDescricao());
                campoLocal.setText(newSel.getLocal());
                campoCor.setText(newSel.getCor());
                campoCategoria.setText(newSel.getCategoria());
                campoData.setText(newSel.getData().toString());
                campoImagem.setText(newSel.getImagem());
                campoTipoUsuario.setText(newSel.getTipoUsuario());
                campoIdUsuario.setText(String.valueOf(newSel.getIdUsuario()));
                campoIdAdmin.setText(String.valueOf(newSel.getIdAdmin()));
                campoId.setDisable(true);
            }
        });
    }

    private void configurarTabela() {
        tabela.setPrefHeight(250);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Postagem, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getId()).asObject());

        TableColumn<Postagem, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));

        TableColumn<Postagem, String> colDescricao = new TableColumn<>("Descrição");
        colDescricao.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDescricao()));

        TableColumn<Postagem, String> colLocal = new TableColumn<>("Local");
        colLocal.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getLocal()));

        TableColumn<Postagem, String> colCor = new TableColumn<>("Cor");
        colCor.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getCor()));

        TableColumn<Postagem, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getCategoria()));

        TableColumn<Postagem, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getData().toString()));

        TableColumn<Postagem, String> colImagem = new TableColumn<>("Imagem");
        colImagem.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getImagem()));

        TableColumn<Postagem, String> colTipoUsuario = new TableColumn<>("Tipo Usuário");
        colTipoUsuario.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTipoUsuario()));

        TableColumn<Postagem, Integer> colIdUsuario = new TableColumn<>("ID Usuário");
        colIdUsuario.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getIdUsuario()).asObject());

        TableColumn<Postagem, Integer> colIdAdmin = new TableColumn<>("ID Admin");
        colIdAdmin.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getIdAdmin()).asObject());

        tabela.getColumns().addAll(colId, colNome, colDescricao, colLocal, colCor, colCategoria,
                colData, colImagem, colTipoUsuario, colIdUsuario, colIdAdmin);
    }

    private void adicionarPostagem() {
        try {
            if (validarCamposAdicionar()) return;

            int id = Integer.parseInt(campoId.getText());
            String nome = campoNome.getText();
            String descricao = campoDescricao.getText();
            String local = campoLocal.getText();
            String cor = campoCor.getText();
            String categoria = campoCategoria.getText();
            LocalDate data = LocalDate.parse(campoData.getText());
            long timestamp = System.currentTimeMillis();
            String imagem = campoImagem.getText();
            String tipoUsuario = campoTipoUsuario.getText();
            int idUsuario = Integer.parseInt(campoIdUsuario.getText());
            int idAdmin = Integer.parseInt(campoIdAdmin.getText());

            Postagem nova = new Postagem(id, nome, descricao, local, cor, categoria, data,
                    timestamp, imagem, tipoUsuario, idUsuario, idAdmin);

            controller.adicionarPostagem(nova);
            postagens.setAll(controller.listarPostagens());
            limparCampos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "IDs devem ser números válidos.");
        } catch (DateTimeParseException e) {
            mostrarAlerta("Erro", "Data deve estar no formato yyyy-mm-dd.");
        }
    }

    private void atualizarPostagem() {
        Postagem selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            try {
                if (validarCamposAtualizar()) return;

                selecionada.setNome(campoNome.getText());
                selecionada.setDescricao(campoDescricao.getText());
                selecionada.setLocal(campoLocal.getText());
                selecionada.setCor(campoCor.getText());
                selecionada.setCategoria(campoCategoria.getText());
                selecionada.setData(LocalDate.parse(campoData.getText()));

                selecionada.setImagem(campoImagem.getText());
                selecionada.setTipoUsuario(campoTipoUsuario.getText());
                selecionada.setIdUsuario(Integer.parseInt(campoIdUsuario.getText()));
                selecionada.setIdAdmin(Integer.parseInt(campoIdAdmin.getText()));

                controller.atualizarPostagem(selecionada);
                tabela.refresh();
                limparCampos();
            } catch (NumberFormatException e) {
                mostrarAlerta("Erro", "IDs devem ser números válidos.");
            } catch (DateTimeParseException e) {
                mostrarAlerta("Erro", "Data deve estar no formato yyyy-mm-dd.");
            }
        }
    }

    private void removerPostagem() {
        Postagem selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            controller.removerPostagem(selecionada.getId());
            postagens.remove(selecionada);
            limparCampos();
        }
    }

    private boolean validarCamposAdicionar() {
        if (campoId.getText().isEmpty() || campoNome.getText().isEmpty() || campoDescricao.getText().isEmpty() ||
                campoLocal.getText().isEmpty() || campoCor.getText().isEmpty() || campoCategoria.getText().isEmpty() ||
                campoData.getText().isEmpty() || campoImagem.getText().isEmpty() || campoTipoUsuario.getText().isEmpty() ||
                campoIdUsuario.getText().isEmpty() || campoIdAdmin.getText().isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos obrigatórios para adicionar.");
            return true;
        }
        return false;
    }

    private boolean validarCamposAtualizar() {
        if (campoNome.getText().isEmpty() || campoDescricao.getText().isEmpty() || campoLocal.getText().isEmpty() ||
                campoCor.getText().isEmpty() || campoCategoria.getText().isEmpty() || campoData.getText().isEmpty() ||
                campoImagem.getText().isEmpty() || campoTipoUsuario.getText().isEmpty() || campoIdUsuario.getText().isEmpty() ||
                campoIdAdmin.getText().isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos obrigatórios para atualizar.");
            return true;
        }
        return false;
    }

    private void limparCampos() {
        campoId.clear();
        campoId.setDisable(false);
        campoNome.clear();
        campoDescricao.clear();
        campoLocal.clear();
        campoCor.clear();
        campoCategoria.clear();
        campoData.clear();
        campoImagem.clear();
        campoTipoUsuario.clear();
        campoIdUsuario.clear();
        campoIdAdmin.clear();
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
