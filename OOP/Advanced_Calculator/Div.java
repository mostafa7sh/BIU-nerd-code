import java.util.List;
import java.util.Map;

/**
 * Class Div.
 * represents a division between two expressions.
 */
public class Div extends BinaryExpression {
    /**
     * div constructor.
     * 
     * @param leftExpression  the numerator.
     * @param rightExpression the denominator.
     */
    public Div(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression, "/");
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double left = leftExpression.evaluate(assignment);
        double right = rightExpression.evaluate(assignment);
        if (right == 0) {
            throw new RuntimeException("you cant divide a number with zero");
        }
        return left / right;
    }

    @Override
    public double evaluate() throws Exception {
        double left = leftExpression.evaluate();
        double right = rightExpression.evaluate();
        if (right == 0) {
            throw new RuntimeException("you cant divide a number with zero");
        }
        return left / right;
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Div(leftExpression.assign(var, expression),
                rightExpression.assign(var, expression));
    }

    @Override
    public Expression differentiate(String var) {
        Expression f = leftExpression;
        Expression g = rightExpression;
        Expression fPrime = f.differentiate(var);
        Expression gPrime = g.differentiate(var);
        Expression numerator = new Minus(new Mult(fPrime, g), new Mult(f, gPrime));
        Expression denominator = new Pow(g, new Num(2));
        return new Div(numerator, denominator);
    }

    @Override
    public Expression simplify() {
        Expression left = leftExpression.simplify();
        Expression right = rightExpression.simplify();

        try {
            // both are numbers
            if (left instanceof Num && right instanceof Num) {
                double num = left.evaluate();
                double den = right.evaluate();
                if (den == 0) {
                    throw new RuntimeException("Cannot divide by zero");
                }
                return new Num(num / den);
            }

            // x / 1 → x
            if (right instanceof Num && right.evaluate() == 1) {
                return left;
            }

            // 0 / x → 0 (x ≠ 0)
            if (left instanceof Num && left.evaluate() == 0) {
                return new Num(0);
            }

            // x / x → 1 (only if equal expressions)
            if (left.equals(right)) {
                return new Num(1);
            }

        } catch (Exception e) {}

        return new Div(left, right);
    }
}
