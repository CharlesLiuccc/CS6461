package CPU.Register;

/***
 * This is the class for Machine Fault Register
 * MFR just has 4 bits in length
 *
 * @author Charles
 */

public class MachineFaultRegister extends Register{
    public MachineFaultRegister(int value){
        super(4,value,"MFR");
    }
}
