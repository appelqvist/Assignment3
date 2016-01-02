package main;

import java.util.LinkedList;

/**
 * Created by Andreas Appelqvist on 2015-12-08.
 *
 * Representerar en lastbil som kan lastas med FoodItem-objekt
 *
 */
public class Truck extends Thread {

    private Controller controller;
    private LinkedList<FoodItem> truck = new LinkedList<FoodItem>();

    private double weightLimit;
    private double volumeLimit;
    private int itemLimit;

    private double weight;
    private double volume;
    private int items;

    private boolean running = false;
    private Storage storage;

    /**
     *
     * Inizisering
     *
     * @param storage
     * @param controller
     * @param weightLimit
     * @param volumeLimit
     * @param itemLimit
     */
    public Truck(Storage storage, Controller controller, double weightLimit, double volumeLimit, int itemLimit) {
        this.storage = storage;
        this.controller = controller;
        this.weightLimit = weightLimit;
        this.volumeLimit = volumeLimit;
        this.itemLimit = itemLimit;
        volume = 0;
        weight = 0;
        items = 0;
    }

    /**
     * Startar tråden
     */
    public void startThread() {
        this.running = true;
        this.start();
    }

    /**
     * Lägger ett nytt FoodItem i lastbilen
     * Om fullt åker och levererar.
     */
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
            controller.setTruckStatus(0);
            controller.currentTruckItems(null);
            deliverFood();
        }
    }

    /**
     * Levererar varorna
     */
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
            getItem();
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
