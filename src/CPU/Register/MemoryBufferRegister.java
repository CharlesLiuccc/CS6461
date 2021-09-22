package CPU.Register;

/***
 * This is the class for Memory Buffer Register
 * MBR just has 16 bits in length
 * MBR has 1 more member function: getFromMem()
 *
 * @author Charles
 */

public class MemoryBufferRegister extends Register{
    public MemoryBufferRegister(){
        super(16,0,"MBR");
    }

    public int getFromMem(int address){
        return 0;
    }
}
