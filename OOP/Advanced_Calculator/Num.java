import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Num implements Expression{
    private double number;
    public Num(double number) {
        this.number = number;
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        try {
            return this.number;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public double evaluate() throws Exception {
        return this.number;
    }

    @Override
    public List<String> getVariables() {
        return new LinkedList<String>();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return this;
    }

    @Override
    public Expression differentiate(String var) {
        return new Num(0);
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public boolean equals() {
        return true;
    }

    @Override
    public String toString() {
        return Double.toString(this.number);
    }
}
