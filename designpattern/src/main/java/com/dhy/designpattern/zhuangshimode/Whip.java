package com.dhy.designpattern.zhuangshimode;

/**
 * Created by dhy
 * Date: 2020/4/1
 * Time: 15:44
 * describe:
 */
public class Whip extends CondimentDecorator {
    Beverage mBeverage;

    public Whip(Beverage beverage) {
        mBeverage = beverage;
    }

    @Override
    public String getDescription() {
        return mBeverage.getDescription() + " , Whip ";
    }

    @Override
    public double cost() {
        return mBeverage.cost() + .2;
    }
}
