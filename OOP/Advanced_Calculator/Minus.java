import java.util.List;
import java.util.Map;
/**
 * Class Minus.
 * represents minus between two expressions.
 */
public class Minus extends BinaryExpression {
    /**
     * minus constructor.
     * @param leftExpression left side.
     * @param rightExpression right side.
     */
    public Minus(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression, "-");
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double leftNumber = super.leftExpression.evaluate(assignment);
        double rightNumber = super.rightExpression.evaluate(assignment);
        return leftNumber - rightNumber;
    }

    @Override
    public double evaluate() throws Exception {
        double leftNumber = super.leftExpression.evaluate();
        double rightNumber = super.rightExpression.evaluate();
        return leftNumber - rightNumber;
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Minus(super.leftExpression.assign(var, expression), super.rightExpression.assign(var, expression));
    }

    @Override
    public Expression differentiate(String var) {
        return new Minus(super.leftExpression.differentiate(var), super.rightExpression.differentiate(var));
    }

    @Override
    public Expression simplify() {
        Expression leftExpression = super.leftExpression.simplify();
        Expression rightExpression = super.rightExpression.simplify();
        // check if two sides are numbers.
        if (leftExpression instanceof Num && rightExpression instanceof Num) {
            try {
                double newForm = leftExpression.evaluate() - rightExpression.evaluate();
                return new Num(newForm);
            } catch (Exception exception) {
                return new Minus(leftExpression, rightExpression);
            }
            // check if left side was a number.
        } else if (leftExpression instanceof Num) {
            try {
                if (leftExpression.evaluate() == 0) {
                    return new Neg(rightExpression);
                }
            } catch (Exception exception) {
                return new Minus(rightExpression, leftExpression);
            }
            // check if right side was a number.
        } else if (rightExpression instanceof Num) {
            try {
                if (rightExpression.evaluate() == 0) {
                    return leftExpression;
                } else if (rightExpression.evaluate() < 0) {
                    return new Plus(leftExpression, new Neg(rightExpression));
                }
            } catch (Exception exception) {
                return new Minus(rightExpression, leftExpression);
            }
        }
        try {
            Expression yo = new Minus(leftExpression, rightExpression);
            if (yo.equals()) {
                return new Num(0);
            }
        } catch (Exception exception) {
            return new Minus(rightExpression, leftExpression);
        }
        return new Minus(rightExpression, leftExpression);
    }
}
