package pmdeclipseplugin;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import pmdeclipseplugin.pmd.PmdTool;

/**
 * The activator class controls the plug-in life cycle
 */
public class PmdUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "pmd-eclipse-plugin"; //$NON-NLS-1$

	// The shared instance
	private static PmdUIPlugin plugin;

	private BundleContext context;

	private PmdTool pmdTool;

	/**
	 * The constructor
	 */
	public PmdUIPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		this.context = context;
		this.pmdTool = new PmdTool();

		registerResourceChangeListener();
	}

	private void registerResourceChangeListener() {
		// IResourceChangeListener resourceChangeListener = new
		// PmdResourceChangeListener();
		// ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceChangeListener);

		// ISaveParticipant saveParticipant = new PmdSaveParticipant();
		// try {
		// ResourcesPlugin.getWorkspace().addSaveParticipant(PLUGIN_ID,
		// saveParticipant);
		// } catch (CoreException e) {
		// throw new IllegalStateException(e);
		// }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static PmdUIPlugin getDefault() {
		return plugin;
	}

	public BundleContext getContext() {
		return context;
	}

	public PmdTool getPmdTool() {
		return pmdTool;
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		super.initializeImageRegistry(reg);

		// for (int i = 1; i <= 5; i++) {
		// String imageRegistryKey = String.valueOf(i);
		// String imageFilePath = "/icons/priority" + imageRegistryKey + ".png";
		// // AbstractUIPlugin.imageDescriptorFromPluginalways returns null
		// ImageDescriptor imageDescriptor =
		// ImageDescriptor.createFromFile(PmdUIPlugin.class, imageFilePath);
		// reg.put(imageRegistryKey, imageDescriptor);
		// }
	}

}
