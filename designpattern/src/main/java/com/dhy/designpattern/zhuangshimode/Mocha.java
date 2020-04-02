package com.dhy.designpattern.zhuangshimode;

/**
 * Created by dhy
 * Date: 2020/4/1
 * Time: 15:44
 * describe:
 */
public class Mocha extends CondimentDecorator {
    Beverage mBeverage;

    public Mocha(Beverage beverage) {
        mBeverage = beverage;
    }

    @Override
    public String getDescription() {
        return mBeverage.getDescription()+" , Mocha ";
    }

    @Override
    public double cost() {
        return mBeverage.cost()+1.2;
    }
}
