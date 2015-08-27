package pt.unl.fct.di.centria.nohr.plugin;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.protege.editor.core.ui.view.ViewComponent;

import pt.unl.fct.di.centria.nohr.parsing.ParseException;
import pt.unl.fct.di.centria.nohr.plugin.rules.RuleEditor;
import pt.unl.fct.di.centria.nohr.plugin.rules.RuleListModel;
import pt.unl.fct.di.centria.nohr.plugin.rules.RulesList;

/**
 * The {@link ViewComponent} where the rules are edited.
 *
 * @author Nuno Costa
 */
public class RulesViewComponent extends AbstractNoHRViewComponent {

	private static RuleListModel ruleListModel;

	/**
	 *
	 */
	private static final long serialVersionUID = 6087261708132206489L;

	private RuleListModel getRuleListModel() {
		if (ruleListModel == null)
			ruleListModel = new RuleListModel(getOWLEditorKit(), getParser(), getProgram());
		return ruleListModel;
	}

	@Override
	public void initialiseOWLView() throws Exception {
		setLayout(new BorderLayout());
		final RuleEditor ruleEditor = new RuleEditor(getOWLEditorKit(), getParser());
		final RulesList ruleList = new RulesList(ruleEditor, getRuleListModel());
		final JScrollPane jScrollPane = new JScrollPane(ruleList);
		add(jScrollPane, BorderLayout.CENTER);
		final JPanel buttonHolder = new JPanel(new FlowLayout(FlowLayout.LEFT));
		final JButton openButton = new JButton(new AbstractAction("Open") {

			private static final long serialVersionUID = -2176187025244957420L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				final int returnVal = fc.showOpenDialog(RulesViewComponent.this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
					try {
						getRuleListModel().load(fc.getSelectedFile());
					} catch (final FileNotFoundException e) {
					} catch (final ParseException e) {
						Messages.invalidExpression(RulesViewComponent.this, e);
					}
			}
		});
		final JButton saveButton = new JButton(new AbstractAction("Save") {

			private static final long serialVersionUID = -2176187025244957420L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				final int returnVal = fc.showOpenDialog(RulesViewComponent.this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
					try {
						getRuleListModel().save(fc.getSelectedFile());
					} catch (final IOException e) {
						Messages.unsucceccfulSave(RulesViewComponent.this, e);
					}
			}
		});
		buttonHolder.add(openButton);
		buttonHolder.add(saveButton);
		add(buttonHolder, BorderLayout.SOUTH);
	}

}
