package sample;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class SplitExample extends AudioInputStream {
    private final AudioInputStream stream;
    private final long startByte,endByte;
    private long t_bytesRead=0;

    private SplitExample(AudioFormat audioFormat,AudioInputStream audioInputStream,long startMilli,long endMilli){
        super(new ByteArrayInputStream(new byte[0]),audioFormat,AudioSystem.NOT_SPECIFIED);
        stream=audioInputStream;
        //calculate where to start and where to end
        startByte=(long)((startMilli/1000)*stream.getFormat().getFrameRate()*stream.getFormat().getFrameSize());
        endByte=(long)((endMilli/1000)*stream.getFormat().getFrameRate()*stream.getFormat().getFrameSize());
    }

    @Override
    public int available() throws IOException{
        return (int)(endByte-startByte-t_bytesRead);
    }
    public int read(byte[] abData,int nOffset,int nLength) throws IOException{
        int bytesRead;
        if(t_bytesRead<startByte){
            do{
                bytesRead=(int)skip(startByte-t_bytesRead);
                t_bytesRead+=bytesRead;
            }while(t_bytesRead<startByte);
        }
        if(t_bytesRead>=endByte)//end reached. signal EOF
            return -1;

        bytesRead=stream.read(abData,0,nLength);
        if(bytesRead==-1)
            return -1;
        else if(bytesRead==0)
            return 0;

        t_bytesRead+=bytesRead;
        if(t_bytesRead>=endByte)// "trim" the extra by altering the number of bytes read
            bytesRead=(int)(bytesRead-(t_bytesRead-endByte));

        return bytesRead;
    }

    //private final static

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException{
        AudioInputStream music;
        music = AudioSystem.getAudioInputStream(new File("src/main/resources/music/3hane.wav"));
        music = new SplitExample(music.getFormat(),music,5000,10000);
        AudioSystem.write(music,AudioFileFormat.Type.WAVE,new File("src/main/resources/music/beyatiSplit.wav"));
    }
}


