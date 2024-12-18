/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2024 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.ext.turbographpp.model.meta;

import java.sql.SQLException;
import java.util.*;

import org.jkiss.dbeaver.DBException;
import org.jkiss.dbeaver.ext.generic.GenericConstants;
import org.jkiss.dbeaver.ext.generic.model.*;
import org.jkiss.dbeaver.ext.generic.model.meta.GenericMetaModel;
import org.jkiss.dbeaver.ext.turbographpp.model.TurboGraphPPTable;
import org.jkiss.dbeaver.ext.turbographpp.model.TurboGraphPPTableColumn;
import org.jkiss.dbeaver.ext.turbographpp.model.TurboGraphPPView;
import org.jkiss.dbeaver.model.exec.jdbc.JDBCResultSet;
import org.jkiss.dbeaver.model.exec.jdbc.JDBCSession;
import org.jkiss.dbeaver.model.exec.jdbc.JDBCStatement;
import org.jkiss.dbeaver.model.impl.jdbc.JDBCUtils;
import org.jkiss.dbeaver.model.runtime.DBRProgressMonitor;

public class TurboGraphPPMetaModel extends GenericMetaModel {

    @Override
    public JDBCStatement prepareTableColumnLoadStatement(
            JDBCSession session, GenericStructContainer owner, GenericTableBase forTable)
            throws SQLException {
        return session.getMetaData()
                .getColumns(
                        null,
                        null,
                        forTable == null
                                ? owner.getDataSource().getAllObjectsPattern()
                                : JDBCUtils.escapeWildCards(session, forTable.getName()),
                        getColumnNamePattern(forTable))
                .getSourceStatement();
    }
    
    
    private String getColumnNamePattern(GenericTableBase forTable) {
        if (forTable == null) {
            return "node";
        } else {
            if (isEdge(forTable.getTableType())) {
                return "edge";
            }
        }
        return "node";
    }
    
    public boolean isEdge(String tableType) {
        return tableType.toUpperCase(Locale.ENGLISH).contains(GenericConstants.TABLE_TYPE_VIEW)
                || tableType.toUpperCase(Locale.ENGLISH).contains("EDGE");
    }

    public boolean isSystemTable(GenericTableBase table) {
        final String tableType = table.getTableType().toUpperCase(Locale.ENGLISH);
        return tableType.contains("SYSTEM");
    }
    
    @Override
    public GenericTableBase createTableImpl(
            GenericStructContainer container,
            String tableName,
            String tableType,
            JDBCResultSet dbResult) {
        if (tableType != null && isEdge(tableType)) {
            return new TurboGraphPPView(container, tableName, tableType, dbResult);
        } 
        
        return new TurboGraphPPTable(container, tableName, tableType, dbResult);
    }
    
    @Override
    public GenericTableColumn createTableColumnImpl(
            DBRProgressMonitor monitor,
            JDBCResultSet dbResult,
            GenericTableBase table,
            String columnName,
            String typeName,
            int valueType,
            int sourceType,
            int ordinalPos,
            long columnSize,
            long charLength,
            Integer scale,
            Integer precision,
            int radix,
            boolean notNull,
            String remarks,
            String defaultValue,
            boolean autoIncrement,
            boolean autoGenerated)
            throws DBException {
        return new TurboGraphPPTableColumn(table, columnName, typeName, columnSize, precision, scale);
    }
    
    @Override
    public String getTableDDL(
            DBRProgressMonitor monitor, GenericTableBase sourceObject, Map<String, Object> options)
            throws DBException {
        return "-- View definition not available";
    }
    
    @Override
    public boolean supportsTableDDLSplit(GenericTableBase sourceObject) {
        return false;
    }
}
