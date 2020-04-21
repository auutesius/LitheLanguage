package Lithe;
import static Lithe.Parser.rule;
import Lithe.ast.Fun;

public class ClosureParser extends FuncParser {
    public ClosureParser() {
        primary.insertChoice(rule(Fun.class)
                                 .sep("fun").ast(paramList).ast(block));
    }
}
