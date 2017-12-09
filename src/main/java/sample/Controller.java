package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.apache.log4j.Logger;

public class Controller {

    private final static Logger logger = Logger.getLogger(Controller.class);

    @FXML
    private ImageView imageView;

    @FXML
    public void initialize() {
        imageView.setImage(new Image("pic/2.jpeg"));
    }

    @FXML
    public void onClick() {
        logger.info("sound check !");
    }

}
