package com.dhy.designpattern.zhuangshimode;


/** 装饰者模式超类
 * Created by dhy
 * Date: 2020/4/1
 * Time: 11:38
 * describe:
 *
 * CondimentDecorator作为装饰者 子类继承后需要持有被装饰者对象(即 继承超类Beverage的具体子类)
 *
 *
 */
public abstract class Beverage {
    String description = "Unkown Beverage";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract double cost();

}
