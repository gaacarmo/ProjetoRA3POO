package projeto_lucas;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDateTime;

public class PostagemCRUD extends Application {

    private TextField idField, nomeField, descricaoField, localField, corField, categoriaField, imagemField;
    private DatePicker dataPicker;
    private ComboBox<Usuario> usuarioComboBox;
    private ComboBox<Moderador> moderadorComboBox;
    private Button salvarBtn, editarBtn, excluirBtn;

    private TableView<Postagem> tabela;
    private PersistenciaPostagem persistencia = new PersistenciaPostagem();

    @Override
    public void start(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20;");

        // Campos
        idField = new TextField();
        idField.setVisible(false);

        nomeField = new TextField();        nomeField.setPromptText("Nome");
        descricaoField = new TextField();   descricaoField.setPromptText("Descrição");
        localField = new TextField();       localField.setPromptText("Local");
        corField = new TextField();         corField.setPromptText("Cor");
        categoriaField = new TextField();   categoriaField.setPromptText("Categoria");
        imagemField = new TextField();      imagemField.setPromptText("URL da Imagem");
        dataPicker = new DatePicker();      dataPicker.setPromptText("Data");

        usuarioComboBox = new ComboBox<>();
        moderadorComboBox = new ComboBox<>();
        usuarioComboBox.setPromptText("Selecione o Usuário");
        moderadorComboBox.setPromptText("Selecione o Moderador");

        usuarioComboBox.getItems().addAll(new Usuario(1, "Lucas"), new Usuario(2, "Abimael"));
        moderadorComboBox.getItems().addAll(new Moderador(1, "Gabriel"), new Moderador(2, "Marcel"));

        salvarBtn = new Button("Salvar");
        editarBtn = new Button("Editar");
        excluirBtn = new Button("Excluir");

        salvarBtn.setOnAction(e -> {
            if (validarEntrada()) salvarPostagem();
        });

        editarBtn.setOnAction(e -> preencherFormulario());
        excluirBtn.setOnAction(e -> excluirPostagem());

        // Tabela
        tabela = new TableView<>();
        tabela.setPrefHeight(200);
        criarColunasTabela();
        atualizarTabela();

        layout.getChildren().addAll(
                idField, nomeField, descricaoField, localField, corField,
                categoriaField, dataPicker, imagemField,
                usuarioComboBox, moderadorComboBox,
                new HBox(10, salvarBtn, editarBtn, excluirBtn),
                tabela
        );

        Scene scene = new Scene(layout, 600, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Postagem CRUD");
        primaryStage.show();
    }

    private void criarColunasTabela() {
        TableColumn<Postagem, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Postagem, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Postagem, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        TableColumn<Postagem, String> colDescricao = new TableColumn<>("Descricao");
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn<Postagem, String> colLocal = new TableColumn<>("Local");
        colLocal.setCellValueFactory(new PropertyValueFactory<>("local"));

        TableColumn<Postagem, String> colCor = new TableColumn<>("Cor");
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));

        TableColumn<Postagem, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));

        tabela.getColumns().addAll(colId, colNome, colCategoria, colDescricao, colLocal, colCor, colData);

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void atualizarTabela() {
        tabela.getItems().setAll(persistencia.listar());
    }

    private boolean validarEntrada() {
        StringBuilder erro = new StringBuilder();

        if (nomeField.getText().isEmpty()) erro.append("Nome obrigatório.\n");
        if (descricaoField.getText().isEmpty()) erro.append("Descrição obrigatória.\n");
        if (localField.getText().isEmpty()) erro.append("Local obrigatório.\n");
        if (corField.getText().isEmpty()) erro.append("Cor obrigatória.\n");
        if (categoriaField.getText().isEmpty()) erro.append("Categoria obrigatória.\n");
        if (dataPicker.getValue() == null) erro.append("Data obrigatória.\n");
        if (usuarioComboBox.getValue() == null) erro.append("Usuário obrigatório.\n");
        if (moderadorComboBox.getValue() == null) erro.append("Moderador obrigatório.\n");

        if (erro.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erros encontrados:");
            alert.setContentText(erro.toString());
            alert.showAndWait();
            return false;
        }

        return true;
    }

    private void salvarPostagem() {
        int id = idField.getText().isEmpty() ? persistencia.gerarId() : Integer.parseInt(idField.getText());
        Postagem postagem = new Postagem(
                id,
                nomeField.getText(),
                descricaoField.getText(),
                localField.getText(),
                corField.getText(),
                categoriaField.getText(),
                dataPicker.getValue(),
                LocalDateTime.now(),
                imagemField.getText(),
                1, // tipoUsuario
                usuarioComboBox.getValue(),
                moderadorComboBox.getValue()
        );

        persistencia.salvar(postagem);
        atualizarTabela();
        limparCampos();

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Sucesso");
        alerta.setContentText("Postagem salva com sucesso!");
        alerta.showAndWait();
    }

    private void preencherFormulario() {
        Postagem selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            idField.setText(String.valueOf(selecionada.getId()));
            nomeField.setText(selecionada.getNome());
            descricaoField.setText(selecionada.getDescricao());
            localField.setText(selecionada.getLocal());
            corField.setText(selecionada.getCor());
            categoriaField.setText(selecionada.getCategoria());
            dataPicker.setValue(selecionada.getData());
            imagemField.setText(selecionada.getImagem());
            usuarioComboBox.setValue(selecionada.getUsuario());
            moderadorComboBox.setValue(selecionada.getModerador());
        }
    }

    private void excluirPostagem() {
        Postagem selecionada = tabela.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            persistencia.remover(selecionada);
            atualizarTabela();
            limparCampos();
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Nenhuma seleção");
            alerta.setContentText("Selecione uma postagem para excluir.");
            alerta.showAndWait();
        }
    }

    private void limparCampos() {
        idField.clear();
        nomeField.clear();
        descricaoField.clear();
        localField.clear();
        corField.clear();
        categoriaField.clear();
        dataPicker.setValue(null);
        imagemField.clear();
        usuarioComboBox.setValue(null);
        moderadorComboBox.setValue(null);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
