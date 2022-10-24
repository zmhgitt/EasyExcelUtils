package com.zmh.generator.parsing;

import java.util.List;

/**
 * @author Miles
 * @date 2022/10/11 12:01
 */
public class SpanStream {

    private Span span;

    private int start;

    private int end;

    public void setSpan(Span span) {
        this.span = span;
        this.start = 0;
        this.end = this.span.getText().length();
    }

    public boolean hasMore(){
        return start < end;
    }
    public void next(){
        start++;
    }

    public boolean startWith(Token token){
        boolean result = span.getText().substring(start).startsWith(token.getLiteral());
        if (result){
            start += token.getLiteral().length();
        }
        return result;
    }

    public boolean match(Token token){
        return span.getText().equals(token.getLiteral());
    }

    public int getStart() {
        return start;
    }
}
