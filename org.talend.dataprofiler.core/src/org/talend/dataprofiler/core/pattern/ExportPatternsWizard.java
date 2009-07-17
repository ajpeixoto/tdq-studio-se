// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
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

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.wizard.Wizard;
import org.talend.commons.emf.FactoriesUtil;
import org.talend.dataprofiler.core.i18n.internal.DefaultMessagesImpl;
import org.talend.dataquality.domain.pattern.Pattern;
import org.talend.dq.helper.resourcehelper.PatternResourceFileHelper;
import org.talend.utils.io.FilesUtils;

/**
 * DOC zqin class global comment. Detailled comment
 */
public class ExportPatternsWizard extends Wizard {

    private static Logger log = Logger.getLogger(ExportPatternsWizard.class);

    private IFolder folder;

    private ExportPatternsWizardPage page;

    private boolean isForExchange;

    public ExportPatternsWizard(IFolder folder, boolean isForExchange) {
        this.folder = folder;
        this.isForExchange = isForExchange;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */
    @Override
    public boolean performFinish() {

        String targetFile = page.getTargetFile();
        Object[] elements = page.getSelectedPatternsTree().getCheckedElements();

        List<Pattern> seletedPatterns = new ArrayList<Pattern>();
        for (Object element : elements) {
            if (element instanceof IFile) {
                IFile file = (IFile) element;
                if (file.getFileExtension().equalsIgnoreCase(FactoriesUtil.PATTERN)) {
                    seletedPatterns.add(PatternResourceFileHelper.getInstance().findPattern(file));
                }
            }
        }

        if ("".equals(targetFile)) {
            MessageDialog.openError(getShell(), "Error", "Please specify a valid resource!");
            return false;
        } else {
            File resource = new File(targetFile);

            if (isForExchange) {

                if (resource.isDirectory()) {
                    ExportFactory.export(resource, folder, seletedPatterns.toArray(new Pattern[seletedPatterns.size()]));

                    for (Iterator iterator = seletedPatterns.iterator(); iterator.hasNext();) {
                        Pattern pattern = (Pattern) iterator.next();
                        File patternFile = new File(resource, pattern.getName() + ".csv");
                        if (patternFile.isFile() && patternFile.exists()) {
                            try {
                                FilesUtils.zip(patternFile, patternFile.getPath() + ".zip");
                                patternFile.delete();

                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                            }
                        }
                    }

                    return true;
                }

                MessageDialog.openError(getShell(), "Error", "Please specify a valid folder!");
                return false;
            } else {
                if (!resource.getName().endsWith(".csv")) {
                    MessageDialog.openError(getShell(), "Error", "Please specify a valid file!");
                    return false;
                }

                boolean isContinue = true;
                if (resource.exists()) {
                    isContinue = MessageDialogWithToggle.openConfirm(null, DefaultMessagesImpl
                            .getString("ExportPatternsWizard.waring"), //$NON-NLS-1$
                            DefaultMessagesImpl.getString("ExportPatternsWizard.fileAlreadyExist")); //$NON-NLS-1$
                }

                if (isContinue) {
                    ExportFactory.export(resource, folder, seletedPatterns.toArray(new Pattern[seletedPatterns.size()]));
                    return true;
                }

                return false;
            }
        }
    }

    @Override
    public void addPages() {
        page = new ExportPatternsWizardPage(folder, isForExchange);
        addPage(page);
    }

}
