# 封装 EasyExcel 实现 导出的工具类
由于md编辑器崩了，详细方法请看Test WriterText类

# 贴出部分代码，快速上手，该工具目前只支持导出、并且只支持实体类导出，没有封装Map，List等导出
 EasyExcelWriter<User> writer = new EasyExcelWriter<User>(User.class);
 // web  使用  Response.getOutputStream方法
 OutputStream outputStream = new FileOutputStream(new File("D:\\11\\" + System.currentTimeMillis() + ".xlsx"));
 //DataUtils.data()模拟数据库数据
 writer.setOutputStream(outputStream)
           .simpleExport(DataUtils.data());
