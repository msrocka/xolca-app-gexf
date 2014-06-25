package org.openlca.xolca.app.gefx;

import java.io.File;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.openlca.app.db.Cache;
import org.openlca.app.db.Database;
import org.openlca.app.wizards.io.ModelSelectionPage;
import org.openlca.core.model.ModelType;
import org.openlca.core.model.descriptors.BaseDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExportWizard extends Wizard implements IExportWizard {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private ModelSelectionPage page;

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("Export product system as GEXF file");
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		page = new ModelSelectionPage(ModelType.PRODUCT_SYSTEM);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		File dir = page.getExportDestination();
		List<BaseDescriptor> models = page.getSelectedModels();
		Export export = new Export(Database.get(), dir, Cache.getEntityCache());
		if (dir == null || models.isEmpty())
			return true;
		try {
			getContainer().run(true, true, (monitor) -> {
				monitor.beginTask("GEXF Export: ", models.size());
				models.forEach((d) -> {
					monitor.subTask(d.getName());
					export.doIt(d);
					monitor.worked(1);
				});
				monitor.done();
			});
			return true;
		} catch (Exception e) {
			log.error("failed to run GEXF export", e);
			return false;
		}
	}

}
