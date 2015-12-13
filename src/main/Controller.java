package main;

import java.util.LinkedList;

/**
 * Created by Andreas Appelqvist on 2015-12-08.
 */
public class Controller {

    private GUISemaphore gui;
    private Storage storage;
    private Factory[] factories = new Factory[2];
    private Truck truck;

    public Controller(GUISemaphore gui) {
        this.gui = gui;
        this.storage = new Storage(this);
        gui.setController(this);
    }

    public void updateGUIstorageSize(int maxSize, int currentSize) {
        float percent = (currentSize * 100 / maxSize);
        gui.updateStorageSize(percent);
    }

    public void startFactory(int thread) {
        if (factories[thread] == null) {
            factories[thread] = new Factory(storage);
            factories[thread].setName(""+thread);
            factories[thread].startThread();
            updateFactoryStatus(thread,true);
        }
    }

    public void stopFactory(int thread) {
        factories[thread].stopThread();
        updateFactoryStatus(thread,false);
        factories[thread] = null;
    }

    public void updateFactoryStatus(int factory, boolean working){
        gui.setFactoryStatusWorking(factory,working);
    }

    public void startDelivery() {
        double weightLimit = 7;
        double volumeLimit = 12;
        int sizeLimit = 8;

        if (truck == null) {
            truck = new Truck(storage, this, weightLimit,volumeLimit,sizeLimit);
            truck.startThread();
            gui.setTruckLimits(weightLimit,volumeLimit,sizeLimit);
        }
    }

    public void setTruckStatus(int statusNbr) {
        switch (statusNbr) {
            case 0:
                gui.setTruckDel();
                break;
            case 1:
                gui.setTruckStatus("PACKING TRUCK");
                break;
        }
    }

    public void currentTruckItems(LinkedList<FoodItem> truck) {
        if (truck != null) {
            String str = "";
            for (int i = 0; i < truck.size(); i++) {
                str += truck.get(i).getName()+"\n";
            }
            gui.setTextTruckTextArea(str);
        } else
            gui.clearTruckTextArea();

    }
}
