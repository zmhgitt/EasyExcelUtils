package text;

import com.zmh.generator.parsing.SourceParsing;
import com.zmh.generator.parsing.Span;
import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Miles
 * @date 2022/10/11 11:33
 *
 */
@Ignore
public class SourceParsingText {

    /**
     * 把Excel文件复制到文本中，然后获取文本，将其转化为sql
     */
    @Test
    public void toSql(){
        try {
            //获取复制到txt 的 excel表格
            String source = FileUtils.readFileToString(new File("D:\\11\\purchaseNumber.txt"), "utf-8");

            List<Span> spanList = new ArrayList<Span>(2000);
            SourceParsing sourceParsing = new SourceParsing(source);
            sourceParsing.parseSpan(spanList);

            StringBuilder result = new StringBuilder();
            int col = 11;//表格有11列

            //sql1
            String sql = "update mis_purchase_order_details set ls_purchase_number = '%s' where purchase_number = '%s' and sku = '%s';\n";
            for (int i = 0; i<spanList.size();i += col){
                result.append(String.format(sql,spanList.get(i+4).getText(),spanList.get(i+3).getText(),spanList.get(i+5).getText()));
            }
            System.out.println(result);


            //sql2
//            String sql2 = "UPDATE mis_warehouse_receipt a INNER JOIN mis_warehouse_receipt_details b ON a.receipt_id = b.receipt_id SET ls_purchase_number = '%s' WHERE a.purchase_number = '%s' AND b.sku = '%s' AND ls_purchase_number IS NULL;\n";
//            for (int i = 0; i<spanList.size();i += col){
//                result.append(String.format(sql2,spanList.get(i+4).getText(),spanList.get(i+3).getText(),spanList.get(i+5).getText()));
//            }
//            System.out.println(result);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void parseSpan() {
        System.out.println("-------校验ParseSpan方法");
        String source = "shnvidjsnsjn snhdicusdhnis nsidcnsidni \n" +
                "xxx          sss";
        List<Span> spanList = new ArrayList<Span>(100);
        SourceParsing sourceParsing = new SourceParsing(source);
        sourceParsing.parseSpan(spanList);
        System.out.println(source);
        for (Span span : spanList) {
            System.err.println(span.getText());
        }
    }

}
