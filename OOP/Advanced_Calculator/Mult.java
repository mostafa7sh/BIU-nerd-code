import java.util.List;
import java.util.Map;
/**
 * Class Mult.
 * represents multiple between two expressions.
 */
public class Mult extends BinaryExpression {
    /**
     * mult constructor
     * @param leftExpression the left side.
     * @param rightExpression the right side.
     */
    public Mult(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression, "*");
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double leftNumber = super.leftExpression.evaluate(assignment);
        double rightNumber = super.rightExpression.evaluate(assignment);
        return leftNumber * rightNumber;
    }

    @Override
    public double evaluate() throws Exception {
        double leftNumber = super.leftExpression.evaluate();
        double rightNumber = super.rightExpression.evaluate();
        return leftNumber * rightNumber;
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Mult(super.leftExpression.assign(var, expression), super.rightExpression.assign(var, expression));
    }

    @Override
    public Expression differentiate(String var) {
        Expression leftDifferentiate = super.leftExpression.differentiate(var);
        Expression rightDifferentiate = super.rightExpression.differentiate(var);
        return new Plus(new Mult(leftDifferentiate, super.rightExpression),
                new Mult(super.leftExpression, rightDifferentiate));
    }

    @Override
    public Expression simplify() {
        Expression leftExpression = super.leftExpression.simplify();
        Expression rightExpression = super.rightExpression.simplify();
        // check if two sides are numbers.
        if (leftExpression instanceof Num && rightExpression instanceof Num) {
            try {
                if (leftExpression.evaluate() == 0 || rightExpression.evaluate() == 0) {
                    return new Num(0);
                }
                return new Num(leftExpression.evaluate() * rightExpression.evaluate());
            } catch (Exception exception) {
                return new Mult(leftExpression, rightExpression);
            }
            // check if left side was a number.
        } else if (leftExpression instanceof Num) {
            try {
                if (leftExpression.evaluate() == 0) {
                    return new Num(0);
                } else if (leftExpression.evaluate() == 1) {
                    return rightExpression;
                }
            } catch (Exception exception) {
                return new Mult(leftExpression, rightExpression);
            }
            // check right side if was a number.
        } else if (rightExpression instanceof Num) {
            try {
                if (rightExpression.evaluate() == 0) {
                    return new Num(0);
                } else if (rightExpression.evaluate() == 1) {
                    return leftExpression;
                }
            } catch (Exception exception) {
                return new Mult(leftExpression, rightExpression);
            }
        }
        return new Mult(leftExpression, rightExpression);
    }
}
