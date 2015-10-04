package de.jevopi.j2og.jdt.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.IStructuredSelection;

import de.jevopi.j2og.ModelException;
import de.jevopi.j2og.model.Model;

public class JDTModelCreator {

	Set<IPackageFragment> packageFragments = new HashSet<IPackageFragment>();
	Set<ICompilationUnit> compilationUnits = new HashSet<ICompilationUnit>();

	public JDTModelCreator(IStructuredSelection selection) {
		Iterator<?> it = selection.iterator();
		while (it.hasNext()) {
			Object next = it.next();
			if (next instanceof IPackageFragment) {
				packageFragments.add((IPackageFragment) next);
			} else if (next instanceof ICompilationUnit) {
				compilationUnits.add((ICompilationUnit) next);
			}
		}
	}

	public boolean selectionFound() {
		return packageFragments.size() + compilationUnits.size() > 0;
	}

	public Model createModel(boolean recursive) {
		if (recursive) {
			HashSet<IPackageFragment> allPackageFragments = new HashSet<IPackageFragment>();
			try {
				for (IPackageFragment packageFragment : packageFragments) {
					allPackageFragments.add(packageFragment);
					collectSubPackages(packageFragment, allPackageFragments);
				}
			} catch (Exception ex) {
				throw new ModelException("Error retrieving subpackages", ex);
			}
			packageFragments = allPackageFragments;
		}

		Model model = new Model();
		SimpleJDTParser simpleJDTParser = new SimpleJDTParser();
		for (IPackageFragment packageFragment : packageFragments) {
			model.basePackageNames.add(packageFragment.getElementName());
			simpleJDTParser.setPackageFragment(packageFragment);
			try {
				simpleJDTParser.run();

			} catch (Exception ex) {
				throw new ModelException("Error parsing packages", ex);
			}
			model.addTypes(simpleJDTParser.getClassifiers());
		}
		for (ICompilationUnit compilationUnit : compilationUnits) {
			model.basePackageNames.add(compilationUnit.getParent().getElementName());
			simpleJDTParser.setCompilationUnit(compilationUnit);
			try {
				simpleJDTParser.run();

			} catch (Exception ex) {
				throw new ModelException("Error parsing packages", ex);
			}
			model.addTypes(simpleJDTParser.getClassifiers());
		}
		return model;

	}

	private void collectSubPackages(IPackageFragment packageFragment, HashSet<IPackageFragment> o_allPackageFragments)
			throws JavaModelException {
		IJavaElement[] packages = ((IPackageFragmentRoot) (packageFragment.getParent())).getChildren();
		String n = packageFragment.getElementName();
		for (IJavaElement sub : packages) {
			if (sub != packageFragment && sub instanceof IPackageFragment && sub.getElementName().startsWith(n)) {
				o_allPackageFragments.add((IPackageFragment) sub);
				collectSubPackages((IPackageFragment) sub, o_allPackageFragments);
			}

		}
	}
}
