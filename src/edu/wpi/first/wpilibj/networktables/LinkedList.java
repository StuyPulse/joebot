/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.networktables;

import java.util.NoSuchElementException;

/**
 *
 * @author Joe Grinstead
 */
class LinkedList {

    Link first;
    Link last;

    Link add(Object data) {
        Link link = new Link(data);

        if (first == null) {
            first = link;
            last = link;
        } else {
            last.add(link);
            last = link;
        }

        return link;
    }

    Object dequeue() {
        if (first == null) {
            throw new NoSuchElementException();
        } else {
            Object value = first.data;
            if (first == last) {
                first = last = first.detach();
            } else {
                first = first.detach();
            }
            return value;
        }
    }

    boolean isEmpty() {
        return first == null;
    }

    void clear() {
        while (first != null) {
            dequeue();
        }
    }

    class Link {

        Object data;
        Link next;
        Link prev;

        private Link(Object data) {
            this.data = data;
        }

        void add(Link link) {
            if (next == null) {
                next = link;
            } else {
                next.prev = link;
                next = link;
            }
        }

        Link detach() {
            Link link = next;

            if (first == this) {
                first = next;
            }
            if (last == this) {
                last = prev;
            }

            if (next != null) {
                next.prev = prev;
            }
            if (prev != null) {
                prev.next = next;
            }

            prev = null;
            data = null;
            next = null;

            return link;
        }
    }
}
