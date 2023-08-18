// ============================================================================
//
// Copyright (C) 2006-2023 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataquality.indicators.mapdb;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.talend.resource.ResourceManager;


public class TalendSerializerBaseTest {

    String className = TalendSerializerBaseTest.class.getSimpleName();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        Thread.currentThread().sleep(200);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSerializerNull() throws InterruptedException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        DBSet<String> dbSet = new DBSet<>(ResourceManager.getMapDBFilePath(), className,
                methodName);
        dbSet.add(null);
        System.setProperty("talend.mapdb.closeDelayTime", "0"); //$NON-NLS-1$ //$NON-NLS-2$
        dbSet.close();
        Thread.currentThread().sleep(200);
        dbSet = new DBSet<>(ResourceManager.getMapDBFilePath(), className,
                methodName);
        Object first = dbSet.first();
        int size = dbSet.size();
        dbSet.clear();
        dbSet.close();
        Assert.assertEquals("The method name should be: testSerializerNull", "testSerializerNull", methodName); //$NON-NLS-1$ //$NON-NLS-2$
        Assert.assertEquals("The size of dbset should be 1 but it is: " + size, 1, size); //$NON-NLS-1$
        Assert.assertEquals("The vaule should be same with TupleEmpty but it is: " + first.toString(), dbSet.EMPTY, //$NON-NLS-1$
                first);
        Assert.assertEquals("The vaule should be null but it is: " + first.toString(), "null", //$NON-NLS-1$ //$NON-NLS-2$
                first.toString());
    }

    @Test
    public void testSerializerTimeStamp() throws InterruptedException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        DBSet<Timestamp> dbSet = new DBSet<>(ResourceManager.getMapDBFilePath(), className,
                methodName);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dbSet.add(timestamp);
        System.setProperty("talend.mapdb.closeDelayTime", "0"); //$NON-NLS-1$ //$NON-NLS-2$
        dbSet.close();
        Thread.currentThread().sleep(200);
        dbSet = new DBSet<>(ResourceManager.getMapDBFilePath(), className,
                methodName);
        Object first = dbSet.first();
        int size = dbSet.size();
        dbSet.clear();
        dbSet.close();
        Assert.assertEquals("The method name should be: testSerializerTimeStamp", "testSerializerTimeStamp", //$NON-NLS-1$ //$NON-NLS-2$
                methodName);
        Assert.assertEquals("The size of dbset should be 1 but it is: " + size, 1, size); //$NON-NLS-1$
        Assert.assertEquals("The vaule should be " + timestamp.toString() + " but it is: " + first.toString(), //$NON-NLS-1$ //$NON-NLS-2$
                timestamp, first);
    }

    @Test
    public void testSerializerDate() throws InterruptedException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        DBSet<Date> dbSet = new DBSet<>(ResourceManager.getMapDBFilePath(), className,
                methodName);
        Date date = new TalendFormatDate(new Date(System.currentTimeMillis()));
        dbSet.add(date);
        System.setProperty("talend.mapdb.closeDelayTime", "0"); //$NON-NLS-1$ //$NON-NLS-2$
        dbSet.close();
        Thread.currentThread().sleep(200);
        dbSet = new DBSet<>(ResourceManager.getMapDBFilePath(), className,
                methodName);
        Object first = dbSet.first();
        int size = dbSet.size();
        dbSet.clear();
        dbSet.close();
        Assert.assertEquals("The method name should be: testSerializerDate", "testSerializerDate", methodName); //$NON-NLS-1$ //$NON-NLS-2$
        Assert.assertEquals("The size of dbset should be 1 but it is: " + size, 1, size); //$NON-NLS-1$
        Assert.assertEquals("The vaule should be " + date.toString() + " but it is: " + first.toString(), //$NON-NLS-1$ //$NON-NLS-2$
                date, first);
    }

    @Test
    public void testSerializerTime() throws InterruptedException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        DBSet<Time> dbSet = new DBSet<>(ResourceManager.getMapDBFilePath(), className,
                methodName);
        Time time = new TalendFormatTime(new Time(System.currentTimeMillis()));
        dbSet.add(time);
        System.setProperty("talend.mapdb.closeDelayTime", "0"); //$NON-NLS-1$ //$NON-NLS-2$
        dbSet.close();
        Thread.currentThread().sleep(200);
        dbSet = new DBSet<>(ResourceManager.getMapDBFilePath(), className,
                methodName);
        Object first = dbSet.first();
        int size = dbSet.size();
        dbSet.clear();
        dbSet.close();
        Assert.assertEquals("The method name should be: testSerializerTime", "testSerializerTime", methodName); //$NON-NLS-1$ //$NON-NLS-2$
        Assert.assertEquals("The size of dbset should be 1 but it is: " + size, 1, size); //$NON-NLS-1$
        Assert.assertEquals("The vaule should be " + time.toString() + " but it is: " + first.toString(), //$NON-NLS-1$ //$NON-NLS-2$
                time, first);
    }

    @Test
    public void testSerializerLocalDateTime() throws InterruptedException {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        DBSet<LocalDateTime> dbSet = new DBSet<>(ResourceManager.getMapDBFilePath(), className,
                methodName);
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(localDateTime.toInstant(OffsetDateTime.now().getOffset()).toEpochMilli()),
                ZoneId.systemDefault());
        localDateTime.getLong(ChronoField.MILLI_OF_SECOND);
        dbSet.add(localDateTime);
        System.setProperty("talend.mapdb.closeDelayTime", "0"); //$NON-NLS-1$ //$NON-NLS-2$
        dbSet.close();
        Thread.currentThread().sleep(200);
        dbSet = new DBSet<>(ResourceManager.getMapDBFilePath(), className,
                methodName);
        Object first = dbSet.first();
        int size = dbSet.size();
        dbSet.clear();
        dbSet.close();
        Assert.assertEquals("The method name should be: testSerializerLocalDateTime", "testSerializerLocalDateTime", //$NON-NLS-1$ //$NON-NLS-2$
                methodName);
        Assert.assertEquals("The size of dbset should be 1 but it is: " + size, 1, size); //$NON-NLS-1$
        Assert.assertEquals("The vaule should be " + localDateTime.toString() + " but it is: " + first.toString(), //$NON-NLS-1$ //$NON-NLS-2$
                localDateTime, first);
    }

}
