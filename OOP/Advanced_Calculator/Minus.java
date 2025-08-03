import java.util.List;
import java.util.Map;

/**
 * Class Minus.
 * represents minus between two expressions.
 */
public class Minus extends BinaryExpression {
    /**
     * minus constructor.
     * 
     * @param leftExpression  left side.
     * @param rightExpression right side.
     */
    public Minus(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression, "-");
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return leftExpression.evaluate(assignment) - rightExpression.evaluate(assignment);
    }

    @Override
    public double evaluate() throws Exception {
        return leftExpression.evaluate() - rightExpression.evaluate();
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Minus(leftExpression.assign(var, expression), rightExpression.assign(var, expression));
    }

    @Override
    public Expression differentiate(String var) {
        return new Minus(leftExpression.differentiate(var), rightExpression.differentiate(var));
    }

    @Override
    public Expression simplify() {
        Expression left = leftExpression.simplify();
        Expression right = rightExpression.simplify();

        try {
            // constant folding: a - b → Num(a - b)
            if (left instanceof Num && right instanceof Num) {
                return new Num(left.evaluate() - right.evaluate());
            }

            // x - 0 → x
            if (right instanceof Num && right.evaluate() == 0) {
                return left;
            }

            // 0 - x → -x
            if (left instanceof Num && left.evaluate() == 0) {
                return new Neg(right);
            }

            // x - x → 0
            if (left.equals(right)) {
                return new Num(0);
            }

        } catch (Exception e) {}

        return new Minus(left, right);
    }
}
