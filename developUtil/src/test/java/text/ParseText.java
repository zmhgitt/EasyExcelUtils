package text;

import com.zmh.generator.parsing.*;
import com.zmh.generator.parsing.Compiler;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Miles
 * @date 2022/10/11 14:47
 */
@Ignore
public class ParseText {

    @Test
    public void parseBaseTokens() {
        System.out.println("-------校验parseBaseTokens方法");

        //1、获得List<Span>
        String source = "Z{1} snhdicusdhnis nsid{cnsidni \n" +
                "xx{x}          s}s}s";
        List<Span> spanList = new ArrayList<>(100);
        SourceParsing sourceParsing = new SourceParsing(source);
        sourceParsing.parseSpan(spanList);

        Parsing parsing = new Parsing(spanList);
        //测试parseBaseTokens方法（在文本中获取所有指定字符串及其所在位置）
        parsing.parseBaseTokens();
        List<TokenPosition> baseTokens = parsing.getBaseTokens();
        for (TokenPosition baseToken : baseTokens) {
            System.out.println(baseToken.getStart() + " : " + baseToken.getToken().getLiteral());
        }
    }

    @Test
    public void effectList(){
        System.out.println("-------是否有effectList--------");

        String source = "hello Z{name}!!!";
        List<Span> spanList = new ArrayList<>(100);
        SourceParsing sourceParsing = new SourceParsing(source);
        sourceParsing.parseSpan(spanList);

        //获取Token列表
        Parsing parsing = new Parsing(spanList);
        parsing.parseBaseTokens();

        //编译Token
        Compiler compiler = new Compiler(parsing);
        Map<String, Object> globalVar = compiler.getGlobalVar();
        globalVar.put("name","张三");
        compiler.Compile();

        List<Effect> effects = compiler.getEffects();
        System.out.println(source);
        for (Effect effect : effects) {
            System.err.println(effect.getStart()+ " " + effect.getEnd() + " " + effect.getResult());
        }
    }
}
