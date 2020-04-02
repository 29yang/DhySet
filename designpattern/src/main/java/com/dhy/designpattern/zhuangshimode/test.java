package com.dhy.designpattern.zhuangshimode;

/**
 * Created by dhy
 * Date: 2020/4/1
 * Time: 15:46
 * describe:
 */
public class test {
    public static void main(String[] args) {
        Beverage houseBlend = new HouseBlend();
        houseBlend = new Mocha(houseBlend);
        System.out.println("="+houseBlend.getDescription()+"  "+houseBlend.cost());
        houseBlend = new Whip(houseBlend);
        System.out.println("="+houseBlend.getDescription()+"  "+houseBlend.cost());
    }
}
