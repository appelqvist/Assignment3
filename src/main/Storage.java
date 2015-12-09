package main;


import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.Semaphore;

/**
 * Created by Andreas Appelqvist on 2015-12-08.
 */

public class Storage {
    LinkedList<FoodItem> storage = new LinkedList<FoodItem>();

    private int maxSize = 20;
    private Semaphore inSem;
    private Semaphore outSem;

    private Controller controller;

    private Object lock = new Object();

    public Storage(Controller controller) {
        this.controller = controller;

        inSem = new Semaphore(1, true); //Skulle vilja sätta denna till 1?
        outSem = new Semaphore(-1);
    }

    public void put(FoodItem item) {


        try {
            inSem.acquire();  //Begär för en plats för att kunna lägga in
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(storage.size() >= maxSize){
        } //Står och väntar tills de finns plats

        synchronized (lock) {
            storage.addFirst(item); //Lägger till i storage
            controller.updateGUIstorageSize(maxSize,storage.size());
        }

        System.err.println("La till: "+item.getName());
        outSem.release(); //Öppnar upp så dem kan hämta i out
        inSem.release(); //Öppnar upp så de finns plats igen.
    }


    public FoodItem get(double maxWeight, double maxVolume, int maxItem) { //Får inte vara större eller tyngre

        try {
            outSem.acquire();  //Begär för en plats om inte vänta
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Fanns item att ta");
        synchronized (lock) {
            if(storage.size() > 0) {
                FoodItem item = storage.getLast();
                if (item.getVolume() <= maxVolume && item.getWeight() <= maxWeight && maxItem >= 1) { //Finns plats i lastbilen
                    storage.removeLast(); //Tar bort elementet sist
                    controller.updateGUIstorageSize(maxSize, storage.size());
                    outSem.release(); //Tar bort en plats (en mindre vara)
                    return item;
                } else {
                    return null;
                }
            }else{
                return null;
            }

        }

    }
}
