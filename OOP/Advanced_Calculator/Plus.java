import java.util.List;
import java.util.Map;

/**
 * Class Plus.
 * Represents addition between two expressions.
 */
public class Plus extends BinaryExpression {
    public Plus(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression, "+");
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return leftExpression.evaluate(assignment) + rightExpression.evaluate(assignment);
    }

    @Override
    public double evaluate() throws Exception {
        return leftExpression.evaluate() + rightExpression.evaluate();
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Plus(leftExpression.assign(var, expression), rightExpression.assign(var, expression));
    }

    @Override
    public Expression differentiate(String var) {
        return new Plus(leftExpression.differentiate(var), rightExpression.differentiate(var));
    }

    @Override
    public Expression simplify() {
        Expression left = leftExpression.simplify();
        Expression right = rightExpression.simplify();

        try {
            // Num + Num → constant
            if (left instanceof Num && right instanceof Num) {
                double sum = left.evaluate() + right.evaluate();
                return new Num(sum);
            }

            // 0 + x → x
            if (left instanceof Num && left.evaluate() == 0) {
                return right;
            }

            // x + 0 → x
            if (right instanceof Num && right.evaluate() == 0) {
                return left;
            }
        } catch (Exception e) {

        }

        // x + x → 2 * x
        if (left.equals(right)) {
            return new Mult(new Num(2), left);
        }

        return new Plus(left, right);
    }
}
