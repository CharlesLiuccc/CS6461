package CPU.Register;

import Memory.Memory;
import Memory.Cache;

/***
 * This is the class for Memory Buffer Register
 * MBR just has 16 bits in length
 * MBR has 2 more member functions: getFromMem() and storeToMem()
 * MBR can just use the address in MAR to interact with Memory
 *
 * @author Charles
 */

public class MemoryBufferRegister extends Register{
    public MemoryBufferRegister(){
        super(16,0,"MBR");
    }

    public void getFromMem(MemoryAddressRegister mar, Memory mem){
       this.setValue(mem.getFromMemory(mar.getValue()));
       //System.out.println(this.getValue());
    }

    //stores to cache
    //not implemented
    //just store the value directly to memory
    //this function is used when we need to store the value in mbr to cache
    public void setToMem(MemoryAddressRegister mar, Memory mem){
        mem.setToMemory(mar.getValue(),this.getValue());
        //mem.setToCache(mar.getValue(),this.getValue());
    }

}
