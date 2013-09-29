// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataquality.record.linkage.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * the values of the match key's handle null field
 * 
 */
public enum HandleNullEnum {
    NULL_MATCH_NULL("nullMatchNull"), //$NON-NLS-1$
    NULL_MATCH_NONE("nullMatchNone"), //$NON-NLS-1$
    NULL_MATCH_ALL("nullMatchAll"); //$NON-NLS-1$

    private String value;

    HandleNullEnum(String value) {
        this.value = value;
    }

    /**
     * Getter for value.
     * 
     * @return the value
     */
    public String getValue() {
        return this.value;
    }

    public static String[] getAllTypes() {
        List<String> list = new ArrayList<String>();
        for (HandleNullEnum theType : values()) {
            list.add(theType.getValue());
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * 
     * get type of the value which in this Enum
     * 
     * @param value
     * @return null can not find this index
     */
    public static HandleNullEnum getTypeByValue(String value) {
        for (HandleNullEnum element : HandleNullEnum.values()) {
            if (element.getValue().equalsIgnoreCase(value)) {
                return element;
            }
        }

        return null;
    }

}