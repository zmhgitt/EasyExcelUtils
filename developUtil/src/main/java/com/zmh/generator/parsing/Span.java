package com.zmh.generator.parsing;

/**
 * @author Miles
 * @date 2022/10/11 10:36
 *
 *  封装片段
 */
public class Span {

    private String source;

    private Integer start;

    private Integer end;

    public Span(String source,Integer start,Integer end){
        this.source = source;
        this.start = start;
        this.end = end;
    }

    public String getText(){
        return source.substring(start,end);
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }
}
