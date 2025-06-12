module com.example.projetosra3poo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.projetosra3poo to javafx.fxml;
    exports com.example.projetosra3poo;

    opens projetoRA3 to javafx.graphics;
    exports projetoRA3;

    opens projeto_gabriel.controller to javafx.fxml;
    opens projeto_gabriel.view to javafx.fxml;
    opens projeto_gabriel.model to javafx.fxml;
    exports projeto_gabriel.controller;
    exports projeto_gabriel.model;
    exports projeto_gabriel.view;

    opens projeto_igor to javafx.fxml;
    exports projeto_igor;

    opens projeto_lucas to javafx.fxml;
    exports projeto_lucas;
}
