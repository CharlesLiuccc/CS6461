package CPU.Register;

/***
 * We will have 4 GPR in our simulator: GPR0, GPR1, GPR2, GPR3
 * Each GPR will have 16 bits in length
 *
 * @author Charles
 */

public class GeneralPurposeRegister extends Register{
    public GeneralPurposeRegister(int value) {
        super(16,value,"GPR");
    }
}
