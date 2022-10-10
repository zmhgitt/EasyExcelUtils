/**
 * 封装所有的关键字
 */
package com.zmh.generator.parsing;

public enum TokenType {


    GetVar("Z{"),

    RightCurly("}"),

    ;
    private String literal;
    TokenType(String literal){
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }
}
