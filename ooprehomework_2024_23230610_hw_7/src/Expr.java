import java.util.ArrayList;

public class Expr {
    private final ArrayList<Term> terms = new ArrayList<>();

    public void addTerm(Term term) {
        terms.add(term);
    }

    @Override
    public String toString() {
        /*TODO(1/8): 可以参考 Term.toString() 实现相应功能*/
        StringBuilder sb = new StringBuilder();
        for (Term term : terms) {
            sb.append(term.toString());
            sb.append("+");
        }
        return sb.substring(0, sb.length() - 1); // remove the last "+"
    }

    public void print() {
        System.out.println("Expr " + this);
    }

}
