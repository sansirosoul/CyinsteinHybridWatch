package com.example;


import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyClass {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.partner.entity");
       addStudent(schema);
        schema.setDefaultJavaPackageDao("com.partner.dao");
        try {
            new DaoGenerator().generateAll(schema, "E:\\test\\Partner\\app\\src\\main\\java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void addStudent(Schema schema) {
        Entity entity = schema.addEntity("Partner");
        entity.addIdProperty();
        entity.addStringProperty("type");
        entity.addStringProperty("date");
        entity.addStringProperty("time");
        entity.addStringProperty("sleep");
        entity.addStringProperty("lightsleep");
        entity.addStringProperty("sleeping");
        entity.addStringProperty("awake");
        entity.addStringProperty("exercisetime");
        entity.addStringProperty("exercisedistance");
        entity.addStringProperty("calcalNum");
        entity.addStringProperty("stepsumsnum");
    }
}