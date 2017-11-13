package pmd.eclipse.plugin.ui.handlers;

//architectural hint: may use eclipse packages
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import pmd.eclipse.plugin.ui.visitors.ResourceVisitor;
import pmdeclipseplugin.PmdUIPlugin;
import pmdeclipseplugin.pmd.PmdTool;

public class ExplorerHandler extends AbstractHandler {

	private final ResourceVisitor resourceVisitor;

	public ExplorerHandler() {
		PmdTool pmdTool = PmdUIPlugin.getDefault().getPmdTool();
		resourceVisitor = new ResourceVisitor(pmdTool);
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage activePage = window.getActivePage();
		ISelection selection = activePage.getSelection();

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;

			Iterator<?> iter = structuredSelection.iterator();
			while (iter.hasNext()) {
				Object selectedObject = iter.next();
				processSelectedObject(selectedObject, event);
			}

		}

		return null;
	}

	private void processSelectedObject(Object selectedObject, ExecutionEvent event) {
		// package explorer:
		IResource resource = null;
		if (selectedObject instanceof IJavaElement) {
			// includes ICompilationUnit, IPackageFragment, IJavaProject
			resource = ((IJavaElement) selectedObject).getResource();
		} else if (selectedObject instanceof IResource) {
			resource = (IResource) selectedObject;
		}

		if (resource != null) {
			try {
				resource.accept(resourceVisitor);
			} catch (CoreException e) {
				throw new IllegalStateException(e);
			}
		}
	}
}
