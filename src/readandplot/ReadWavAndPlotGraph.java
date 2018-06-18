package readandplot;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.math.plot.Plot2DPanel;
import util.WavFile;
import javax.swing.*;

public class ReadWavAndPlotGraph
{

    private static double[][] allFramesArray;
    private static List<Double> totalFramesData = new ArrayList<>();
    private static int FRAME_WIDTH = 600;
    private static int FRAME_HEIGHT = 600;
    private static double time[];
    private static int numFrames;
    private static long sampleRate;


    public static void main(String[] args)
    {
        try
        {
            // Open the wav file specified as the first argument
            WavFile wavFile = WavFile.openWavFile(new File("/Users/achaudhari/Downloads/AudioAnalysis/ChillingMusic.wav"));

            // Display information about the wav file
            wavFile.display();

            // Get the number of audio channels in the wav file
            int numChannels = wavFile.getNumChannels();

            sampleRate = wavFile.getSampleRate();

            numFrames = (int) wavFile.getNumFrames();

            // Create a buffer of 100 frames
            double[] buffer = new double[(int) (wavFile.getNumFrames() * numChannels)];

            wavFile.readFrames(buffer,numFrames);
            allFramesArray = new double[numChannels][numFrames];
           // wavFile.getFrameData().stream().forEach(element -> System.out.println(element));

            totalFramesData = wavFile.getFrameData();
            convertListTo2DArray();
            plot2dWavGraph();
            wavFile.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void convertListTo2DArray() {

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
    }

    public static void plot2dWavGraph() {
        JFrame frame = new JFrame("Chilling music Sound Plot");
        frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        frame.setLayout(new BorderLayout());

        Plot2DPanel plot = new Plot2DPanel();
        getTimeAxisArray();
        plot.addLinePlot("Channel 1",time,allFramesArray[0]);
        plot.addLinePlot("Channel 2",time,allFramesArray[1]);
        plot.addLegend("South");
        plot.setAxisLabels("Time","Amplitude");

        frame.add(plot);
        frame.setContentPane(plot);
        frame.setVisible(true);
    }

    public static void getTimeAxisArray() {
        time = new double[numFrames];

        for(int i=1; i<=time.length;i++) {
            time[i-1]= (1.0/sampleRate) * i;
        }
    }
}