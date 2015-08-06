// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.ui.editor.preview.model.states.freq;

import java.util.List;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.jfree.chart.JFreeChart;
import org.talend.dataprofiler.common.ui.editor.preview.chart.TopChartFactory;
import org.talend.dataprofiler.core.i18n.internal.DefaultMessagesImpl;
import org.talend.dataprofiler.core.ui.editor.preview.IndicatorUnit;
import org.talend.dataprofiler.core.ui.editor.preview.model.entity.TableStructureEntity;
import org.talend.dataprofiler.core.ui.editor.preview.model.states.ChartTableProviderClassSet.SoundexBaseChartTableLabelProvider;
import org.talend.dataprofiler.core.ui.utils.ComparatorsFactory;
import org.talend.dq.analysis.explore.DataExplorer;
import org.talend.dq.analysis.explore.SoundexFrequencyExplorer;
import org.talend.dq.indicators.ext.FrequencyExt;

/**
 * 
 * DOC mzhao Low soundex frequency table.
 */
public class SoundexLowFrequencyTableState extends FrequencyTypeStates {

    public SoundexLowFrequencyTableState(List<IndicatorUnit> units) {
        super(units);
    }

    @Override
    protected void sortIndicator(FrequencyExt[] frequencyExt) {
        ComparatorsFactory.sort(frequencyExt, ComparatorsFactory.SOUNDEX_LOW_FREQUENCY_COMPARATOR_ID);
    }

    @Override
    protected String getTitle() {
        return DefaultMessagesImpl.getString("FrequencyTypeStates.SoundexLowFreqyebctStatistics"); //$NON-NLS-1$
    }

    @Override
    protected ITableLabelProvider getLabelProvider() {
        return new SoundexBaseChartTableLabelProvider();
    }

    @Override
    protected TableStructureEntity getTableStructure() {
        TableStructureEntity entity = new TableStructureEntity();
        entity.setFieldNames(new String[] {
                DefaultMessagesImpl.getString("FrequencyTypeStates.value"), DefaultMessagesImpl.getString("FrequencyTypeStates.distinctCount"), DefaultMessagesImpl.getString("FrequencyTypeStates.count"), "%" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        entity.setFieldWidths(new Integer[] { 180, 120, 100, 100 });
        return entity;
    }

    public DataExplorer getDataExplorer() {
        return new SoundexFrequencyExplorer();
    }

    @Override
    public JFreeChart getChart() {
        return TopChartFactory.createBarChart(DefaultMessagesImpl.getString("TopChartFactory.distinctCount"), getDataset());
    }

}
