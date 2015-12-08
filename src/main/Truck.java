package main;

import java.util.LinkedList;

/**
 * Created by Andreas Appelqvist on 2015-12-08.
 */
public class Truck extends Thread {

    private LinkedList<FoodItem> truck = new LinkedList<FoodItem>();

    private double weightLimit;
    private double volumeLimit;

    private double weight;
    private double volume;

    private boolean running = false;
    private Storage storage;

    public Truck(Storage storage, double weightLimit, double volumeLimit) {
        this.storage = storage;
        this.weightLimit = weightLimit;
        this.volumeLimit = volumeLimit;
        volume = 0;
        weight = 0;
    }

    public void startThread() {
        this.running = true;
        this.start();
    }

    public void stopThread() {
        this.running = false;
    }

    public void getItem() {
        FoodItem item = storage.get(weightLimit-weight, volumeLimit-volume);
        if(item != null){
            truck.addFirst(item);
            weight += item.getWeight();
            volume += item.getVolume();
            System.out.println("Tog ur: "+item.getName());
        }else{
            System.out.println("Åker iväg -----------------------------------------------------------------------");
            deliverFood();
        }
    }

    public void deliverFood() {
        truck.clear();
        weight = 0;
        volume = 0;
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        while (running) {
            getItem();
        }
    }
}
