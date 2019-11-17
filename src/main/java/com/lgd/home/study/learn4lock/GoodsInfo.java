package com.lgd.home.study.learn4lock;

public class GoodsInfo {
    private final String name;
    private double totalMoney;//销售额
    private int storeNumber;//库存总数

    public GoodsInfo(String name, double totalMoney, int storeNumber) {
        this.name = name;
        this.totalMoney = totalMoney;
        this.storeNumber = storeNumber;
    }

    public String getName() {
        return name;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public int getStoreNumber() {
        return storeNumber;
    }
    public void changeNumber(int sellNumber){
        totalMoney+=sellNumber*25;
        storeNumber-=sellNumber;
    }
}
