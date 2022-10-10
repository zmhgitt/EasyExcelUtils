package com.zmh.generator.parsing;

/**
 * @author Miles
 * @date 2022/10/10 18:30
 */
public class SourcePosition {

    private int start;

    private int end;
    private String value;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int length(){
        return end - start;
    }
}
