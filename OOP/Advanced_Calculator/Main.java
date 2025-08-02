
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws Exception {
        Expression firstStepBro = new Sin(new Sin(new Div(new Num(1), new Var("x"))));
        Expression secondStepBro = new Pow(new Mult(new Mult(new Num(5), new Var("x")), firstStepBro), new Num(5));
        System.out.println(secondStepBro);
        Expression dershadi = secondStepBro.differentiate("x");
        System.out.println(dershadi);
        System.out.println(dershadi.simplify());
    }
}