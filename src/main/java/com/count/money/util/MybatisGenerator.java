package com.count.money.util;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * <p>
 * 代码生成器演示
 * </p>
 *
 * @author hubin
 * @date 2016-12-01
 */
public class MybatisGenerator {
    //包路径
    private static final String PACKAGE_NAME = "com.count.money";
    //保存路径
    private static final String projectPath = System.getProperty("user.dir") + "\\src\\main\\java";
    public static void main(String[] args) {
       /* 生成代码包名 */
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath);
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        //gc.setBaseResultMap(true);// XML ResultMap
        //gc.setBaseColumnList(false);// XML columList
       // gc.setAuthor("wang");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        // gc.setServiceName("MP%sService");
        // gc.setServiceImplName("%sServiceDiy");
        // gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setUrl("jdbc:mysql://localhost:3306/future?characterEncoding=utf8");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
//        strategy.setTablePrefix(new String[] { "citizen_"});// 此处可以修改为您的表前缀
//        strategy.setNaming(NamingStrategy.remove_prefix_and_camel);// 表名生成策略
//        strategy.setInclude(new String[] { "citizen_bigdata_log" }); // 需要生成的表
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[] { "future_money_bill" }); // 需要生成的表
        strategy.setInclude(new String[] { "future_money_member" }); // 需要生成的表
        strategy.setInclude(new String[] { "future_money_project" }); // 需要生成的表
        strategy.setNaming(NamingStrategy.remove_prefix_and_camel);// 表名生成策略
        strategy.setFieldNaming(NamingStrategy.underline_to_camel);
//        strategy.setFieldNaming(NamingStrategy.underline_to_camel);
        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
        // 自定义实体父类
//         strategy.setSuperEntityClass(PACKAGE_NAME+".common.mybatis.base.BsBaseEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
//         strategy.setSuperMapperClass(PACKAGE_NAME+".common.mybatis.base.BsBaseMapper");
        // 自定义 service 父类
//         strategy.setSuperServiceClass(PACKAGE_NAME+".common.mybatis.base.BsBaseService");
        // 自定义 service 实现类父类
//         strategy.setSuperServiceImplClass(PACKAGE_NAME+".common.mybatis.base.BsBaseServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
         strategy.setEntityColumnConstant(false);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(PACKAGE_NAME);
        pc.setModuleName("future");
        mpg.setPackageInfo(pc);

        // 执行生成
        mpg.execute();
    }


}