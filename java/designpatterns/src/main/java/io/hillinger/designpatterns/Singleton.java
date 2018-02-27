package io.hillinger.designpatterns;

import java.util.Random;

public class Singleton {
    private static volatile Singleton instance;
    private static int id;
    private static Object mutex = new Object();
    
    private Singleton() {}
    
    public int getId() { return id; }
    
    public static Singleton getInstance() {
        Singleton result = instance;
        
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                
                if (result == null) {
                    instance = result = new Singleton();
                    id = new Random(Long.MAX_VALUE).nextInt();
                }
            }
        }
        return instance;
    }
}
