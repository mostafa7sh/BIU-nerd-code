import java.util.List;
import java.util.Map;

public class Sin extends UnaryExpression {

    public Sin(Expression expression) {
        super(expression, "sin");
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return Math.sin(expression.evaluate(assignment));
    }

    @Override
    public double evaluate() throws Exception {
        return Math.sin(expression.evaluate());
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Sin(expression.assign(var, expression));
    }

    @Override
    public Expression differentiate(String var) {
        Expression innerDerivative = expression.differentiate(var);
        return new Mult(new Cos(expression), innerDerivative);
    }

    @Override
    public Expression simplify() {
        Expression single = expression.simplify();
        if (single instanceof Num) {
            try {
                return new Num(Math.sin(Math.toRadians(single.evaluate())));
            } catch (Exception exception) {}
        }
        return new Sin(single);
    }
}
