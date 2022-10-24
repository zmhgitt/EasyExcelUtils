package com.zmh.generator.parsing;


import java.util.ArrayList;
import java.util.List;

import static com.zmh.generator.parsing.Token.*;

/**
 * @author Miles
 * @date 2022/10/11 12:08
 */
public class Parsing {

    /**
     * 令牌
     */
    private List<TokenPosition> baseTokens;

    private List<Span> spans;


    private Token[] baseTokenAll = new Token[]{
            GetVar, RightCurly
    };

    public Parsing(List<Span> spans) {
        this.spans = spans;
    }

    /**
     * 检索Span中的关键字，按先后顺序添加
     */
    public void parseBaseTokens() {
        parseBaseTokens(this.baseTokenAll);
    }
    /**
     * 检索Span中的关键字，按先后顺序添加
     */
    public void parseBaseTokens(Token[] baseTokenAll) {
        this.baseTokens = new ArrayList<>(this.spans.size());
        SpanStream spanStream = new SpanStream();

        for (int i = 0; i < this.spans.size(); i++) {
            Span span = this.spans.get(i);
            spanStream.setSpan(span);
            while (spanStream.hasMore()) {
                for (Token token : baseTokenAll) {
                    if (spanStream.startWith(token)) {
                        this.baseTokens.add(new TokenPosition(token, spanStream.getStart()-token.getLiteral().length(),i));
                        break;
                    }
                }
                spanStream.next();
            }
        }
    }



    public List<TokenPosition> getBaseTokens() {
        return baseTokens;
    }

    public List<Span> getSpans() {
        return spans;
    }
}
