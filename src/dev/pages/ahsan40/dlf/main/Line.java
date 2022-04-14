package dev.pages.ahsan40.dlf.main;

import java.util.ArrayList;

public class Line {
    private ArrayList<Integer> lines;
    private String text;

    public Line(int line, String text) {
        this.lines = new ArrayList<>();
        this.lines.add(line);
        this.text = text;
    }

    public String getLines() {
        return lines.toString();
    }

    public ArrayList<Integer> getAllLine() {
        return lines;
    }

    public void setAllLines(ArrayList<Integer> lines) {
        this.lines = lines;
    }

    public void addLine(int line) {
        this.lines.add(line);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCopies() {
        return lines.size();
    }
}
