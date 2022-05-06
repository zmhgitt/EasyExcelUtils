package com.zmh.easyexcel.context;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.exception.ExcelCommonException;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.zmh.easyexcel.annotation.group.Default;
import com.zmh.easyexcel.annotation.ExcelPropertySupport;
import com.zmh.easyexcel.entity.WriteField;
import com.zmh.easyexcel.enums.ExcelType;
import com.zmh.easyexcel.util.FieldUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Miles(miles @ kastking.com)
 * @date: 2022/4/28 15:34
 * @description:
 *
 * 对Field的Annotation统计
 */
public class WriterContext {

    private ExcelWriterBuilder excelWriterBuilder;

    /**
     * 操作的对象字节码
     * */
    private Class<?> entityClazz;

    /**
     * 下标对应写入字段的列顺序
     * */
    private List<WriteField> writeFields;

    /**
     * 标题最高层级
     * */
    private int lastTitleIndex = 0;

    /**
     * 字段是否默认导出
     * */
    boolean isExport = true;

    public WriterContext(Class<?> entityClazz){
        this.entityClazz = entityClazz;
    }

    /**
     *init
     * */
    public void initWriteField(){
        if (entityClazz == null){
            throw new RuntimeException();
        }

        if (entityClazz.getAnnotation(ExcelIgnoreUnannotated.class) != null){
            isExport = false;
        }

        List<Field> allFields = FieldUtils.getAllFields(entityClazz);
        List<WriteField> orderFieldList = new ArrayList<WriteField>(allFields.size());
        Map<Integer, WriteField> indexFiledMap = new TreeMap<Integer, WriteField>();
        for (Field field : allFields){
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            ExcelPropertySupport excelPropertySupport = field.getAnnotation(ExcelPropertySupport.class);
            ExcelIgnore excelIgnore = field.getAnnotation(ExcelIgnore.class);

            boolean exportField = true;
            if (isExport){
                if (excelIgnore != null){
                    exportField = false;
                }
            }else{
                if (excelProperty == null){
                    exportField = false;
                }
            }
            if (exportField){
                if (excelPropertySupport != null &&
                        excelPropertySupport.type().equals(ExcelType.IMPORT)){
                    exportField = false;
                }
            }

            if (exportField){
                WriteField writeField = new WriteField();
                writeField.setField(field);
                writeField.setExcelProperty(excelProperty);
                writeField.setExcelPropertySupport(excelPropertySupport);
                if (excelProperty != null && excelProperty.index() != -1){
                    //这段为EasyExcel 的源码
                    if (indexFiledMap.containsKey(excelProperty.index())) {
                        throw new ExcelCommonException("The index of '" + indexFiledMap.get(excelProperty.index()).getField().getName()
                                + "' and '" + field.getName() + "' must be inconsistent[need different @ExcelProperties.index]");
                    }
                    indexFiledMap.put(excelProperty.index(), writeField);
                }else{
                    orderFieldList.add(writeField);
                }
            }
        }

        //将orderFiled 和 indexFiled 按顺序合并  为了避免和EasyExcel源码排序不同，此处模仿源码处理
        Map<Integer,WriteField> allFiledMap = new HashMap<Integer, WriteField>(
                orderFieldList.size()+indexFiledMap.size()
        );
        int index = 0;
        for (WriteField writeField : orderFieldList){
            while (indexFiledMap.containsKey(index)){
                allFiledMap.put(index,indexFiledMap.get(index));
                allFiledMap.remove(index);
                index++;
            }
            allFiledMap.put(index,writeField);
            index++;
        }
        allFiledMap.putAll(indexFiledMap);

        //将最终排序确定的index设置到WriteField中，并加入writeFields
        //index排序可以跳，比如第一列有值，但是第二列可以没值，直接跳到第三列，所以需要确切的列标
        //不能单纯的用下标表示列标
        writeFields = new ArrayList<WriteField>(allFields.size());
        for (Integer key : allFiledMap.keySet()){
            WriteField writeField = allFiledMap.get(key);
            writeField.setIndex(key);
            writeFields.add(writeField);
        }
        //最高层级标题
        for (WriteField writeField : writeFields){
            if (writeField.getExcelProperty() != null){
                writeField.setLastTitleIndex(writeField.getExcelProperty().value().length);
                if (lastTitleIndex < writeField.getLastTitleIndex()){
                    lastTitleIndex = writeField.getLastTitleIndex();
                }
            }
        }
    }

    /**
     *  通过 {@link ExcelPropertySupport#groups()}
     *  确定目前需要导出的参数
     *  该功能是可以让用户在一个实体类这实现不同的导出参数，无需创建第二个实体类
     *  不过建议不要弄到太多太混就行，
     *  如果想同时导出那些没有手动声明groups()的，请另class继承{@link Default}
     * */
    public void doGroups(Class group){
        if (writeFields == null){
            throw new RuntimeException("EasyExcelWriter.createExcelWriter get ExcelWriter");
        }

        Iterator<WriteField> its = writeFields.iterator();
        while (its.hasNext()){
            WriteField writeField = its.next();
            boolean isInclude = false; //
            if (writeField.getExcelPropertySupport() != null && writeField.getExcelPropertySupport().groups() != null){
                Class[] groups =  writeField.getExcelPropertySupport().groups();
                for (Class _group : groups){
                    if (_group != null && _group.isAssignableFrom(group)){
                        isInclude = true;
                    }
                }
            }else if (writeField.getExcelPropertySupport() == null){
                if (Default.class.isAssignableFrom(group)){
                    isInclude = true;
                }
            }
            if (!isInclude){
                its.remove();
            }
        }
    }

    /**
     * 获取fieldName
     * */
    public Set<String> getFieldNameSet(){
        Set<String> fieldNames = new HashSet<String>(writeFields.size());
        for (WriteField writeField : writeFields){
            fieldNames.add(writeField.getField().getName());
        }
        return fieldNames;
    }

    public List<WriteField> getWriteFields() {
        return writeFields;
    }

    public int getLastTitleIndex() {
        return lastTitleIndex;
    }

    public ExcelWriterBuilder getExcelWriterBuilder() {
        return excelWriterBuilder;
    }

    public void setExcelWriterBuilder(ExcelWriterBuilder excelWriterBuilder) {
        this.excelWriterBuilder = excelWriterBuilder;
    }
}
