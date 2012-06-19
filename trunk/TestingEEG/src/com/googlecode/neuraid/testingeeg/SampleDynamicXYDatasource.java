package com.googlecode.neuraid.testingeeg;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
 
public class SampleDynamicXYDatasource implements Runnable {
 
   // encapsulates management of the observers watching this datasource for update events:
   class MyObservable extends Observable {
   @Override
   public void notifyObservers() {
       setChanged();
       super.notifyObservers();
   }
}
 
   private static final int SAMPLE_SIZE = 2000;
   private ArrayList<Number> ys;
   private MyObservable notifier;
   private boolean updated;
 
   {
       notifier = new MyObservable();
   }
 
   //@Override
   public void run() {
       try {
           updated = false;
           ys = new ArrayList<Number>();
           for (int i = 0; i < SAMPLE_SIZE; i++) {
        	   ys.add(0);
           }
           while (true) {
 
               Thread.sleep(10); // decrease or remove to speed up the refresh rate.
        	   notifier.notifyObservers();
//               synchronized(this) {
//               if (updated) {
//            	   notifier.notifyObservers();
//            	   updated = false;
//               }
//               }
           }
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
   }
 
   public int getItemCount(int series) {
       return SAMPLE_SIZE;
   }
 
   public Number getX(int series, int index) {
       if (index >= SAMPLE_SIZE) {
           throw new IllegalArgumentException();
       }
       return index;
   }
 
   public Number getY(int series, int index) {
        if (index >= SAMPLE_SIZE) {
            throw new IllegalArgumentException();
        } else if (index >= ys.size()) {
        	return 0;
        }
        return ys.get(index);
   }
 
   public synchronized void setY(int y) {
	   ys.add(y);
	   if (ys.size() > SAMPLE_SIZE) {
		   ys.remove(0);
	   }
	   updated = true;
   }
   
   public void addObserver(Observer observer) {
       notifier.addObserver(observer);
   }
 
   public void removeObserver(Observer observer) {
       notifier.deleteObserver(observer);
   }
 
}