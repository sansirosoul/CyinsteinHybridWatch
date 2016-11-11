package com.example;


import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyClass {
    public static void main(String[] args) {
        //生成数据库的实体类 对应的是 数据库的表
        Schema schema = new Schema(1, "com.partner.entity");
       addStudent(schema);
      //  addToOne(schema);   //一对一，一对多
        schema.setDefaultJavaPackageDao("com.partner.dao");
        try {
            new DaoGenerator().generateAll(schema, "E:\\test\\Partner\\app\\src\\main\\java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //创建数据库的表
    private static void addStudent(Schema schema) {
        Entity entity = schema.addEntity("Partner");//创建数据库的表
        entity.addIdProperty();//主键 是int类型
        entity.addStringProperty("type");
        entity.addStringProperty("date");
        entity.addStringProperty("time");
        entity.addStringProperty("sleep");
        entity.addStringProperty("lightsleep");
        entity.addStringProperty("sleeping");
        entity.addStringProperty("awake");
    }


//    private static void addToOne(Schema schema){
//        //日期Date
//        Entity date = schema.addEntity("Date");
//        date.implementsInterface("Parcelable");    //序列化User操作
//        date.addStringProperty("dateid").notNull().primaryKey();  // 主键
//        date.addStringProperty("date");
//
//        //数据 data
//        Entity formation = schema.addEntity("Data");
//        Property formation_id = formation.addStringProperty("uuid").notNull().primaryKey().getProperty();//阵容uuid
//        formation.addStringProperty("tepe");//步数
//        formation.addStringProperty("time");//时间
//        Property author = formation.addStringProperty("date_id").notNull().getProperty();
//
//        date.addToMany(formation, author); //【一个人可以有多个阵容】当用人去找阵容时，阵容是Target，阵容中的player_id是targerProperty
//       // formation.addToOne(user, formation_id);//【一个阵容只能有一个人】当用阵容去找人时，人是Target，阵容中的uuid是targetProperty
//    }
}