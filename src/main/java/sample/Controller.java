package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.log4j.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Controller {

    private static int counter = 0;
    private static int imageNumberCounter = 0;

    private static boolean editableMode = false;

    private final static Logger logger = Logger.getLogger(Controller.class);

    private static Map<String, ImageCoordinates> coordinates = new HashMap<>();

    private ImageCoordinates c;

    class ImageCoordinates {
        private double x1;
        private double y1;
        private double x2;
        private double y2;

        public double getX1() {
            return x1;
        }

        public void setX1(double x1) {
            this.x1 = x1;
        }

        public double getY1() {
            return y1;
        }

        public void setY1(double y1) {
            this.y1 = y1;
        }

        public double getX2() {
            return x2;
        }

        public void setX2(double x2) {
            this.x2 = x2;
        }

        public double getY2() {
            return y2;
        }

        public void setY2(double y2) {
            this.y2 = y2;
        }

        @Override
        public String toString() {
            return "ImageCoordinates{" +
                    "x1=" + x1 +
                    ", y1=" + y1 +
                    ", x2=" + x2 +
                    ", y2=" + y2 +
                    '}';
        }
    }

    @FXML
    private ImageView imageView;

    @FXML
    private Button editButton;

    @FXML
    public void initialize() {

        imageView.setImage(new Image("pic/2.jpeg"));

        editButton.setOnMouseClicked(e -> {
            if (!editableMode) {
                editButton.setText("CloseEDIT");
                editableMode = true;
            } else {
                editableMode = false;
                editButton.setText("EDIT");
            }
        });


        imageView.setOnMouseClicked(e -> {

            if (editableMode) {
                if (counter % 2 == 0) {
                    //left top corner
                    c = new ImageCoordinates();
                    c.setX1(e.getX());
                    c.setY1(e.getY());
                    counter++;
                    System.out.println("left -> " + "["+e.getX()+", "+e.getY()+"]");
                } else {
                    imageNumberCounter++;
                    //right bottom
                    c.setX2(e.getX());
                    c.setY2(e.getY());
                    String keyName = "beyatiSplit" + imageNumberCounter + ".wav";
                    coordinates.put(keyName, c);
                    counter++;
                    System.out.println("right -> " + "["+e.getX()+", "+e.getY()+"]" );
                }


                System.out.println("\n\n coordinates -> " + coordinates.toString() + "\n\n");

            } else {
                String fileName = findImageNameFromPosition(e.getX(), e.getY());
                System.out.println("names -> " + fileName);
                if (!fileName.isEmpty()) {
                    try {
                        String filePath = "src/main/resources/music/split/" + fileName;
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                                new File(filePath));
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.loop(5);
                        clip.start();
                    } catch(Exception ex) {
                        System.out.println("Error with playing sound.");
                        ex.printStackTrace();
                    }
                }


            }

        });
    }

    /**
     * returns wav file name to be called and played
     * @param x
     * @param y
     * @return
     */
    public String findImageNameFromPosition(double x, double y) {
        Iterator it = coordinates.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            ImageCoordinates coordinateXY = (ImageCoordinates) pair.getValue();
            if (x > coordinateXY.getX1() && x < coordinateXY.getX2() &&
                    y > coordinateXY.getY1() && y < coordinateXY.getY2() ) {
                //falls into that section
                System.out.println("in section -> " + pair.getKey());

                return (String) pair.getKey();
            }
            System.out.println(pair.getKey() + " = " + pair.getValue());
        }
        return "";
    }

}
