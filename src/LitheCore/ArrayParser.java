package LitheXCore;
import static LitheXCore.Parser.rule;
import javassist.gluonj.Reviser;
import LitheXCore.ast.*;

@Reviser public class ArrayParser extends FuncParser {
    Parser elements = rule(ArrayLiteral.class)
                          .ast(expr).repeat(rule().sep(",").ast(expr));
    public ArrayParser() {
        reserved.add("]");
        primary.insertChoice(rule().sep("[").maybe(elements).sep("]"));
        postfix.insertChoice(rule(ArrayRef.class).sep("[").ast(expr).sep("]"));
    }
}
