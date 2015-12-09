package main;


import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.Semaphore;

/**
 * Created by Andreas Appelqvist on 2015-12-08.
 */

public class Storage {
    LinkedList<FoodItem> storage = new LinkedList<FoodItem>();
    private int maxSize = 10;

    private Semaphore inSem;
    private Semaphore outSem;

    private Object lock = new Object();

    public Storage() {
        inSem = new Semaphore(2, true); //Skulle vilja sätta denna till 1?
        outSem = new Semaphore(0);
    }

    public void put(FoodItem item) {
        try {
            inSem.acquire();  //Begär för en plats för att kunna lägga in
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (lock) {
            storage.addFirst(item); //Lägger till i storage
        }

        System.out.println("La till: "+item.getName());
        outSem.release(); //Öppnar upp så dem kan hämta i out
        inSem.release(); //Öppnar upp så de finns plats igen.
    }


    public FoodItem get(double maxWeight, double maxVolume) { //Får inte vara större eller tyngre

        try {
            outSem.acquire();  //Begär för en plats om inte vänta
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (lock) {
            FoodItem item = storage.getLast();
            if ((item.getVolume() <= maxVolume && item.getWeight() <= maxWeight) && storage.size() <= maxSize) { //Finns plats i lastbilen
                storage.removeLast(); //Tar bort elementet sist
                inSem.release(); //Tar bort en plats (en mindre vara)
                return item;
            } else {
                return null;
            }
        }

    }
}
