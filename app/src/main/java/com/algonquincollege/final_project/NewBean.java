package com.algonquincollege.final_project;

public class NewBean {
    public double calories;
    public double fat;

    public NewBean(double calories, double fat) {
        super();
        this.calories = calories;
        this.fat = fat;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public String toString(){
        return "Calory: " + calories + " Fat: " + fat;
    }

    public NewBean() {
        super();
    }

}
