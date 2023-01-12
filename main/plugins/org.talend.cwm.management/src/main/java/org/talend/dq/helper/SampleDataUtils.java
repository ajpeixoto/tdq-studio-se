// ============================================================================
//
// Copyright (C) 2006-2021 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dq.helper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.talend.core.model.metadata.builder.connection.MetadataColumn;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.cwm.helper.ColumnHelper;
import org.talend.cwm.helper.TaggedValueHelper;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.analysis.SampleDataShowWay;
import org.talend.dataquality.helpers.AnalysisHelper;
import org.talend.dataquality.sample.data.SampleDataStatement;
import org.talend.dq.analysis.data.preview.DataPreviewHandler;
import orgomg.cwm.objectmodel.core.ModelElement;

/**
 * DOC zshen class global comment. Detailled comment
 */
public class SampleDataUtils {

    private static SampleDataUtils instance;

    private SampleDataUtils() {

    }

    /**
     * Getter for instance.
     *
     * @return the instance
     */
    public static SampleDataUtils getInstance() {
        if (instance == null) {
            instance = new SampleDataUtils();
        }
        return instance;
    }

    private SampleDataStatement statement = null;

    private static Logger log = Logger.getLogger(SampleDataUtils.class);

    /*
     * (non-Javadoc)
     *
     * @see org.talend.dataprofiler.service.IAnalysisEditorService#getSampleData()
     */
    public SampleDataStatement getSampleDataStatement(final Analysis findAnalysis) {
        statement = null;
        DataPreviewHandler dataPreviewHandler = new DataPreviewHandler();
        dataPreviewHandler.setDataFilter(getDataFilter(findAnalysis));
        List<Object[]> previewData = new ArrayList<Object[]>();
        ModelElement[] columns = null;
        try {
            columns = getColumns(findAnalysis);
            previewData = dataPreviewHandler.createPreviewData(columns, getLimitNumber(findAnalysis),
                    isShowRandomData(findAnalysis));
        } catch (SQLException e) {
            log.error(e, e);
            // this exception generate mean that the sample data can not be get out so that we will return a empty result.
        }
        statement = new SampleDataStatement(previewData, getPropertyNames(columns));
        // }
        return statement;
    }

    /**
     * DOC zshen Comment method "getPropertyNames".
     *
     * @return
     */
    private String[] getPropertyNames(final ModelElement[] columns) {
        // ModelElement[] columns = getColumns(findAnalysis);
        String[] propertyNames = new String[columns.length];
        int index = 0;
        for (ModelElement modelElement : columns) {
            propertyNames[index++] = modelElement.getName();
        }
        return propertyNames;
    }

    /**
     * DOC zshen Comment method "getLimitNumber".
     *
     * @param findAnalysis
     * @return
     */
    private int getLimitNumber(Analysis findAnalysis) {
        String valueString = TaggedValueHelper.getValueString(TaggedValueHelper.PREVIEW_ROW_NUMBER, findAnalysis);
        Integer limitNumber = 0;
        try {
            limitNumber = Integer.valueOf(valueString);
        } catch (NumberFormatException e) {
            // there keep limitNumber is zero because of when this value is less than or same with zero then mean that no limit
            // here
        }
        return limitNumber;
    }

    /**
     * DOC zshen Comment method "isShowRandomData".
     *
     * @param findAnalysis
     * @return
     */
    private boolean isShowRandomData(final Analysis findAnalysis) {
        return findAnalysis.getParameters().getSampleDataShowWay() == SampleDataShowWay.RANDOM;
    }

    /**
     * DOC zshen Comment method "getColumns".
     *
     * @param findAnalysis
     * @return
     */
    private ModelElement[] getColumns(final Analysis findAnalysis) {
        EList<ModelElement> ownedElement = findAnalysis.getContext().getAnalysedElements();
        ModelElement modelElement = ownedElement.get(0);
        MetadataTable columnOwnerAsMetadataTable = ColumnHelper.getColumnOwnerAsMetadataTable(modelElement);
        EList<MetadataColumn> columns = columnOwnerAsMetadataTable.getColumns();
        ModelElement[] modelElementArray = new ModelElement[columns.size()];
        int index = 0;
        for (MetadataColumn metadataColumn : columns) {
            modelElementArray[index++] = metadataColumn;
        }
        return modelElementArray;
    }

    /**
     * DOC zshen Comment method "getDataFilter".
     *
     * @return
     */
    private String getDataFilter(final Analysis findAnalysis) {
        return AnalysisHelper.getStringDataFilter(findAnalysis);
    }

}
