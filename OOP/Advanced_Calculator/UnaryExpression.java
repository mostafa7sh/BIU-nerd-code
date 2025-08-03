import java.util.LinkedList;
import java.util.List;

public abstract class UnaryExpression extends BaseExpression {
    protected Expression expression;

    public UnaryExpression(Expression expression, String operator) {
        super(operator);
        this.expression = expression;
    }

    @Override
    public List<String> getVariables() {
        List<String> oneSideVariables = this.expression.getVariables();
        List<String> allVariables = new LinkedList<String>();
        for (String var : oneSideVariables) {
            if (!allVariables.contains(var)) {
                allVariables.add(var);
            }
        }
        return allVariables;
    }

    @Override
    public String toString() {
        return this.operator + "(" + this.expression + ")";
    }
}
