package FilterHumanVoice;

import exception.WavFileException;
import util.AudioAnalysisBaseClass;
import util.WavFile;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.sound.sampled.*;

public class BoostDialogue extends AudioAnalysisBaseClass {

    static int numberOfFrames;



    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
            boostVolume(new File("/Users/achaudhari/Downloads/AudioAnalysis/Boost_dialogue.wav"));
//        try{
//            WavFile wavFile = WavFile.openWavFile(new File("/Users/achaudhari/Downloads/AudioAnalysis/Boost_dialogue.wav"));
//
//            readFile(wavFile);
//
//            totalFramesData = wavFile.getFrameData();
//
//            allFramesArray = new double[wavFile.getNumChannels()][(int)wavFile.getNumFrames()];
//
//            convertListTo2DArray();
//
//            amplifyHumanVoice();
    }

    public static void readFile(WavFile wavFile) throws IOException, WavFileException{
        wavFile.display();
        double[] buffer = new double[(int) (wavFile.getNumFrames() * wavFile.getNumChannels())];
        numberOfFrames = (int) wavFile.getNumFrames();
        wavFile.readFrames(buffer,numberOfFrames);
    }

    public static void amplifyHumanVoice() {
                linearToDecibel(allFramesArray[0]);
                System.out.println("FFTPrinceton output array length is" + Arrays.toString(decibelArray));
    }


    public static double[] linearToDecibel (double[] linearArray) {
        decibelArray = new double[linearArray.length];

        for (int i =0 ;i< linearArray.length; i++) {
            decibelArray[i] = 20.0 * Math.log10(linearArray[i]);
        }
        return decibelArray;
    }

    public static void boostVolume (File wavFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(wavFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(1.0f);
        clip.start();
        Thread.sleep(10000);
    }

}
