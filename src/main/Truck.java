package main;

import java.util.LinkedList;

/**
 * Created by Andreas Appelqvist on 2015-12-08.
 */
public class Truck extends Thread {

    Controller controller;

    private LinkedList<FoodItem> truck = new LinkedList<FoodItem>();

    private double weightLimit = 7;
    private double volumeLimit = 12;
    private int itemLimit = 9;

    private double weight;
    private double volume;
    private int items;

    private boolean running = false;
    private Storage storage;

    public Truck(Storage storage, Controller controller) {
        this.storage = storage;
        this.controller = controller;
        volume = 0;
        weight = 0;
        items = 0;
    }

    public void startThread() {
        this.running = true;
        this.start();
    }

    public void stopThread() {
        this.running = false;
    }

    public void getItem() {
        controller.setTruckStatus(1);

        FoodItem item = storage.get(weightLimit-weight, volumeLimit-volume, itemLimit-items);
        if(item != null){
            truck.addFirst(item);
            weight += item.getWeight();
            volume += item.getVolume();
            items++;
            System.out.println("Tog ur: "+item.getName());
            controller.currentTruckItems(truck);
        }else{
            System.out.println("Åker iväg -----------------------------------------------------------------------");
            controller.setTruckStatus(2);
            controller.currentTruckItems(null);
            deliverFood();
        }
    }

    public void deliverFood() {
        truck.clear();
        weight = 0;
        volume = 0;
        items = 0;
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
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getItem();
        }
    }
}
