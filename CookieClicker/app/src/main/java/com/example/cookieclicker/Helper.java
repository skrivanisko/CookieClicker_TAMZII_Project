package com.example.cookieclicker;

public class Helper {

    private int id;
    private String name;
    private int production;
    private int cost;
    private int count;

    public Helper(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProduction() {
        return production;
    }

    public void setProduction(int production) {
        this.production = production;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ID: " + this.id + " NAME: " + this.name + " PRODUCTION: " +
                this.production + " COST: " + this.cost + "COUNT: " + this.count;
    }
}
