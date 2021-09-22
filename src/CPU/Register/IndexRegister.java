package CPU.Register;

/***
 * We will have 3 Index Register in our simulator: X1, X2, X3
 * Each Index Register will have 16 bits in length
 *
 * @author Charles
 */

public class IndexRegister extends Register{
    public IndexRegister(int value){
        super(16,value,"IXR");
    }
}
