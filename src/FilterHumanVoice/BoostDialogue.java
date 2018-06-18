package FilterHumanVoice;

import edu.princeton.cs.algorithms.Complex;
import exception.WavFileException;
import util.AudioAnalysisBaseClass;
import util.FFTUtils.FFTPrinceton;
import util.WavFile;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class BoostDialogue extends AudioAnalysisBaseClass {

    static int numberOfFrames;
    static int dbGain =1;


    public static void main(String[] args) {
        try{
            WavFile wavFile = WavFile.openWavFile(new File("/Users/achaudhari/Downloads/AudioAnalysis/Boost_dialogue.wav"));
            readFile(wavFile);
            totalFramesData = wavFile.getFrameData();
            allFramesArray = new double[wavFile.getNumChannels()][(int)wavFile.getNumFrames()];
            convertListTo2DArray();
            amplifyHumanVoice();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFile(WavFile wavFile) throws IOException, WavFileException{
        wavFile.display();
        double[] buffer = new double[(int) (wavFile.getNumFrames() * wavFile.getNumChannels())];
        numberOfFrames = (int) wavFile.getNumFrames();
        wavFile.readFrames(buffer,numberOfFrames);

        //wavFile.getFrameData().forEach(element -> System.out.println(element));
    }

    public static void amplifyHumanVoice() {
                convertToComplexArray(allFramesArray[0]);
//                FFTByKircher.fft(allFramesArray[0]);
//                convertDoubleToFloat(allFramesArray[0]);
//                FFT tarsosFFT = new FFT(allFramesArray.length,null);
//                tarsosFFT.forwardTransform((floatArray));
        fftOutputArray= FFTPrinceton.fft(fftInputArray);

        System.out.println(Arrays.toString(fftOutputArray));

        //SilenceDetector silenceDetector = new SilenceDetector();
       // silenceDetector.
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

    public static Complex[] convertToComplexArray(double[] inputArray) {
        fftInputArray = new Complex[inputArray.length];
        fftOutputArray = new Complex[inputArray.length/2];
        for (int i=0;i< inputArray.length;i++) {
            fftInputArray[i] = new Complex(inputArray[i],0);
        }
        return fftInputArray;
    }

}
