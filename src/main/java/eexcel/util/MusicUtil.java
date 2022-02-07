package eexcel.util;

public class MusicUtil {
    public static double getDiff(double frequencyA,double frequencyB){
        return Math.abs(frequencyA-frequencyB);
    }

    public static double audioInterpolationOscillator(double frequency){
        return Math.log10(frequency/27.5)*12/Math.log10(2)+1;
    }
}
