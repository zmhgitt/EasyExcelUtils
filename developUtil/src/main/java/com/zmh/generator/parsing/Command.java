package com.zmh.generator.parsing;

import static com.zmh.generator.parsing.TokenType.*;

/**
 * @author Miles
 * @date 2022/10/10 18:21
 * 封装所有有效词
 */
public class Command {

    private TokenType[][] baseCommand = new TokenType[][]{
           new TokenType[]{GetVar,RightCurly},
    };
}
