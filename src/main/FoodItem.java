package main;

/**
 * Created by Andreas Appelqvist on 2015-12-08.
 *
 *  Klassen används för att hålla reda på data om en vara.
 */
public class FoodItem {

    private String name;
    private double weight;
    private double volume;

    /**
     * Konstruktorn, Initizierar namn,vikt,volym
     *
     * @param name
     * @param weight
     * @param volume
     */
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
