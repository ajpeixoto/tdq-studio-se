// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.pattern;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.talend.dataprofiler.core.ImageLib;

/**
 * DOC qzhang class global comment. Detailled comment <br/>
 * 
 * $Id: talend.epf 1 2006-09-29 17:06:40Z nrousseau $
 * 
 */
public class ImportPatternsAction extends Action {

    private IFolder folder;

    /**
     * DOC qzhang ImportPatternsAction constructor comment.
     */
    public ImportPatternsAction(IFolder folder) {
        setText("import patterns");
        setImageDescriptor(ImageLib.getImageDescriptor(ImageLib.PATTERN_REG));
        this.folder = folder;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        ImportPatternsWizard wizard = new ImportPatternsWizard(folder);
        WizardDialog dialog = new CheatSheetWizardDialog(Display.getDefault().getActiveShell(), wizard, "");
        wizard.setWindowTitle(getText());
        if (WizardDialog.OK == dialog.open()) {
            try {
                folder.refreshLocal(IResource.DEPTH_INFINITE, null);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }
}
