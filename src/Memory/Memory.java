package Memory;
import CPU.Register.MemoryAddressRegister;
import Memory.Cache;
import java.util.ArrayList;
import Memory.Cache.CacheLine;

import java.util.Map;

import java.io.*;
import java.util.*;

/***
 * This is the class for Memory.
 * Memory can be set and fetched by MBR.
 * The initial size of memory is 2048 and can be expanded to 4069
 *
 * @author Charles
 */

public class Memory {


    private int[] memory;
    private int size;

    /**
     * 16 block fully associative, unified cache
     */
    Cache cache;

    public Cache getCache() {
        return cache;
    }

    /**
     * initialize the Memory size to 2048.
     */
    public Memory(){
        this.memory = new int[2048];
        this.size=2048;
        this.cache = new Cache();
    }

    //expand the memory size to 4096
    public void MemoryExpansion(){
        this.memory = new int[4096];
        this.size=4096;
    }

    public int getSize() {
        return size;
    }

    public int[] getAllMemory() {
        return this.memory;
    }


    public int getFromMemory(int address){
        return this.memory[address];
    }

    public void setToMemory(int address,int value){
        this.memory[address]=value;
    }

    public void resetMemory(){
        this.memory = new int[2048];
    }

    //Store directly into memory using address and value with memory simultaneously
    public void setToCache(int address,int value) {

        setToMemory(address,value); //the data gets store in memory
        for (Cache.CacheLine line : cache.getCacheLines()) { // check every block the
            // tag is already exist
            if (address == line.getTag()) {
                line.setData(value); // replace the block
                return;
            }
        }
        // tag does not exist
        cache.add(address,value);


    }




}



