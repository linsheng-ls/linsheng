

package com.example.awswebdemo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;

import com.baomidou.mybatisplus.generator.config.OutputFile;


import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create(
                        "jdbc:mysql://database-1.cfu2oig6itra.ap-northeast-1.rds.amazonaws.com:3306/demo?useSSL=false&serverTimezone=Asia/Tokyo ",
                        "admin",
                        "Linsheng00")
                .globalConfig(builder -> {
                    builder.author("linsheng") // 作者
                            .outputDir(System.getProperty("user.dir") + "/src/main/java") // 生成路径
                            .commentDate("yyyy-MM-dd");
                })
                .packageConfig(builder -> {
                    builder.parent("com.example")
                            .moduleName("awswebdemo")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/resources/mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("suumo_house_info") // 表名
                            .entityBuilder()
                            .idType(IdType.AUTO)
                            .enableLombok(); // 使用 Lombok 简化 getter/setter
                })
                .execute();
    }
}
