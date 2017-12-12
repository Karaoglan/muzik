package sample;

import org.apache.log4j.Logger;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SplitExample {

    private final static Logger logger = Logger.getLogger(SplitExample.class);

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException {

        int startTimeinMs = 10000;
        int endTimeinMs = 15000;

        File soundFile = new File("src/main/resources/music/3hane.wav");
        AudioInputStream originalAudioInputStream = AudioSystem.getAudioInputStream(soundFile);
        AudioFormat audioFormat = originalAudioInputStream.getFormat();

        float startInBytes = (startTimeinMs / 1000 * audioFormat.getSampleRate() * audioFormat.getFrameSize());
        float lengthInFrames = ((endTimeinMs - startTimeinMs) / 1000 * audioFormat.getSampleRate());

        originalAudioInputStream.skip((long) startInBytes);
        AudioInputStream partAudioInputStream = new AudioInputStream(originalAudioInputStream,
                originalAudioInputStream.getFormat(), (long) lengthInFrames);

        AudioSystem.write(partAudioInputStream, AudioFileFormat.Type.WAVE, new File
                ("src/main/resources/music/split/beyatiSplit2.wav"));

    }
}


