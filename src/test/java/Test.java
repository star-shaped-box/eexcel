public class Test {
    public static void main(String[] args) {
        //Log10($param/27.5)*12/log10(2)+1
        double frequency = 398.44;
        double result = Math.log10(frequency/27.5)*12/Math.log10(2)+1;
        System.out.println(result);
    }
}
