package com.model.design.adapter.abstractfactory.adapter;

/**
 * @author liuzheng
 * 2018/12/26 9:49
 * 适配器模式
 */
public class ModelAdapter extends Source implements Targetable{


    public void method2() {
        System.out.println("4567fghty------>>>>method2");
    }

    public static void main(String[] args) {
        Targetable targetable = new ModelAdapter();
        targetable.method1();
        targetable.method2();
    }
}
