package com.zmh.generator.parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Miles
 * @date 2022/10/11 17:53
 */
public class Compiler {

    private Parsing parsing;

    /**
     * 全局变量
     */
    private HashMap<String,Object> globalVar;

    /**
     * 临时变量
     */
    private HashMap<String,Object> tempVar;

    /**
     * 模板需要替换字符集合
     * 必须按照先后顺序来，可以给他排序，按Effect.start从小到大
     * */
    private List<Effect> effects;

    public Compiler(Parsing parsing){
        this.parsing = parsing;
        this.globalVar = Param.newInc();
        this.tempVar = Param.newInc();
        this.effects = new ArrayList<>(100);
    }

    public void Compile(){
        //获取Source源文件中所有token
        parsing.parseBaseTokens();
        List<TokenPosition> baseTokens = parsing.getBaseTokens();
        //执行过程中一定会有被忽视的token（废文本中也可能出现token，但其不构成执行条件），将其记录下来
        List<TokenPosition> ignoreTokens = new ArrayList<>(50);

        //遍历token
        TokenStream tokenStream = new TokenStream(baseTokens);
        while(tokenStream.hasMore()){
            TokenPosition baseToken = tokenStream.getCurToken();
            boolean flag = false;
            switch (baseToken.getToken()){
                case GetVar:{
                    flag = getVars(tokenStream);
                    break;
                }
            }
            if (!flag){
                ignoreTokens.add(baseToken);
            }
            tokenStream.next();
        }
    }

    /**
     * 解析最基础的  类似Z{}文本替换
     * @param tokenStream
     */
    private boolean getVars(TokenStream tokenStream){
        //匹配Z{成功 则期望下一个是 }
        if (tokenStream.expect(Token.RightCurly.getLiteral())){
            TokenPosition curToken = tokenStream.getCurToken();
            TokenPosition expect = tokenStream.getNextToken();
            //这个语句只能出现在一个span中，出现在两个中则忽略
            if (curToken.getSpanIndex() != expect.getSpanIndex()){
                return false;
            }
            Span span = parsing.getSpans().get(curToken.getSpanIndex());
            Effect effect = new Effect();
            effect.setStart(span.getStart()+ curToken.getStart());
            effect.setEnd(span.getStart()+expect.getStart()+expect.getToken().getLiteral().length());

            String varName = span.getText().substring(curToken.getStart()+curToken.getToken().getLiteral().length(),expect.getStart());
            Object tempValue = tempVar.get(varName);
            Object globalValue = globalVar.get(varName);
            Object result = tempValue != null ? tempValue : globalValue;
            //当var没有值时，不做处理
            if (result != null){
                effect.setResult(result.toString());
                effects.add(effect);
            }
            tokenStream.resetStart();
            return true;
        }else{
            //期望失败
            return false;
        }
    }


    public HashMap<String, Object> getGlobalVar() {
        return globalVar;
    }

    public HashMap<String, Object> getTempVar() {
        return tempVar;
    }

    public List<Effect> getEffects() {
        return effects;
    }
}
