package com.dhy.designpattern.zhuangshimode;

/**
 * Created by dhy
 * Date: 2020/4/1
 * Time: 15:39
 * describe:
 */
public class HouseBlend extends Beverage {
    public HouseBlend() {
        description="House Blend";
    }

    @Override
    public double cost() {
        return 1.2;
    }
}
