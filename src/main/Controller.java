package main;

/**
 * Created by Andreas Appelqvist on 2015-12-08.
 */
public class Controller {

    private GUISemaphore gui;
    private Storage storage;
    private Factory[] factories = new Factory[2];
    private Truck truck;

    public Controller(GUISemaphore gui){
        this.gui = gui;
        this.storage = new Storage(this);
        gui.setController(this);
    }

    public void updateGUIstorageSize(int maxSize, int currentSize){
        float percent = (currentSize*100/maxSize);
        gui.updateStorageSize(percent);
    }

    public void startFactory(int thread){
        if(factories[thread] == null){
            factories[thread] = new Factory(storage);
            factories[thread].startThread();
        }
    }

    public void stopFactory(int thread){
        factories[thread].stopThread();
        factories[thread] = null;
    }

    public void startDelivery(){
        if(truck == null){
            truck = new Truck(storage);
            truck.startThread();
        }
    }

    public void setTruckStatus(int statusNbr){

    }

}
