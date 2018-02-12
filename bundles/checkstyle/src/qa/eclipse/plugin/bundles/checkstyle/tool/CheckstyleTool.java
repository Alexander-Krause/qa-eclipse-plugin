package qa.eclipse.plugin.bundles.checkstyle.tool;
// may not contain anything from the Eclipse API

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader.IgnoredModulesOptions;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.PropertyResolver;
import com.puppycrawl.tools.checkstyle.ThreadModeSettings;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

import qa.eclipse.plugin.bundles.checkstyle.EclipsePlatform;
import qa.eclipse.plugin.bundles.checkstyle.FileUtil;
import qa.eclipse.plugin.bundles.checkstyle.ProjectUtil;
import qa.eclipse.plugin.bundles.checkstyle.preference.CheckstylePreferences;

public class CheckstyleTool {

	private final Checker checker;

	public CheckstyleTool() {
		this.checker = new Checker();
	}

	// FIXME remove Eclipse API
	public void startAsyncAnalysis(List<IFile> eclipseFiles, CheckstyleListener checkstyleListener) {
		IFile file = eclipseFiles.get(0);
		IProject project = file.getProject();

		checker.setBasedir(null);
		// checker.setCacheFile(fileName);

		try {
			checker.setCharset(project.getDefaultCharset());
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		} catch (CoreException e) {
			throw new IllegalStateException(e);
		}

		IEclipsePreferences projectPreferences = CheckstylePreferences.INSTANCE.getProjectScopedPreferences(project);
		String[] customModuleJarPaths = CheckstylePreferences.INSTANCE.loadCustomModuleJarPaths(projectPreferences);
		File eclipseProjectPath = ProjectUtil.getProjectPath(project);
		URL[] urls = FileUtil.filePathsToUrls(eclipseProjectPath, customModuleJarPaths);

		// Checker requires moduleClassLoader

		ClassLoader moduleClassLoader = new URLClassLoader(urls, getClass().getClassLoader());
		checker.setModuleClassLoader(moduleClassLoader);

		Locale platformLocale = EclipsePlatform.getLocale();
		checker.setLocaleLanguage(platformLocale.getLanguage());
		checker.setLocaleCountry(platformLocale.getCountry());

		Configuration configuration;
		// configuration = new DefaultConfiguration("Eclipse Checkstyle Config",
		// ThreadModeSettings.SINGLE_THREAD_MODE_INSTANCE);

		String configFilePath = CheckstylePreferences.INSTANCE.loadConfigFilePath(projectPreferences);
		File configFile = FileUtil.makeAbsoluteFile(configFilePath, eclipseProjectPath);
		String absoluteConfigFilePath = configFile.toString();

		PropertyResolver propertyResolver = new PropertiesExpander(new Properties());
		IgnoredModulesOptions ignoredModulesOptions = IgnoredModulesOptions.OMIT;
		ThreadModeSettings threadModeSettings = ThreadModeSettings.SINGLE_THREAD_MODE_INSTANCE;
		try {
			configuration = ConfigurationLoader.loadConfiguration(absoluteConfigFilePath, propertyResolver,
					ignoredModulesOptions, threadModeSettings);
		} catch (CheckstyleException e) {
			throw new IllegalStateException(e);
		}

		try {
			checker.configure(configuration);
		} catch (CheckstyleException e) {
			throw new IllegalStateException(e);
		}

		checker.addListener(checkstyleListener);
		checker.addBeforeExecutionFileFilter(checkstyleListener);

		// https://github.com/checkstyle/eclipse-cs/blob/master/net.sf.eclipsecs.core/src/net/sf/eclipsecs/core/builder/CheckerFactory.java#L275

		List<File> files = new ArrayList<>();

		for (IFile eclipseFile : eclipseFiles) {
			final File sourceCodeFile = eclipseFile.getLocation().toFile().getAbsoluteFile();
			files.add(sourceCodeFile);
		}

		try {
			checker.process(files);
		} catch (CheckstyleException e) {
			if (e.getCause() instanceof OperationCanceledException) {
				// user requested cancellation, keep silent
			} else {
				throw new IllegalStateException(e); // log to error view somewhere
			}
		}
	}

}
