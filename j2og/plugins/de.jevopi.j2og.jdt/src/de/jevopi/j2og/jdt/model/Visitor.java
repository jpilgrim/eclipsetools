/*******************************************************************************
 * Copyright (c) 2012 Jens von Pilgrim
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU General Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 *
 * Contributors:
 *    Jens von Pilgrim - initial API and implementation
 ******************************************************************************/
package de.jevopi.j2og.jdt.model;

import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import de.jevopi.j2og.model.Attribute;
import de.jevopi.j2og.model.Class;
import de.jevopi.j2og.model.Interface;
import de.jevopi.j2og.model.Member.Scope;
import de.jevopi.j2og.model.Model;
import de.jevopi.j2og.model.Operation;
import de.jevopi.j2og.model.Type;
import de.jevopi.j2og.model.TypedElement;

/**
 * @author Jens von Pilgrim (developer@jevopi.de)
 */
public class Visitor extends ASTVisitor {
	/**
	 * Logger for this class
	 */
	// @SuppressWarnings("unused") //$NON-NLS-1$
	private static final Logger log = Logger.getLogger(Visitor.class.getName());

	Model model = new Model();

	Stack<Type> classifierStack = new Stack<Type>();
	Stack<Operation> operationStack = new Stack<Operation>();

	/**
	 * @return the model
	 */
	public Model getModel() {
		return model;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.EnumDeclaration)
	 * @since Aug 21, 2011
	 */
	@Override
	public boolean visit(EnumDeclaration i_node) {
		return startType(i_node);
	};

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.AnnotationTypeDeclaration)
	 * @since Aug 21, 2011
	 */
	@Override
	public boolean visit(AnnotationTypeDeclaration i_node) {
		return startType(i_node);
	}

	protected boolean startType(AbstractTypeDeclaration i_node) {
		ITypeBinding binding = i_node.resolveBinding();
		if (binding != null) {

			Type type = getType(binding, false);
			classifierStack.push(type);

			if (log.isLoggable(Level.INFO)) {
				log.info("push " + type); //$NON-NLS-1$
			}

			for (ITypeBinding _interface : binding.getInterfaces()) {
				type.addInterface((Interface) getType(_interface.getErasure(), true));
			}

			if (!binding.isInterface()) {
				ITypeBinding _superBinding = binding.getSuperclass();
				if (_superBinding != null) {
					((Class) type).setSuper((Class) getType(_superBinding.getErasure(), true));
				}
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param i_name
	 * @param i_pname
	 * @return
	 * @since Aug 19, 2011
	 */
	private Type getType(ITypeBinding typeBinding, boolean isContext) {

		typeBinding = typeBinding.getErasure();

		String fqn = typeBinding.getQualifiedName();
		Type t = model.get(fqn);

		if (t == null) {

			String name = typeBinding.getName();
			String packageName = getPackageName(fqn);

			if (typeBinding.isInterface()) {
				t = new Interface(name, packageName);
			} else {
				t = new Class(name, packageName);
				((Class) t).setAbstract(Modifier.isAbstract(typeBinding.getModifiers()));
			}

			t.setContext(isContext);

			model.add(t);

		}

		if (!isContext) {
			t.setContext(false);

		}
		return t;

	}

	/**
	 * @param i_fqn
	 * @return
	 * @since Oct 31, 2011
	 */
	public static String getPackageName(String i_fqn) {
		int p = i_fqn.lastIndexOf('.');
		if (p <= 0) {
			return "";
		}
		return i_fqn.substring(0, p);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.SimpleName)
	 * @since Aug 20, 2011
	 */
	@Override
	public boolean visit(SimpleName i_node) {

		if (!classifierStack.isEmpty()) {

			ITypeBinding typeBinding = i_node.resolveTypeBinding();
			if (typeBinding != null) {

				Type type = getType(typeBinding, true);
				if (type != classifierStack.peek() && !type.getFqn().startsWith(classifierStack.peek().getFqn())) {
					classifierStack.peek().addDependency(type);
				}
			}
		}
		return super.visit(i_node);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.TypeDeclaration)
	 * @since Aug 18, 2011
	 */
	@Override
	public boolean visit(TypeDeclaration i_node) {
		if (i_node.isLocalTypeDeclaration()) {
			return false;
		}
		return startType(i_node);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#endVisit(org.eclipse.jdt.core.dom.TypeDeclaration)
	 * @since Aug 18, 2011
	 */
	@Override
	public void endVisit(TypeDeclaration i_node) {
		super.endVisit(i_node);
		if (i_node.isLocalTypeDeclaration()) {
			return;
		}
		endType(i_node);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#endVisit(org.eclipse.jdt.core.dom.EnumDeclaration)
	 * @since Aug 21, 2011
	 */
	@Override
	public void endVisit(EnumDeclaration i_node) {
		endType(i_node);
		super.endVisit(i_node);
	}

	// /**
	// * {@inheritDoc}
	// *
	// * @see
	// org.eclipse.jdt.core.dom.ASTVisitor#endVisit(org.eclipse.jdt.core.dom.AnnotationTypeDeclaration)
	// * @since Aug 21, 2011
	// */
	// @Override
	// public void endVisit(AnnotationTypeDeclaration i_node) {
	// ITypeBinding binding = i_node.resolveBinding();
	// if (binding != null) {
	// classifierStack.pop();
	// }
	// }

	protected void endType(AbstractTypeDeclaration i_node) {
		ITypeBinding binding = i_node.resolveBinding();
		if (binding != null) {
			Type t = classifierStack.pop();

			if (log.isLoggable(Level.INFO)) {
				log.info("pop " + t); //$NON-NLS-1$
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.VariableDeclarationFragment)
	 * @since Aug 18, 2011
	 */
	@Override
	public boolean visit(VariableDeclarationFragment i_node) {

		IVariableBinding binding = i_node.resolveBinding();
		if (binding != null && binding.isField()) {
			Attribute attribute = new Attribute(binding.getName());

			initTypedElement(attribute, binding);

			int modifiers = binding.getModifiers();
			attribute.setScope(getScope(modifiers));

			if (Modifier.isStatic(binding.getModifiers())) {
				attribute.setStatic(true);
			}

			classifierStack.peek().addAttribute(attribute);

		}
		return super.visit(i_node);

	}

	/**
	 * @param typedElement
	 * @param binding
	 * @since Aug 19, 2011
	 */
	private void initTypedElement(TypedElement typedElement, IVariableBinding binding) {

		ITypeBinding varTypeBinding = binding.getType();

		if (varTypeBinding.isArray()) {

			// TODO dimensions of arrays
			int dim = varTypeBinding.getDimensions();

			// replace with element type
			varTypeBinding = varTypeBinding.getElementType();
			typedElement.setBounds(-1, -1);

		} else if (isCollection(varTypeBinding)) {
			if (varTypeBinding.isParameterizedType()) {
				if (varTypeBinding.getTypeArguments().length == 1) {
					// replace with type parameter
					varTypeBinding = varTypeBinding.getTypeArguments()[0];
					typedElement.setBounds(0, -1);
				}
			}
		}

		varTypeBinding = varTypeBinding.getErasure();

		Type type = getType(varTypeBinding, true);
		typedElement.type = type;

	}

	/**
	 * @param i_modifiers
	 * @return
	 * @since Aug 19, 2011
	 */
	private Scope getScope(int i_modifiers) {
		if (Modifier.isPrivate(i_modifiers)) {
			return Scope.PRIVATE;
		}
		if (Modifier.isProtected(i_modifiers)) {
			return Scope.PROTECTED;
		}
		if (Modifier.isPublic(i_modifiers)) {
			return Scope.PUBLIC;
		}
		return Scope.PACKAGE;
	}

	/**
	 * @param i_varTypeBinding
	 * @return
	 * @since Aug 19, 2011
	 */
	private boolean isCollection(ITypeBinding i_varTypeBinding) {

		String s = i_varTypeBinding.getErasure().getQualifiedName();

		if ("java.util.Collection".equals(s)) {
			return true;
		}

		for (ITypeBinding _interface : i_varTypeBinding.getInterfaces()) {
			if (isCollection(_interface)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#visit(org.eclipse.jdt.core.dom.MethodDeclaration)
	 * @since Aug 18, 2011
	 */
	@Override
	public boolean visit(MethodDeclaration i_node) {
		IMethodBinding binding = i_node.resolveBinding();

		if (binding != null && !binding.isConstructor()) {

			IJavaElement javaElement = binding.getJavaElement();

			Operation operation = new Operation(binding.getName());

			ITypeBinding returnTypeBinding = binding.getReturnType();
			Type type = getType(returnTypeBinding, true);
			operation.type = type;

			operation.setScope(getScope(binding.getModifiers()));

			classifierStack.peek().addOperation(operation);
			operationStack.push(operation);

			if (Modifier.isStatic(binding.getModifiers())) {
				operation.setStatic(true);
			}

			for (int i = 0; i < i_node.parameters().size(); i++) {
				VariableDeclaration parNode = (VariableDeclaration) i_node.parameters().get(i);
				IVariableBinding parBinding = parNode.resolveBinding();
				TypedElement typedElement = new TypedElement(parBinding.getName());
				initTypedElement(typedElement, parBinding);

				operation.addFormalParameter(typedElement);
			}

		}

		return super.visit(i_node);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jdt.core.dom.ASTVisitor#endVisit(org.eclipse.jdt.core.dom.MethodDeclaration)
	 * @since Aug 19, 2011
	 */
	@Override
	public void endVisit(MethodDeclaration i_node) {
		IMethodBinding binding = i_node.resolveBinding();

		if (binding != null && !binding.isConstructor()) {
			operationStack.pop();
		}

		super.endVisit(i_node);
	}

}
