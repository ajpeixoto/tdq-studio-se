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
package org.talend.dataquality.datamasking;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.dataquality.datamasking.Functions.ReplaceNumericInteger;

/**
 * created by jgonzalez on 25 juin 2015 Detailled comment
 *
 */
public class ReplaceNumericIntegerTest {

    private int input = 123;

    private int output;

    private ReplaceNumericInteger rni = new ReplaceNumericInteger();

    @Test
    public void testGood() {
        rni.parameters = "6".split(","); //$NON-NLS-1$ //$NON-NLS-2$
        rni.integerParam = 6;
        output = rni.generateMaskedRow(input);
        assertEquals(output, 666);
    }

    @Test
    public void testBad() {
        rni.parameters = "10".split(","); //$NON-NLS-1$ //$NON-NLS-2$
        rni.integerParam = 10;
        output = rni.generateMaskedRow(input);
        assertEquals(output, 888);
    }

}