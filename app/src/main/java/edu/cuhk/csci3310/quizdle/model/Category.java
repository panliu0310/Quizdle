package edu.cuhk.csci3310.quizdle.model;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<String> subCategory;

    public String getName() {
        return name;
    }
    public ArrayList<String> getSubCategory() {
        return subCategory;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setSubCategory(ArrayList<String> subCategory) {
        this.subCategory = subCategory;
    }
}
