package com.zmh.generator.start;

import com.zmh.generator.parsing.SourceBuilder;

/**
 * @author Miles
 * @date 2022/10/10 17:47
 */
public class Generator {

    /**
     * 源字符串
     */
    private String source;

    public Generator(String source){
        this.source = source;
    }

    /**
     * 执行
     */
    public String execute(){
        return null;
    }

    public static void main(String[] args) {
        String source = "csuihuisvid'xxsdsidhji'sehfcuishwfiuwehi'asasasas'";
        SourceBuilder sourceStream = new SourceBuilder(source);
        sourceStream.eliminateQuotes();

        System.out.println(source);
        System.out.println(sourceStream.getResult());
    }
}
