module com.example.projetosra3poo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projetosra3poo to javafx.fxml;
    exports com.example.projetosra3poo;

    opens projeto_gabriel.view to javafx.fxml;
    exports projeto_gabriel.view;
    exports projeto_gabriel.controller;
    exports projeto_gabriel.model;

    opens projeto_igor to javafx.fxml;
    exports projeto_igor;

    opens projeto_lucas to javafx.fxml;
    exports projeto_lucas;

    opens projeto_marcel to javafx.fxml;
    exports projeto_marcel;

}