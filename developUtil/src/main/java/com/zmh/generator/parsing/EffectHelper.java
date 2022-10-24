package com.zmh.generator.parsing;

import java.util.List;

/**
 * @author Miles
 * @date 2022/10/11 18:16
 */
public class EffectHelper {

    private String source;
    /**必须按照先后顺序来，可以给他排序，按start从小到大*/
    private List<Effect> effects;

    private String result;

    public EffectHelper(String source,List<Effect> effects){
        this.source = source;
        this.effects = effects;
    }

    public void effect(){
        int offset = 0;
        String result = this.source;
        for (Effect effect : effects) {
            result = result.substring(0,effect.getStart()+offset) + effect.getResult() + result.substring(effect.getEnd() + offset);
            offset = effect.offset(offset);
        }
        this.result = result;
    }
}
