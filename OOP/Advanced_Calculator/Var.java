import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Var implements Expression {
    private String variable;

    public Var(String variable) {
        this.variable = variable;
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        if (assignment.get(this.variable) != null) {
            return assignment.get(this.variable);
        }
        throw new RuntimeException("variable is empty");
    }

    @Override
    public double evaluate() throws Exception {
        throw new RuntimeException("value is empty");
    }

    @Override
    public List<String> getVariables() {
        List<String> list = new LinkedList<>();
        list.add(this.variable);
        return list;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        if (this.variable.equals(var)) {
            return expression;
        }
        return this;
    }

    @Override
    public Expression differentiate(String var) {
        if (this.variable.equals(var)) {
            return new Num(1);
        }
        return new Num(0);
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public boolean equals() {
        return false;
    }

    @Override
    public String toString() {
        return this.variable;
    }
}
