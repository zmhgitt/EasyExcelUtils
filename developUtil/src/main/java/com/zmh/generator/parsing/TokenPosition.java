package com.zmh.generator.parsing;

/**
 * @author Miles
 * @date 2022/10/11 12:13
 */
public class TokenPosition {

    private Token token;

    private int start;

    private int spanIndex;

    public TokenPosition(Token token,int start,int spanIndex){
        this.token = token;
        this.start = start;
        this.spanIndex = spanIndex;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getSpanIndex() {
        return spanIndex;
    }
}
