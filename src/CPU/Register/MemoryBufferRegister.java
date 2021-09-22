package CPU.Register;

import Memory.Memory;

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
    }

    public void storeToMem(MemoryAddressRegister mar, Memory mem){
        mem.setToMemory(mar.getValue(),this.getValue());
    }
}
