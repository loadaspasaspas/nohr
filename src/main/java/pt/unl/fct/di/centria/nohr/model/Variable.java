package pt.unl.fct.di.centria.nohr.model;

import pt.unl.fct.di.centria.nohr.model.terminals.ModelVisitor;

/**
 * Represents variable.
 *
 * @author Nuno Costa
 */

public interface Variable extends Term, Comparable<Variable> {

	Variable accept(ModelVisitor visitor);
}
