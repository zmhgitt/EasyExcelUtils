package com.zmh.generator.parsing;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Miles
 * @date 2022/10/10 17:56
 * 封装所有对源字符串 进行处理的方法
 */
public class SourceStream {

    private final String temp = "Temp?";

    /**
     * 源字符串
     */
    private String source;

    /**
     * 处理过的字符
     */
    private String result;

    private Map<Integer,SourcePosition> sourcePositionMap;

    /**某字符的中间字符*/
    private Map<Integer,SourcePosition> middlePositionMap;

    public SourceStream(String source){
        this.source = source;
        this.result = source;
        this.sourcePositionMap = new HashMap<>(32);
    }

    /**
     * 剔除引号内容，以避免被邮箱
     */
    public void eliminateQuotes(){
        middlePositionMap = new HashMap<>(32);

        eliminate("\"","\"","0",middlePositionMap);
        eliminate("'","'","1",middlePositionMap);
    }

    public void eliminate(String startStr,String endStr,String rStr,Map<Integer,SourcePosition> middlePositionMap){
        int curIndex = this.result.indexOf(startStr);

        int endLen = endStr.length();
        int initSize = middlePositionMap.size();
        int position = initSize;
        while (curIndex > -1){
            int endIndex = this.result.indexOf(endStr, curIndex + 1);
            if (endIndex < 0){
                break;
            }
            SourcePosition sourcePosition = new SourcePosition();
            sourcePosition.setStart(curIndex);
            sourcePosition.setEnd(endIndex+endLen);
            sourcePosition.setValue(new String(this.result.substring(curIndex,sourcePosition.getEnd())));
            curIndex = this.result.indexOf(startStr,sourcePosition.getEnd()+1);
            middlePositionMap.put(position++,sourcePosition);
        }
        // >> 是否有替换
        if(initSize != position){
            String result = this.result;
            int offset = 0;
            for (;initSize < position;initSize++){
                SourcePosition sourcePosition = middlePositionMap.get(initSize);
                result = result.substring(0,sourcePosition.getStart()+offset) + " " + temp + rStr + " " + result.substring(sourcePosition.getEnd() + offset);
                offset += -sourcePosition.length() + 2 + temp.length() + rStr.length();
            }
            this.result = result;
        }
    }
    /**
     * 剔除无关字符（与关键字不相干均为无关字符）
     */
    public void eliminate(){
    }


    public String getResult() {
        return result;
    }
}
