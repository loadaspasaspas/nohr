package pt.unl.fct.di.centria.nohr.reasoner.translation.ontology;

import java.util.Set;

import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

import pt.unl.fct.di.centria.nohr.reasoner.UnsupportedOWLProfile;

public interface OntologyTranslator {

    public Set<String> getNegatedPredicates();

    public Set<String> getTabledPredicates();

    public boolean hasDisjunctions();

    public void translate(Set<String> translationContainer)
	    throws ParserException, UnsupportedOWLProfile,
	    OWLOntologyCreationException, OWLOntologyStorageException;

}