package Memory;

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

    public Memory(){
        this.memory = new int[2048];
        this.size=2048;
    }

    public void MemoryExpansion(){
        this.memory = new int[4096];
        this.size=4096;
    }

    public int getSize() {
        return size;
    }

    public int[] getAllMemory() {
        return memory;
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
}
