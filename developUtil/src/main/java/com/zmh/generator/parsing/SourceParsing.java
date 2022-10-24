package com.zmh.generator.parsing;

import java.util.List;

/**
 * @author Miles
 * @date 2022/10/11 10:35
 */
public class SourceParsing {

    /**元字符*/
    private String source;

    private int start;

    private int end;

    private int offset;//不是单纯的偏移量，而是已经对start进行了加法

    public SourceParsing(String source){
        this.source = source;
        this.start = 0;
        this.offset = 0;
        this.end = source.length();
    }

    /**重置偏移量*/
    public void resetOffset(){
        offset = start;
    }

    /**
     * 重置start   start = offset
     */
    public void resetStart(){
        this.start = this.offset;
    }

    public void reset(){
        this.start = 0;
    }

    /**
     * 转化为span
     * @param spans
     * @return
     */
    public void parseSpan(List<Span> spans){
        while (start < end){
            findWhiteSpace();
            if (offset > start){
                spans.add(new Span(source,start,offset));
            }
            skipWhiteSpace();
            resetStart();
        }
    }

    /**
     * 找到空白字符
     */
    public void findWhiteSpace() {
        while (offset < end) {
            char c = source.charAt(offset);
            if (c == ' ' || c == '\n' || c == '\r' || c == '\t') {
                break;
            }
            offset++;
        }
    }

    /**
     * 跳过空白字符
     */
    public void skipWhiteSpace() {
        while (offset < end) {
            char c = source.charAt(offset);
            if (c == ' ' || c == '\n' || c == '\r' || c == '\t') {
                offset++;
            } else {
                break;
            }
        }
    }
}
