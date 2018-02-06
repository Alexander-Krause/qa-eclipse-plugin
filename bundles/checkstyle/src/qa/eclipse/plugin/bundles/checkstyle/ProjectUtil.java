package qa.eclipse.plugin.bundles.checkstyle;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public final class ProjectUtil {

	private ProjectUtil() {
		// utility class
	}

	/**
	 * 
	 * @param project
	 * @return e.g., <code>JavaSE-1.8</code>
	 */
	public static String getCompilerCompliance(IProject project) {
		try {
			if (project.hasNature(JavaCore.NATURE_ID)) {
				IJavaProject javaProject = JavaCore.create(project);
				String compilerCompliance = javaProject.getOption(JavaCore.COMPILER_COMPLIANCE, true);
				return compilerCompliance;
			}
			throw new IllegalStateException("The project is not a Java project.");
		} catch (CoreException e) {
			throw new IllegalStateException(e);
		}
	}

	public static File getProjectPath(IProject project) {
		IPath location = project.getLocation(); // getRawLocation returns null
		return location.makeAbsolute().toFile();
	}
}
