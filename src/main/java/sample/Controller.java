package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class Controller {

    private final static Logger logger = Logger.getLogger(Controller.class);

    @FXML
    private ImageView imageView;

    @FXML
    public void initialize() {
        imageView.setImage(new Image("pic/2.jpeg"));
    }

    @FXML
    public void onClick() throws URISyntaxException {
        logger.info("sound check !");

        URI uri = getClass().getClassLoader().getResource("music/3hane.mp3").toURI();
        Media media = new Media(new File(uri).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }

}
