package main;


import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.Semaphore;

/**
 * Created by Andreas Appelqvist on 2015-12-08.
 *
 * Representerar ett lager som håller i FoodItem-object
 *
 */
public class Storage {
    LinkedList<FoodItem> storage = new LinkedList<FoodItem>();

    private int maxSize = 30;
    private Semaphore inSem;
    private Semaphore outSem;

    private Controller controller;

    private Object lock = new Object();

    /**
     * Initisering
     * @param controller
     */
    public Storage(Controller controller) {
        this.controller = controller;
        inSem = new Semaphore(maxSize, true);
        outSem = new Semaphore(0);
    }

    /**
     * Lägger in ett FoodItem-object i bufferten(lagret)
     * @param item
     */
    public void put(FoodItem item) {
        try {
            inSem.acquire();  //Begär för en plats för att kunna lägga in
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (lock) {
            storage.addFirst(item); //Lägger till i storage
            controller.updateGUIstorageSize(maxSize, storage.size());
        }

        System.err.println("La till: " + item.getName());
        outSem.release(); //Öppnar upp så dem kan hämta i out
        printSemAndSize();

    }

    /**
     * Print SystemLn (Size, Sem) **TEST**
     */
    public void printSemAndSize(){
        System.out.println("SEMOUT: "+outSem.availablePermits());
        System.out.println("SIZE: "+storage.size());
    }

    /**
     *
     * Hämtar ett FoodItem-obj från bufferten(lagret)
     *
     * @param maxWeight
     * @param maxVolume
     * @param maxItem
     * @return
     */
    public FoodItem get(double maxWeight, double maxVolume, int maxItem) { //Får inte vara större eller tyngre
        try {
            outSem.acquire();  //Begär för en plats om inte vänta
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        while (true) {
            synchronized (lock){
                if(storage.size() > 0){
                    break;
                }
            }
        }

        synchronized (lock) {
            FoodItem item = storage.getLast();
            if ((item.getVolume() <= maxVolume) && (item.getWeight() <= maxWeight) && (maxItem >= 1) || storage.size() == 0 ) { //Finns plats i lastbilen
                storage.removeLast(); //Tar bort elementet sist
                controller.updateGUIstorageSize(maxSize, storage.size());
                printSemAndSize();
                return item;
            } else {
                inSem.release();
                printSemAndSize();
                return null;
            }
        }

    }
}

