package main;

/**
 * Created by Andreas Appelqvist on 2015-12-08.
 */
public class FoodItem {

    private String name;
    private double weight;
    private double volume;

    public FoodItem(String name, double weight, double volume){
        this.name = name;
        this.weight = weight;
        this.volume = volume;
    }

    public double getVolume(){
        return this.volume;
    }

    public double getWeight(){
        return this.weight;
    }

    public String getName(){
        return this.name;
    }

}
