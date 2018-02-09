package qa.eclipse.plugin.bundles.checkstyle.tool;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.BeforeExecutionFileFilter;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

import qa.eclipse.plugin.bundles.checkstyle.Activator;
import qa.eclipse.plugin.bundles.checkstyle.marker.CheckstyleMarkers;

class CheckstyleListener implements AuditListener, BeforeExecutionFileFilter {

	private final Map<String, IFile> eclipseFileByFilePath;
	private final SubMonitor monitor;

	public CheckstyleListener(IProgressMonitor monitor, Map<String, IFile> eclipseFileByFilePath) {
		this.eclipseFileByFilePath = eclipseFileByFilePath;
		int numFiles = eclipseFileByFilePath.size();
		String taskName = "Analyzing " + numFiles + " file(s)...";
		this.monitor = SubMonitor.convert(monitor, taskName, numFiles);
	}

	@Override
	public void auditStarted(AuditEvent arg0) {
		// do nothing
	}

	@Override
	public boolean accept(String absoluteFilePath) {
		monitor.split(1);

		IFile eclipseFile = eclipseFileByFilePath.get(absoluteFilePath);
		return eclipseFile.isAccessible();
	}

	@Override
	public void fileStarted(AuditEvent event) {
		// do nothing
	}

	@Override
	public void fileFinished(AuditEvent arg0) {
		// do nothing
	}

	@Override
	public void auditFinished(AuditEvent arg0) {
		// do nothing
	}

	@Override
	public void addError(AuditEvent violation) {
		SeverityLevel severityLevel = violation.getSeverityLevel();

		System.out.println("violation: " + violation.getLocalizedMessage().getMessage());

		
		String violationFilename = violation.getFileName();
		IFile eclipseFile = eclipseFileByFilePath.get(violationFilename);
		try {
			CheckstyleMarkers.appendViolationMarker(eclipseFile, violation);
		} catch (CoreException e) {
			// ignore if marker could not be created
		}
		
		// TODO Auto-generated method stub

	}

	@Override
	public void addException(AuditEvent event, Throwable throwable) {
		Activator.getDefault().logThrowable(event.getMessage(), throwable);
	}

}
