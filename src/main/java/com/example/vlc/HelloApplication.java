package com.example.vlc;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloApplication extends Application {
    //Declaring the sliders and buttons
    Slider volume = new Slider();
    Slider duration = new Slider();


    @Override
    public void start(Stage stage) throws IOException {


            VBox root = new VBox();
            //Inserting video to play on the mediaView
            String file = getClass().getResource("/video.mp4").toExternalForm();
            Media media = new Media(file);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);

            String fl = getClass().getResource("/mute.png").toExternalForm();
            ImageView vol = new ImageView(new Image(fl));
            vol.setFitWidth(30);
            vol.setFitHeight(30);

            String stp = getClass().getResource("/stop-button.png").toExternalForm();
            ImageView Stopbtn = new ImageView(new Image(stp));
            Stopbtn.setFitWidth(50);
            Stopbtn.setFitHeight(50);


        //Play and Pause Button
            String src = getClass().getResource("/play-button.png").toExternalForm();
            ImageView PlayPause = new ImageView(new Image(src));
            PlayPause.setFitHeight(50);
            PlayPause.setFitWidth(50);

            String src1 = getClass().getResource("/pause-button.png").toExternalForm();
            ImageView PlayPause1 = new ImageView(new Image(src1));
            PlayPause1.setFitHeight(50);
            PlayPause1.setFitWidth(50);

            //setting the mediaview
            mediaView.setFitWidth(900);
            mediaView.setFitHeight(800);

            //stopbtn
            Stopbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaPlayer.stop();
                    PlayPause.setImage(new Image(src));
                }
            });


            //Play and Pause Button on one button
            PlayPause.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mediaPlayer.getStatus()==MediaPlayer.Status.PLAYING){
                    mediaPlayer.pause();
                    //switching the playmode when pressing the play button
                    PlayPause.setImage(new Image(src));
                }else{
                    mediaPlayer.play();
                    PlayPause.setImage(new Image(src1));
                }
            }

        });


            //Volume Slider
            volume.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    volume.setValue(mediaPlayer.getVolume()*100);
                    volume.valueProperty().addListener(new InvalidationListener() {
                        @Override
                        public void invalidated(Observable observable) {
                            mediaPlayer.setVolume(volume.getValue()/100);
                            //checking the value of the volume to provide appropriate icon
                            if(mediaPlayer.getVolume() != 0){
                                String img = getClass().getResource("/audio.png").toExternalForm();
                                Image image = new Image(img);
                                vol.setImage(image);
                            }else{
                                String img = getClass().getResource("/mute.png").toExternalForm();
                                Image image = new Image(img);
                                vol.setImage(image);
                            }
                        }
                    });
                }
            });

            //Duration Slider Properties
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration duration1, Duration t1) {
                    duration.setValue(t1.toSeconds());
                }
            });
            duration.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaPlayer.seek(Duration.seconds(duration.getValue()));
                }
            });

            duration.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaPlayer.seek(Duration.seconds(duration.getValue()));
                }
            });

            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    Duration total = media.getDuration();
                    duration.setMax(total.toSeconds());
                }
            });
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    PlayPause.setImage(new Image(src));
                    mediaPlayer.stop();
                    duration.setValue(0);
                }
            });


            //Layouts
            VBox vBox = new VBox();
            HBox hBox = new HBox(20);
            HBox hBox2 = new HBox(10);
            VBox vBox1 = new VBox(15);

            //setting the position of the vBox on the Scene
            vBox.setAlignment(Pos.CENTER);
            hBox.setAlignment(Pos.BOTTOM_CENTER);
            hBox2.setAlignment(Pos.BOTTOM_LEFT);

            hBox2.getChildren().addAll(vol,volume);
            hBox2.setPadding(new Insets(0,0,50,200));
            hBox.setPadding(new Insets(20 ,5 ,0 ,0));


            //setting ids for my elements
            volume.setId("playee");
            vol.setId("icon");
            duration.setId("playtime");
            PlayPause.setId("btnplay");

            //adding children to the layouts
            vBox.getChildren().addAll(mediaView,duration,hBox2);
            hBox.getChildren().addAll(PlayPause,Stopbtn);
            root.getChildren().addAll(vBox,hBox,hBox2);

            Scene scene = new Scene(root, 320, 240);


            //styling the Scene using CSS
            scene.getStylesheets().add(getClass().getResource("/vlc.css").toExternalForm());
            Image icon = new Image(getClass().getResource("/vlc.jpg").toExternalForm());
            //setting the tittle of the MediaPlayer
            stage.setTitle("VLC");
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}