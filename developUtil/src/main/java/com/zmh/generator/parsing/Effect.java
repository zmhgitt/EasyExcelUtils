package com.zmh.generator.parsing;

/**
 * @author Miles
 * @date 2022/10/11 18:19
 */
public class Effect {

    private int start;

    private int end;

    private String result;

    public int offset(int offset){
        return offset + ( start - end ) + result.length();
    }
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
