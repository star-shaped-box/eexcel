package eexcel.bean;

import java.util.Map;

public class ExcelLineBean {
    public ExcelLineBean(String degree, Map<String, Double> noteToFrequencyMap) {
        this.degree = degree;
        this.noteToFrequencyMap = noteToFrequencyMap;
    }

    private final String degree;
    private final Map<String,Double> noteToFrequencyMap;

    public String getDegree() {
        return degree;
    }

    public Double getFrequencyMap(String note) {
        return noteToFrequencyMap.get(note);
    }

    public Map<String, Double> getNoteToFrequencyMap() {
        return noteToFrequencyMap;
    }

    @Override
    public String toString() {
        return "ExcelLineBean{" +
                "degree='" + degree + '\'' +
                ", noteToFrequencyMap=" + noteToFrequencyMap +
                '}';
    }
}
