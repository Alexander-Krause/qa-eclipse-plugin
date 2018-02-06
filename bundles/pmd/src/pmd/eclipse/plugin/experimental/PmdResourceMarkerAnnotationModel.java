package pmd.eclipse.plugin.experimental;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;

import pmd.eclipse.plugin.markers.CheckstyleViolationMarker;

class PmdResourceMarkerAnnotationModel extends ResourceMarkerAnnotationModel {

	private int maxPriority = 1;

	public PmdResourceMarkerAnnotationModel(IResource resource) {
		super(resource);
	}

	@Override
	protected boolean isAcceptable(IMarker marker) {
		boolean isAcceptable = super.isAcceptable(marker);
		if (!isAcceptable) {
			return false;
		}

		CheckstyleViolationMarker pmdViolationMarker = new CheckstyleViolationMarker(marker);
		int priority = pmdViolationMarker.getPriority();
		return (maxPriority >= priority);
	}

}
