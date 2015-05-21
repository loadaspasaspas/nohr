package nohr.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import nohr.model.predicates.Predicate;
import nohr.model.predicates.PredicateImpl;

public class Model {

    public static Constant cons(Number n) {
	return new NumericConstantImpl(n);
    }

    public static Constant cons(String symbol) {
	return new ConstantImpl(symbol);
    }

    public static Term list(Term... terms) {
	List<Term> list = new LinkedList<Term>();
	Collections.addAll(list, terms);
	return new ListTermImpl(list);
    }

    public static NegativeLiteral negLiteral(Predicate pred, Term... args) {
	List<Term> argsList = new LinkedList<Term>();
	Collections.addAll(argsList);
	return new NegativeLiteralImpl(new AtomImpl(pred, argsList));
    }

    public static NegativeLiteral negLiteral(String pred, Term... args) {
	List<Term> argsList = new LinkedList<Term>();
	Collections.addAll(argsList);
	return new NegativeLiteralImpl(new AtomImpl(new PredicateImpl(pred,
		args.length), argsList));
    }

    public static PositiveLiteral posLiteral(Predicate pred, Term... args) {
	List<Term> argsList = new LinkedList<Term>();
	Collections.addAll(argsList, args);
	return new PositiveLiteralImpl(new AtomImpl(pred, argsList));
    }

    public static PositiveLiteral posLiteral(String pred, Term... args) {
	List<Term> argsList = new LinkedList<Term>();
	Collections.addAll(argsList, args);
	return new PositiveLiteralImpl(new AtomImpl(new PredicateImpl(pred,
		args.length), argsList));
    }

    public static Query query(List<Variable> vars, Literal... literals) {
	List<Literal> literalList = new LinkedList<Literal>();
	Collections.addAll(literalList, literals);
	return new QueryImpl(literalList, vars);
    }

    public static Query query(Literal... literals) {
	List<Literal> literalList = new LinkedList<Literal>();
	List<Variable> vars = new LinkedList<Variable>();
	Collections.addAll(literalList, literals);
	for (Literal literal : literals)
	    vars.addAll(literal.getVariables());
	return new QueryImpl(literalList, vars);
    }

    public static Rule rule(PositiveLiteral head, Literal... body) {
	List<Literal> bodyList = new LinkedList<Literal>();
	Collections.addAll(bodyList, body);
	return new RuleImpl(head, bodyList);
    }

    public static Substitution subs(Map<Variable, Term> map) {
	SortedMap<Variable, Integer> varsIdx = new TreeMap<Variable, Integer>();
	Term[] vals = new Term[map.size()];
	int i = 0;
	for (Entry<Variable, Term> entry : map.entrySet()) {
	    varsIdx.put(entry.getKey(), i);
	    vals[i++] = entry.getValue();
	}
	return new SubstitutionImpl(varsIdx, vals);
    }

    public static Substitution subs(
	    SortedMap<Variable, Integer> varsIdx, Term... vals) {
	return new SubstitutionImpl(varsIdx, vals);
    }

    public static Substitution subs(Variable var, Term term) {
	SortedMap<Variable, Integer> varsIdx = new TreeMap<Variable, Integer>();
	Term[] vals = new Term[1];
	varsIdx.put(var, 0);
	vals[0] = term;
	return new SubstitutionImpl(varsIdx, vals);
    }

    public static Variable var(String name) {
	return new VariableImpl(name);
    }

}