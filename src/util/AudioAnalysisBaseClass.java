package util;

import edu.princeton.cs.algorithms.Complex;

import java.util.ArrayList;
import java.util.List;

public class AudioAnalysisBaseClass {

    protected static List<Double> firstThreeSecondsFrameData = new ArrayList<Double>();
    protected static List<Double> lastThreeSecondsFrameData = new ArrayList<Double>();
    protected static List<Double> totalFramesData = new ArrayList<Double>();
    protected static int threeSecondsFrameCount = 0;
    protected static double[][] allFramesArray;
    protected static Complex[] fftInputArray;
    protected static Complex[] fftOutputArray;
    protected static float[] floatArray;
    protected static double[] decibelArray;

    public static double[][] convertListTo2DArray() {

        int arrayCounter = 0;

        for(int frameCounter = 0; frameCounter < totalFramesData.size(); frameCounter ++) {

            if(frameCounter % 2 ==0) {
                allFramesArray[0] [arrayCounter] = totalFramesData.get(frameCounter);
            }

            else {
                allFramesArray [1] [arrayCounter] = totalFramesData.get(frameCounter);
                arrayCounter++;
            }
        }
        return allFramesArray;
    }

    public static float[] convertDoubleToFloat(double[] doubleArray) {
        floatArray = new float[doubleArray.length];

        for (int i = 0; i< floatArray.length; i++){
            floatArray[i] = (float) doubleArray[i];
        }
        return floatArray;
    }
}
