/**
 * File name: NutritionNewBean.java
 * NutritionAuthor: Feng Cheng, ID#:040719618
 * Course: CST2335 - Mobile Graphical Interface Prog.
 * Final project
 * Date: 2018-11-12
 * Professor: Eric
 * Purpose: To store the data of calory and fat
 */
package com.algonquincollege.final_project;

/**
 * @author Feng Cheng
 */
public class NutritionNewBean {
    public double calories;
    public double fat;

    /**
     * constructor for instantiation
     *
     * @param calories double
     * @param fat      double
     */
    public NutritionNewBean(double calories, double fat) {
        super();
        this.calories = calories;
        this.fat = fat;
    }

    /**
     * to get the calories
     *
     * @return the calories double
     */
    public double getCalories() {
        return calories;
    }

    /**
     * to the calories
     *
     * @param calories the calories to set
     */
    public void setCalories(double calories) {
        this.calories = calories;
    }

    /**
     * to get the fat
     *
     * @return fat double
     */
    public double getFat() {
        return fat;
    }

    /**
     * to set the fat
     *
     * @param fat fat to set
     */
    public void setFat(double fat) {
        this.fat = fat;
    }
//
//    public String toString() {
//        return "Calory: " + calories + " Fat: " + fat;
//    }

    /**
     * ]
     * the constructor
     */
    public NutritionNewBean() {
        super();
    }

}
