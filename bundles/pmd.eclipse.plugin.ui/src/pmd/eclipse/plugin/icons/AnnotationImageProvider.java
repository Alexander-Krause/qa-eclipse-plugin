package pmd.eclipse.plugin.icons;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.texteditor.IAnnotationImageProvider;
import org.eclipse.ui.texteditor.MarkerAnnotation;

import pmd.eclipse.plugin.PmdUIPlugin;
import pmd.eclipse.plugin.markers.PmdViolationMarker;

/**
 * @author Christian Wulf
 *
 */
public class AnnotationImageProvider implements IAnnotationImageProvider {

	private final ImageRegistry imageRegistry;

	/**
	 * Is created automatically once from the Eclipse Plugin Environment.
	 */
	public AnnotationImageProvider() {
		imageRegistry = PmdUIPlugin.getDefault().getImageRegistry();

		for (int priority = 1; priority <= 5; priority++) {
			String imageRegistryKey = getImageRegistryKeyByPriority(priority);
			String imageFilePath = "/icons/pmd" + imageRegistryKey + ".png";
			// AbstractUIPlugin.imageDescriptorFromPlugin always returns null
			ImageDescriptor imageDescriptor = ImageDescriptor.createFromFile(PmdUIPlugin.class, imageFilePath);
			imageRegistry.put(imageRegistryKey, imageDescriptor);
		}
	}

	@Override
	public Image getManagedImage(Annotation annotation) {
		if (!(annotation instanceof MarkerAnnotation)) {
			return null;
		}

		int priority = getPriorityFromAnnotation((MarkerAnnotation) annotation);

		String imageRegistryKey = getImageRegistryKeyByPriority(priority);
		return imageRegistry.get(imageRegistryKey);
	}

	@Override
	public String getImageDescriptorId(Annotation annotation) {
		if (!(annotation instanceof MarkerAnnotation)) {
			return null;
		}

		int priority = getPriorityFromAnnotation((MarkerAnnotation) annotation);

		return getImageRegistryKeyByPriority(priority);
	}

	@Override
	public ImageDescriptor getImageDescriptor(String imageDescriptorId) {
		return imageRegistry.getDescriptor(imageDescriptorId);
	}

	/**
	 * @return "pmdPriority"-annotation
	 */
	public static String getImageRegistryKeyByPriority(int pmdPriority) {
		return String.valueOf(pmdPriority) + "-annotation";
	}

	private int getPriorityFromAnnotation(MarkerAnnotation markerAnnotation) {
		IMarker marker = markerAnnotation.getMarker();
		PmdViolationMarker violationMarker = new PmdViolationMarker(marker);
		return violationMarker.getPriority();
	}

}
