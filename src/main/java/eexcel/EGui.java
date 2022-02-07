package eexcel;

import eexcel.bean.ExcelLineBean;
import eexcel.util.ExcelUtil;
import eexcel.util.MusicUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class EGui extends JFrame {
    private Map<String, ExcelLineBean> degreeToExcelLineBeanMap = new HashMap<>();
    private JButton queryButton;

    private JLabel frequencyJLabel;
    private JTextField frequencyText;

    private JLabel degreeJLabel;
    private JTextField degreeText;

    private JLabel noteJLabel;
    private JTextField noteText;

    private JLabel diffFrequencyJLabel;
    private JTextField diffText;

    private JLabel realAudioInterpolationOscillatorJLabel;
    private JTextField realAudioInterpolationOscillator;

    private JLabel diffAudioInterpolationOscillatorJLabel;
    private JTextField diffAudioInterpolationOscillator;

    private Font font = new Font("宋体", Font.BOLD, 25);

    public EGui() throws HeadlessException {
        super("MUSIC");
        initDegreeToExcelLineBeanMap();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,400);
        setVisible(true);

        setLayout(new GridLayout(8,4));

        frequencyJLabel = new JLabel("        实际频率:");
        frequencyJLabel.setFont(font);
        add(frequencyJLabel);

        frequencyText = new JTextField(200);
        frequencyText.setFont(font);
        add(frequencyText);

        degreeJLabel = new JLabel("        八度:");
        degreeJLabel.setFont(font);
        add(degreeJLabel);

        degreeText = new JTextField(200);
        degreeText.setFont(font);
        add(degreeText,BorderLayout.CENTER);

        noteJLabel = new JLabel("        音符:");
        noteJLabel.setFont(font);
        add(noteJLabel);

        noteText = new JTextField(200);
        noteText.setFont(font);
        add(noteText);

        diffFrequencyJLabel = new JLabel("        频率差:");
        diffFrequencyJLabel.setFont(font);
        add(diffFrequencyJLabel);

        diffText = new JTextField(200);
        diffText.setFont(font);
        add(diffText);

        realAudioInterpolationOscillatorJLabel = new JLabel("        实际音分:");
        realAudioInterpolationOscillatorJLabel.setFont(font);
        add(realAudioInterpolationOscillatorJLabel);

        realAudioInterpolationOscillator = new JTextField(200);
        realAudioInterpolationOscillator.setFont(font);
        add(realAudioInterpolationOscillator);

        diffAudioInterpolationOscillatorJLabel = new JLabel("        音分差:");
        diffAudioInterpolationOscillatorJLabel.setFont(font);
        add(diffAudioInterpolationOscillatorJLabel);

        diffAudioInterpolationOscillator = new JTextField(200);
        diffAudioInterpolationOscillator.setFont(font);
        add(diffAudioInterpolationOscillator);

        queryButton = new JButton("查询");
        this.add(queryButton);

        queryButton.addActionListener(new ButtonListener());
    }
    class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            double inputFrequency = Double.parseDouble(frequencyText.getText());

            String resultDegree=null;
            String resultNote=null;
            double resultFrequency=0.00;
            double resultDiff=Double.MAX_EXPONENT;
            try {
                for(Map.Entry<String, ExcelLineBean> degreeToExcelLineBean:degreeToExcelLineBeanMap.entrySet()){
                    String currentDegree = degreeToExcelLineBean.getKey();
                    Map<String,Double> noteToFrequencyMap = degreeToExcelLineBean.getValue().getNoteToFrequencyMap();
                    for(Map.Entry<String,Double> noteToFrequency:noteToFrequencyMap.entrySet()){
                        String currentNote = noteToFrequency.getKey();
                        double currentFrequency = noteToFrequency.getValue();
                        if(null==resultNote){
                            resultDegree = currentDegree;
                            resultNote = currentNote;
                            resultFrequency = currentFrequency;
                            System.out.println("1 输入频率="+inputFrequency+";八度="+resultDegree+";音符="+ resultNote+";频率="+resultFrequency+";差值="+resultDiff);

                        }else {
                            double currentDiff = MusicUtil.getDiff(inputFrequency,currentFrequency);
                            if(resultDiff > currentDiff){
                                resultDiff = currentDiff;
                                resultDegree = currentDegree;
                                resultNote = currentNote;
                                resultFrequency = currentFrequency;
                                System.out.println("交换 输入频率="+inputFrequency+";八度="+resultDegree+";音符="+ resultNote+";频率="+resultFrequency+";差值="+currentDiff);
                            }
                        }
                    }
                }
                System.out.println("输入频率="+inputFrequency+";八度="+resultDegree+";音符="+ resultNote+";频率="+resultFrequency+";差值="+resultDiff);
                degreeText.setText(resultDegree);
                noteText.setText(resultNote);
                diffText.setText(Double.toString(resultDiff));

                double standardAudioInterpolationOscillatorDouble = MusicUtil.audioInterpolationOscillator(resultFrequency);
                double realAudioInterpolationOscillatorDouble = MusicUtil.audioInterpolationOscillator(inputFrequency);
                realAudioInterpolationOscillator.setText(Double.toString(realAudioInterpolationOscillatorDouble));
                double diff = (realAudioInterpolationOscillatorDouble-standardAudioInterpolationOscillatorDouble)*100;
                diffAudioInterpolationOscillator.setText(Double.toString(diff));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void initDegreeToExcelLineBeanMap() {
        degreeToExcelLineBeanMap= ExcelUtil.readDegreeToExcelLineBeanMap("ABC.xlsx");
    }

    private double getStandardFrequency(String degree,String note) throws Exception {
        try {
            return degreeToExcelLineBeanMap.get(degree.trim()).getFrequencyMap(note.trim());
        }catch (Exception e){
            throw new Exception(e.getMessage()+",CAN NOT FOUND THE DEGREE="+degree+",NOTE="+note,e);
        }
    }
}
