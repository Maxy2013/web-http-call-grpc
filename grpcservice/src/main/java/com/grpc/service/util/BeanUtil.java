package com.grpc.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;



/**
 * 类型转化工具类(beanUtil)
 *
 * @author dengxu
 * @since 2018-11-13
 */
public class BeanUtil {

    private static final Logger log = LoggerFactory.getLogger(BeanUtil.class);


    /**
     * 两个不同的实体类型的深度拷贝
     *
     * @param copyFrom 需要被拷贝实体
     * @param copyTo   拷贝结果实体
     * @param <T>      拷贝结果实体类型
     * @return         拷贝后的实体
     */
    public static  <T> T copy(Object copyFrom,T copyTo)  {
        Assert.notNull(copyFrom,"输出类型不能为空");
        Assert.notNull(copyTo,"输入类型不能为空");

        Class classFrom = copyFrom.getClass();
        Field[] fieldsFrom = classFrom.getDeclaredFields();
        Class classTo = copyTo.getClass();
        Field[] fieldsTo = classTo.getDeclaredFields();

        for (Field to:fieldsTo) {
            to.setAccessible(true);
            for (Field from:fieldsFrom) {

                if ((to.getName()+"_").equals(from.getName()) ) {  //如果域的名字或者类型不同，则不进行拷贝
                    doCopy(copyFrom,copyTo,from,to);
                }
            }
        }
        return copyTo;
    }


    /**
     * 对一个域执行拷贝操作
     *
     * @param copyFrom
     * @param copyTo
     * @param from
     * @param to
     * @param <T>
     */
    private static  <T> void doCopy(Object copyFrom,T copyTo,Field from,Field to){

        from.setAccessible(true);
        if (isBaseType(from) || isEnmu(from)) {  //如果是基本类型或者是枚举类型，使用基本拷贝
            copyForBase(copyFrom,copyTo,from,to);
            return;
        }

        try {
            if(null == from.get(copyFrom)){   //若需要拷贝的内容为空，将拷贝结果的对应字段置空
                to.set(copyTo, null);
                return;
            }
        } catch (IllegalAccessException e) {
            log.error(e.toString());
        }

        if(isList(from)){  //若需要拷贝的内容为List，调用Collection的处理方法copyForList
            try {
                List listFrom = (List) from.get(copyFrom);
                List listTo = listFrom.getClass().newInstance();
                copyForList(listFrom,listTo);
                to.set(copyTo, listTo);
            } catch (IllegalAccessException | InstantiationException e) {
                log.error(e.toString());
            }
            return;
        }

        if(isMap(from)){ //若需要拷贝的内容为Map，调用Map的处理方法copyForMap
            try {
                Map mapFrom = (Map) from.get(copyFrom);
                Map mapTo = mapFrom.getClass().newInstance();
                copyForMap(mapFrom,mapTo);
                to.set(copyTo, mapTo);
            } catch (IllegalAccessException | InstantiationException e) {
                log.error(e.toString());
            }
            return;
        }

        copyForQuote(copyFrom,copyTo,from,to);  //否则即为引用型，调用引用型的处理方法

    }
    /**
     * 基本类型域的拷贝（包括八种基本类型、字符串以及BigDecimal类型）
     *
     * @param copyFrom
     * @param copyTo
     * @param from
     * @param to
     * @param <T>
     */
    private static <T> void copyForBase(Object copyFrom,T copyTo,Field from,Field to){
        from.setAccessible(true);
        try {
            to.set(copyTo, from.get(copyFrom));
        } catch (IllegalAccessException e) {
            log.error(e.toString());
        }
    }


    /**
     * 对集合（List和Set）域进行拷贝
     *
     * @param listFrom
     * @param listTo
     */
    private static void  copyForList(List listFrom, List listTo){

        for (int i = 0; i < listFrom.size(); i++) {
            Class listClassFrom = listFrom.get(i).getClass();

            if(isBaseType(listClassFrom) || listClassFrom.isEnum()){  //类型判断
                listTo.add(listFrom.get(i));
                continue;
            }

            if(isList(listClassFrom)){
                List newListFrom = (List)listFrom.get(i);
                try {
                    List newListTo = (List)listClassFrom.newInstance();
                    copyForList(newListFrom,newListTo);
                    listTo.add(newListTo);
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error(e.toString());
                }

                continue;
            }

            if(isMap(listClassFrom)){
                try {
                    Map newMapTo = (Map)listClassFrom.newInstance();
                    copyForMap((Map)listFrom.get(i),newMapTo);
                    listTo.add(newMapTo);
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error(e.toString());
                }
                continue;
            }

            try {
                Object objectTo = listClassFrom.newInstance();
                copy(listFrom.get(i),objectTo);
                listTo.add(objectTo);
            } catch (InstantiationException | IllegalAccessException e) {
                log.error(e.toString());
            }

        }

    }

    /**
     * 对Map类型的域进行拷贝
     *
     * @param mapFrom
     * @param mapTo
     */
    private static void  copyForMap(Map mapFrom, Map mapTo) {

        for (Object keyFrom : mapFrom.keySet()) {
            Class mapClassFrom = mapFrom.get(keyFrom).getClass();
            if(isBaseType(mapClassFrom) || mapClassFrom.isEnum()){  //基本类型
                mapTo.put(keyFrom,mapFrom.get(keyFrom));
                continue;
            }

            if(isList(mapClassFrom)){
                List newListFrom = (List) mapFrom.get(keyFrom);
                List newListTo = null;
                try {
                    newListTo = (List) mapClassFrom.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error(e.toString());
                }
                copyForList(newListFrom,newListTo);
                mapTo.put(keyFrom,newListTo);
                continue;
            }

            if(isMap(mapClassFrom)){
                try {
                    Map newMapTo = (Map) mapClassFrom.newInstance();
                    copyForMap((Map) mapFrom.get(keyFrom),newMapTo);
                    mapTo.put(keyFrom,newMapTo);
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error(e.toString());
                }
                continue;
            }

            try {
                Object objectTo = mapClassFrom.newInstance();
                copy(mapFrom.get(keyFrom),objectTo);
                mapTo.put(keyFrom,objectTo);
            }  catch (InstantiationException | IllegalAccessException e) {
                log.error(e.toString());
            }

        }
    }

    /**
     * 引用类型域的拷贝
     *
     * @param copyFrom
     * @param copyTo
     * @param from
     * @param to
     * @param <T>
     */
    private static <T> void copyForQuote(Object copyFrom,T copyTo,Field from,Field to){
        from.setAccessible(true);
        try {
            if(null == from.get(copyFrom)){
                to.set(copyTo, null);
            }else {
                to.set(copyTo, copy(from.get(copyFrom),getNewObject(to)));
            }
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
        }
    }


    /**
     * 根据域的类型创建一个此域的对象
     *
     * @param field
     * @return
     */
    private static Object getNewObject(Field field){
        field.setAccessible(true);
        String type = field.getType().toString();
        String path = type.substring(type.indexOf(' ')+1);

        try {
            return Class.forName(path).newInstance();
        } catch (InstantiationException |IllegalAccessException | ClassNotFoundException e) {
            log.error(e.getMessage());
        }

        log.error("get new Object faild");
        return  null;
    }

    /**
     * 判断当前域是否是Map
     *
     * @param field
     * @return
     */
    private static Boolean isMap(Field field){
        Type type = field.getType();
        return isMap(type);
    }

    private static Boolean isMap(Type type){
        return  type.equals(java.util.Map.class) ||
                type.equals(java.util.HashMap.class) ||
                type.equals(java.util.LinkedHashMap.class) ||
                type.equals(java.util.TreeMap.class) ||
                type.equals(java.util.EnumMap.class) ||
                type.equals(java.util.WeakHashMap.class) ||
                type.equals(java.util.IdentityHashMap.class) ||
                type.equals(java.util.concurrent.ConcurrentHashMap.class);
    }

    /**
     * 判断当前域是否是List
     *
     * @param field
     * @return
     */
    private static Boolean isList(Field field){
        Type type = field.getType();
        return isList(type);
    }
    private static  Boolean isList(Type type){
        return  type.equals(java.util.List.class) ||
                type.equals(java.util.ArrayList.class) ||
                type.equals(java.util.LinkedList.class) ||
                type.equals(java.util.Vector.class) ;

    }

    /**
     * 判断当前域是否为枚举类型
     *
     * @param field
     * @return
     */
    private static boolean isEnmu(Field field){

        Type type = field.getType();
        return isEnmu(type);
    }

    private static boolean isEnmu(Type type){

        String typeToString = type.toString();
        String path = typeToString.substring(typeToString.indexOf(' ')+1);
        Class enmuClass = null;
        try {
            enmuClass = Class.forName(path);
        } catch (ClassNotFoundException e) {
            log.error("class ${} not found ",path);
        }
        return (enmuClass == null) || enmuClass.isEnum();
    }

    /**
     * 判断当前域是否为直接复制的基本类型（包括8中基本类型、String以及BigDecimal类型）
     *
     * @param field
     * @return
     */
    private static boolean isBaseType(Field field) {
        Type fieldType =field.getType();
        return isBaseType(fieldType);
    }

    private static boolean isBaseType(Type type) {
        return   type.equals(java.lang.Integer.class) ||
                type.equals(java.lang.Byte.class) ||
                type.equals(java.lang.Long.class) ||
                type.equals(java.lang.Double.class) ||
                type.equals(java.lang.Float.class) ||
                type.equals(java.lang.Character.class) ||
                type.equals(java.lang.Short.class) ||
                type.equals(java.lang.Boolean.class) ||
                type.equals(java.lang.String.class)||
                type.equals(java.math.BigDecimal.class)||
                type.toString().equals("int") ||
                type.toString().equals("byte") ||
                type.toString().equals("long") ||
                type.toString().equals("double") ||
                type.toString().equals("float") ||
                type.toString().equals("char") ||
                type.toString().equals("short") ||
                type.toString().equals("boolean")  ;

    }
}
