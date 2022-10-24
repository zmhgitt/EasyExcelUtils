package com.zmh.generator.parsing;

import java.util.List;

/**
 * @author Miles
 * @date 2022/10/10 18:27
 *
 * 封装所有Token
 */
public class TokenStream {

    /**集合*/
    private List<TokenPosition> tokens;

    /**当前下标*/
    private Integer start;

    /**偏移量*/
    private Integer offset;


    public TokenStream(List<TokenPosition> tokens){
        this.start = 0;
        this.offset = start;
        this.tokens = tokens;
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

    /**
     * 是否还有元素
     * @return
     */
    public boolean hasMore(){
        return offset < tokens.size();
    }

    public void next(){
        offset++;
    }

    public Integer getStart() {
        return start;
    }

    public TokenPosition getCurToken(){
        return tokens.get(offset);
    }
    public TokenPosition getNextToken(){
        return tokens.get(++offset);
    }

    /**
     * 期望下一个是我所期待的token
     * @param token
     * @return
     */
    public boolean expect(String token){
        return tokens.get(offset + 1).getToken().getLiteral().equals(token);
    }
}
