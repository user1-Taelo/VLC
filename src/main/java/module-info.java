module com.example.vlc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.vlc to javafx.fxml;
    exports com.example.vlc;
}