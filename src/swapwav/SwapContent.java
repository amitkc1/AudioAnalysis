package swapwav;

import util.AudioAnalysisBaseClass;
import util.SwapContentUtil;
import util.WavFile;
import java.io.File;
import java.util.Collections;


public class SwapContent extends AudioAnalysisBaseClass{

    public static void main(String[] args) {
        try{
            WavFile wavFile = WavFile.openWavFile(new File("/Users/achaudhari/Downloads/AudioAnalysis/ChillingMusic.wav"));

            // Display information about the wav file
            wavFile.display();

            // Get the number of audio channels in the wav file
            int numChannels = wavFile.getNumChannels();

            int numFrames = (int) wavFile.getNumFrames();

            // Create a buffer of numberOfFrames*channels
            double[] buffer = new double[numFrames * numChannels];

            int numberOfFrames = wavFile.readFrames(buffer,numFrames);

            allFramesArray = new double[numChannels][numberOfFrames];

            threeSecondsFrameCount = 44100 * numChannels * 3;

            swapWavFileContent(wavFile);

            System.out.println("List size after swapping "+ totalFramesData.size());
            convertListTo2DArray();
            SwapContentUtil.writeWavFile(allFramesArray);

        }catch(Exception e){

            e.printStackTrace();

        }
    }

    public static void getFirstThreeSecondsFrames() {
        for (int frameCounter = 0 ; frameCounter < threeSecondsFrameCount;frameCounter++) {
            firstThreeSecondsFrameData.add(totalFramesData.get(frameCounter));
        }
    }

    public static void getLastThreeSecondsFrames() {
        for (int frameCounter = totalFramesData.size() -1; frameCounter >= (totalFramesData.size()-threeSecondsFrameCount); frameCounter--) {
            lastThreeSecondsFrameData.add(totalFramesData.get(frameCounter));
        }
        Collections.reverse(lastThreeSecondsFrameData);
    }

    private static void swapWavFileContent(WavFile wavFile) {
        totalFramesData = wavFile.getFrameData();

        getFirstThreeSecondsFrames();

        getLastThreeSecondsFrames();

        totalFramesData.subList(0,threeSecondsFrameCount).clear();
        totalFramesData.subList((totalFramesData.size()-threeSecondsFrameCount), totalFramesData.size()).clear();

        lastThreeSecondsFrameData.addAll(totalFramesData);
        lastThreeSecondsFrameData.addAll(firstThreeSecondsFrameData);
        totalFramesData.clear();
        totalFramesData = lastThreeSecondsFrameData;
    }


}
