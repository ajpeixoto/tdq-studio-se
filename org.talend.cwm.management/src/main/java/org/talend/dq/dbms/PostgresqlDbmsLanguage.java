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
package org.talend.dq.dbms;

import org.talend.dataquality.indicators.DateGrain;

/**
 * DOC scorreia class global comment. Detailled comment
 */
public class PostgresqlDbmsLanguage extends DbmsLanguage {

    /**
     * DOC scorreia PostgresqlDbmsLanguage constructor comment.
     */
    public PostgresqlDbmsLanguage() {
        super(DbmsLanguage.POSTGRESQL);
    }

    /**
     * DOC scorreia PostgresqlDbmsLanguage constructor comment.
     * 
     * @param dbmsType
     * @param majorVersion
     * @param minorVersion
     */
    public PostgresqlDbmsLanguage(String dbmsType, int majorVersion, int minorVersion) {
        super(dbmsType, majorVersion, minorVersion);
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.cwm.management.api.DbmsLanguage#toQualifiedName(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public String toQualifiedName(String catalog, String schema, String table) {
        // do not use Catalog (ZQL Parser does not know how to handle qualified names such as 'c.s.t'
        return super.toQualifiedName(null, schema, table);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.cwm.management.api.DbmsLanguage#getPatternFinderDefaultFunction(java.lang.String)
     */
    @Override
    public String getPatternFinderDefaultFunction(String expression) {
        return "TRANSLATE(" + expression
                + " ,'1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZÂÊÎÔÛÄËÏÖÜabcdefghijklmnopqrstuvwxyzçâêîôûéèùïöü' "
                + ",RPAD('9',10,'9') || RPAD('A',36,'A')||RPAD('a',38,'a'))";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.cwm.management.api.DbmsLanguage#getTopNQuery(java.lang.String, int)
     */
    @Override
    public String getTopNQuery(String query, int n) {
        return query + " LIMIT " + n;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.cwm.management.api.DbmsLanguage#extract(org.talend.dataquality.indicators.DateGrain,
     * java.lang.String)
     */
    @Override
    protected String extract(DateGrain dateGrain, String colName) {
        return " CAST( EXTRACT(" + dateGrain + from() + colName + ") AS INTEGER )";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.cwm.management.api.DbmsLanguage#getSelectRegexp(java.lang.String)
     */
    @Override
    protected String getSelectRegexp(String regexLikeExpression) {
        return "SELECT " + regexLikeExpression + " AS OK" + EOS;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.cwm.management.api.DbmsLanguage#regexLike(java.lang.String, java.lang.String)
     */
    @Override
    public String regexLike(String element, String regex) {
        return surroundWithSpaces(element + " ~ " + regex);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.cwm.management.api.DbmsLanguage#regexNotLike(java.lang.String, java.lang.String)
     */
    @Override
    public String regexNotLike(String element, String regex) {
        return surroundWithSpaces(element + " !~ " + regex);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.cwm.management.api.DbmsLanguage#getQuoteIdentifier()
     */
    @Override
    public String getHardCodedQuoteIdentifier() {
        return "\"";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.cwm.management.api.DbmsLanguage#supportNonIntegerConstantInGroupBy()
     */
    @Override
    public boolean supportNonIntegerConstantInGroupBy() {
        return false;
    }

}
