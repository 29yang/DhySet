package com.dhy.designpattern.zhuangshimode;

/**
 * Created by dhy
 * Date: 2020/4/1
 * Time: 15:39
 * describe:
 */
public class Espresso extends Beverage {
    public Espresso() {
        description="Espresso";
    }

    @Override
    public double cost() {
        return 18.2;
    }
}
