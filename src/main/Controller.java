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
        this.storage = new Storage();
        factories[0] = new Factory(storage);
        factories[1] = new Factory(storage);
        truck = new Truck(storage,20.0,15.0);

        startFactory(0);
        startFactory(1);
        truck.startThread();
        System.out.println("Färdigt");
    }

    public void startFactory(int thread){
        factories[thread].startThread();
    }

    public void stopFactory(int thread){
        factories[thread].stopThread();
    }
}
