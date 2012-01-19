/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.networktables;

import java.util.Hashtable;
import java.util.NoSuchElementException;

/**
 *
 * @author Joe Grinstead
 */
class CountingQueue {

    Hashtable counts = new Hashtable();
    LinkedList queue = new LinkedList();

    // null will start and end a transaction block
    public synchronized void offer(Entry entry) {
        if (entry == null) {
            queue.add(null);
        } else {
            Counter counter = (Counter) counts.get(entry.getKey());
            if (counter == null) {
                counter = new Counter();
                counts.put(entry.getKey(), counter);
            }

            counter.increment();
            queue.add(entry);
        }
    }

    public synchronized Entry poll() {
        if (isEmpty()) {
            throw new NoSuchElementException("Nothing left in the queue");
        }

        Entry entry = (Entry) queue.dequeue();

        if (entry != null) {
            ((Counter) counts.get(entry.getKey())).decrement();
        }

        return entry;
    }

    public synchronized boolean containsKey(Key key) {
        Counter counter = (Counter) counts.get(key);
        return counter != null && counter.value() > 0;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    private static class Counter {

        private int value = 0;

        void increment() {
            value++;
        }

        void decrement() {
            value--;
        }

        int value() {
            return value;
        }
    }
}
