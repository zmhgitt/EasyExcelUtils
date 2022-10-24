/**
 * 封装所有的关键字
 */
package com.zmh.generator.parsing;

public enum Token {


    GetVar("Z{"),

    RightCurly("}"),

    ;
    private String literal;
    Token(String literal){
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

    public boolean equal(Token token){
        return this.literal.equals(token.literal);
    }
}
