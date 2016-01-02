package main;

/**
 * Created by Andreas Appelqvist on 2015-12-08.
 *
 * Klass som startar programmet
 *
 */
public class Start {
    public static void main(String[] args)
    {
        GUISemaphore test = new GUISemaphore();
        test.start();

        new Controller(test);
    }
}
