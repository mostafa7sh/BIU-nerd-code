import java.util.List;
import java.util.Map;

/**
 * Class Mult.
 * represents multiple between two expressions.
 */
public class Mult extends BinaryExpression {
    /**
     * mult constructor
     * 
     * @param leftExpression  the left side.
     * @param rightExpression the right side.
     */
    public Mult(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression, "*");
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return leftExpression.evaluate(assignment) *
                rightExpression.evaluate(assignment);
    }

    @Override
    public double evaluate() throws Exception {
        return leftExpression.evaluate() * rightExpression.evaluate();
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Mult(leftExpression.assign(var, expression),
                rightExpression.assign(var, expression));
    }

    @Override
    public Expression differentiate(String var) {
        return new Plus(
                new Mult(leftExpression.differentiate(var), rightExpression),
                new Mult(leftExpression, rightExpression.differentiate(var)));
    }

    @Override
    public Expression simplify() {
        Expression left = leftExpression.simplify();
        Expression right = rightExpression.simplify();

        try {
            // if both sides are numbers, return result
            if (left instanceof Num && right instanceof Num) {
                return new Num(left.evaluate() * right.evaluate());
            }

            // if either side is 0, return 0
            if ((left instanceof Num && left.evaluate() == 0)
                    || (right instanceof Num && right.evaluate() == 0)) {
                return new Num(0);
            }

            // if left is 1, return right
            if (left instanceof Num && left.evaluate() == 1) {
                return right;
            }

            // if right is 1, return left
            if (right instanceof Num && right.evaluate() == 1) {
                return left;
            }

        } catch (Exception e) {
        }

        return new Mult(left, right);
    }
}
