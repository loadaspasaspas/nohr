/**
 *
 */
package pt.unl.fct.di.centria.nohr.model;

/**
 * @author Nuno Costa
 */
public interface RuleConstant extends Constant {

	@Override
	String accept(FormatVisitor visitor);

	public Constant accept(ModelVisitor visitor);

}