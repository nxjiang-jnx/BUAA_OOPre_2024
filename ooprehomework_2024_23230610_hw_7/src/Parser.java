public class Parser {
    private final Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Expr parseExpr() {
        Expr expr = new Expr();
        /*TODO(4/8): 实现“表达式”的解析逻辑，可以参考 Parser.parseSubExpr() 的实现方式*/
        expr.addTerm(parseTerm());
        while (!lexer.isEnd() && lexer.getCurToken().getType() == Token.Type.ADD) {
            lexer.nextToken();
            expr.addTerm(parseTerm());
        }
        expr.print();
        return expr;
    }

    public Term parseTerm() {
        Term term = new Term();
        /*TODO(5/8): 实现“项”的解析逻辑，可以参考 Parser.parseSubTerm() 的实现方式*/
        term.addFactor(parseFactor());
        while (!lexer.isEnd() && lexer.getCurToken().getType() == Token.Type.MUL) {
            lexer.nextToken();
            term.addFactor(parseFactor());
        }
        term.print();
        return term;
    }

    public Factor parseFactor() {
        Token token = lexer.getCurToken();
        if (token.getType() == Token.Type.NUM) {
            return parseNum();
        } else if (token.getType() == Token.Type.VAR) {
            return parseVar();
        } else {
            /*TODO(6/8): 解析“因子”中的“表达式因子”这一情况，注意“表达式因子”的文法：表达式因子 → '(' 子表达式 ')'，因此请注意左右括号对解析的影响*/
            /*你需要做的：跳过左括号；解析“子表达式”；跳过右括号；返回结果*/
            //跳过左括号
            lexer.nextToken();
            //解析“子表达式”
            SubExpr expr = parseSubExpr();
            //跳过右括号
            lexer.nextToken();
            return expr;
        }
    }

    public SubExpr parseSubExpr() {
        SubExpr subExpression = new SubExpr();
        subExpression.addTerm(parseSubTerm());
        while (!lexer.isEnd() && lexer.getCurToken().getType() == Token.Type.ADD) {
            lexer.nextToken();
            subExpression.addTerm(parseSubTerm());
        }
        subExpression.print();
        return subExpression;
    }

    public SubTerm parseSubTerm() {
        SubTerm subTerm = new SubTerm();
        subTerm.addFactor(parseNum());
        while (/*TODO(7/8)*/ !lexer.isEnd() && lexer.getCurToken().getType() == Token.Type.MUL) {
            lexer.nextToken();
            subTerm.addFactor(parseNum());
        }
        subTerm.print();
        return subTerm;
    }

    public Num parseNum() {
        Num num;
        /*TODO(8/8): 实现“常数因子”的解析逻辑，可以参考 Parser.parseVar() 的实现方式*/
        Token token = lexer.getCurToken();
        lexer.nextToken();
        num = new Num(Integer.parseInt(token.getContent()));
        num.print();
        return num;
    }

    public Var parseVar() {
        Token token = lexer.getCurToken();
        lexer.nextToken();
        Var var = new Var(token.getContent());
        var.print();
        return var;
    }
}
