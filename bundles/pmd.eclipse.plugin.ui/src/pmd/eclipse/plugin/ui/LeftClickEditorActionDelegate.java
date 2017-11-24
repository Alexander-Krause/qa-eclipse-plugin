package pmd.eclipse.plugin.ui;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.SelectRulerAction;

import pmd.eclipse.plugin.views.PmdViolationsView;

public class LeftClickEditorActionDelegate extends SelectRulerAction /* AbstractRulerActionDelegate */ {

	// @Override
	// protected IAction createAction(ITextEditor editor, IVerticalRulerInfo
	// rulerInfo) {
	// // new
	// SelectMarkerRulerAction(TextEditorMessages.getBundleForConstructedKeys(),
	// // "LeftClickEditorActionDelegate",
	// // editor, rulerInfo);
	// // new SelectAnnotationRulerAction(bundle, prefix, editor);
	// return super.createAction(editor, rulerInfo);
	// }

	@Override
	public void mouseDown(MouseEvent e) {
		super.mouseDown(e);

		// if (e.button != 1) {
		// return;
		// }

		// final IWorkbenchWindow activeWorkbenchWindow =
		// PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		// IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		// try {
		// activePage.showView(PmdViolationsView.ID);
		// } catch (PartInitException ex) {
		// throw new IllegalStateException(ex);
		// }
	}

	@Override
	public void mouseUp(MouseEvent e) {
		// super.mouseUp(e);

		if (e.button != 1) {
			return;
		}

		Object source = e.getSource();

		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
		IEditorPart activeEditor = activePage.getActiveEditor();

		try {
			PmdViolationsView viewPart = (PmdViolationsView) activePage.showView(PmdViolationsView.ID);
			// viewPart.selectRow();
		} catch (PartInitException ex) {
			throw new IllegalStateException(ex);
		}
	}
}
