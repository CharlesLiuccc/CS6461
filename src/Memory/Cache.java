package Memory;
import Memory.Memory;

import java.io.IOException;
import java.util.LinkedList;



/***
 * This is the class for the Simple Cache.
 * which sits between memory and the rest of the processor.
 * The cache is just a vector having a format similar to that described in the lecture notes.
 * It should be a fully associative, unified cache.
 * simple FIFO algorithm to replace cache lines.
 *
 * @author Christian A. Garcia
 */

public class Cache {

    public class CacheLine {

        int tag;
        int data;

        public CacheLine(int tag, int data) {
            this.tag = tag;
            this.data = data;
        }

        public int getTag() {
            return this.tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }
    }

    LinkedList<CacheLine> cacheLines;

    public Cache() {

        this.cacheLines = new LinkedList<CacheLine>();
    }

    public LinkedList<CacheLine> getCacheLines()
    {
        return cacheLines;
    }

    public void add(int address, int value) {
        if (this.cacheLines.size() >= 16) { //set the cache size to 16 lines
            this.cacheLines.removeLast();
        }
        this.cacheLines.addFirst(new CacheLine(address, value));
    }







}
