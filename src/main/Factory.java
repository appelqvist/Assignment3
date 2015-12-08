package main;

/**
 * Created by Andreas Appelqvist on 2015-12-08.
 */
public class Factory extends Thread {
    private Storage storage;
    private FoodItem[] items = new FoodItem[20];
    private boolean running;

    public Factory(Storage storage) {
        this.storage = storage;
        initItems();
    }

    public void startThread() {
        running = true;
        this.start();
    }

    public void stopThread() {
        running = false;
    }

    @Override
    public void run() {
        super.run();
        while (true) {              //kanske inte såhär.??
            if (running) {
                storage.put(randomFood());

                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private FoodItem randomFood() {
        int rand = (int) (Math.random()*20);
        return items[rand];
    }

    private void initItems() {
        items[0] = new FoodItem("Julmust", 1.1, 0.5);
        items[1] = new FoodItem("Banan", 0.9, 0.2);
        items[2] = new FoodItem("Kaffe", 0.3, 0.3);
        items[3] = new FoodItem("Pizza", 0.9, 1.5);
        items[4] = new FoodItem("Mjölk", 1.0, 0.3);

        items[5] = new FoodItem("Bröd", 0.3, 1.2);
        items[6] = new FoodItem("Kött", 2.0, 1.0);
        items[7] = new FoodItem("Ris", 1.2, 1.0);
        items[8] = new FoodItem("Äpple", 0.3, 0.9);
        items[9] = new FoodItem("Kyckling", 2.1, 2.0);

        items[10] = new FoodItem("Köttfärs", 1.8, 0.2);
        items[11] = new FoodItem("Godis", 0.4, 0.5);
        items[12] = new FoodItem("Öl", 0.6, 0.5);
        items[13] = new FoodItem("Toapapper", 0.2, 1.3);
        items[14] = new FoodItem("Disktrasa", 0.4, 0.2);

        items[15] = new FoodItem("Pasta", 1.1, 0.8);
        items[16] = new FoodItem("Köttbullar", 1.1, 0.5);
        items[17] = new FoodItem("Lax", 1.0, 0.7);
        items[18] = new FoodItem("Korv", 0.7, 0.7);
        items[19] = new FoodItem("Sallad", 0.5, 1.2);


    }
}
