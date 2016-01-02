package main;

import java.util.LinkedList;

/**
 * Created by Andreas Appelqvist on 2015-12-08.
 *
 * Klassen sköter logiken i programmet.
 *
 */
public class Controller {

    private GUISemaphore gui;
    private Storage storage;
    private Factory[] factories = new Factory[2];
    private Truck truck;

    /**
     * Initisering
     * @param gui
     */
    public Controller(GUISemaphore gui) {
        this.gui = gui;
        this.storage = new Storage(this);
        gui.setController(this);
    }

    /**
     *
     * Uppdaterar gui med storleken på bufferten
     *
     * @param maxSize
     * @param currentSize
     */
    public void updateGUIstorageSize(int maxSize, int currentSize) {
        float percent = (currentSize * 100 / maxSize);
        gui.updateStorageSize(percent);
    }

    /**
     * Startar en fabrik.
     *
     * @param thread vilken fabrik som ska startas
     */
    public void startFactory(int thread) {
        if (factories[thread] == null) {
            factories[thread] = new Factory(storage);
            factories[thread].setName(""+thread);
            factories[thread].startThread();
            updateFactoryStatus(thread,true);
        }
    }

    /**
     * Stannar en fabrik.
     *
     * @param thread vilken fabrik som ska stannas
     */
    public void stopFactory(int thread) {
        factories[thread].stopThread();
        updateFactoryStatus(thread,false);
        factories[thread] = null;
    }

    /**
     *
     * Uppdaterar gui om en fabrik jobbar eller inte.
     *
     * @param factory
     * @param working
     */
    public void updateFactoryStatus(int factory, boolean working){
        gui.setFactoryStatusWorking(factory,working);
    }

    /**
     *
     * Startar Lastbilen med angivna limits om hur mycket
     * lastbilen klarar av.
     *
     */
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

    /**
     *
     * Uppdatera gui om lastbilsstatus
     *
     * 0 = Lastbil är på leverans
     * 1 = Lastbil lastas
     *
     * @param statusNbr
     */
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

    /**
     * Updaterar gui med vad som finns i lastbilen
     * @param truck lista på alla varor i lastbilen
     */
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
