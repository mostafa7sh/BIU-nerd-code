import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class BinaryExpression.
 * represents every binary expression.
 */
public abstract class BinaryExpression extends BaseExpression {
    /**
     * the left expression.
     */
    protected Expression leftExpression;
    /**
     * the right expression.
     */
    protected Expression rightExpression;

    /**
     * binary expression constructor.
     * @param left the left expression.
     * @param right the right expression.
     * @param operator the operator between the two expression.
     */
    public BinaryExpression(Expression left, Expression right, String operator) {
        super(operator);
        this.leftExpression = left;
        this.rightExpression = right;
    }
    @Override
    public List<String> getVariables() {
        List<String> leftVariables = this.leftExpression.getVariables();
        List<String> rightVariables = this.rightExpression.getVariables();
        List<String> allVariables = new LinkedList<String>();
        for (String var : rightVariables) {
            if (!allVariables.contains(var)) {
                allVariables.add(var);
            }
        }
        for (String var : leftVariables) {
            if (!allVariables.contains(var)) {
                allVariables.add(var);
            }
        }
        return allVariables;
    }

    @Override
    public String toString() {
        return "(" + this.leftExpression + " " + this.operator + " " + this.rightExpression + ")";
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return null;
    }

    @Override
    public Expression differentiate(String var) {
        return null;
    }

    @Override
    public Expression simplify() {
        return null;
    }

    @Override
    public boolean equals() {
        Expression leftSimplified = this.leftExpression.simplify();
        Expression rightSimplified = this.rightExpression.simplify();
        String leftString = leftSimplified.toString();
        String rightString = rightSimplified.toString();
        List<String> allVariables = this.getVariables();
        if (leftString.equals(rightString)) {
            return true;
        }
        try {
            if (this.checkWithAssign(allVariables, 1.0) &&
                this.checkWithAssign(allVariables, 10.0) &&
                this.checkWithAssign(allVariables, 7.0) &&
                this.checkWithAssign(allVariables, 25.0)) {
                return true;
            }
        } catch (Exception exception) {
            return false;
        }
        return false;
    }

    /**
     * a very useful function that helps me check if two sides of the expression are equal,
     * I pass to all the variables random values, and check if too sides behave like each other,
     * if we get the same value no matter what we assign, then they are equal,
     * otherwise return false.
     * @param variables a list of all the variables in the expression.
     * @param start the starting number to
     * @return true if left and right expression behave like each other, false otherwise.
     * @throws Exception throw error if something unwanted occurs.
     */
    public boolean checkWithAssign(List<String> variables, double start) throws Exception {
        Map<String, Double> assignment = new TreeMap<String, Double>();
        for (String var : variables) {
            assignment.put(var, start);
            start++;
        }
        try {
            double leftValue = this.leftExpression.evaluate(assignment);
            double rightValue = this.rightExpression.evaluate(assignment);
            return leftValue == rightValue;
        } catch (Exception exception) {
            return false;
        }
    }
}
