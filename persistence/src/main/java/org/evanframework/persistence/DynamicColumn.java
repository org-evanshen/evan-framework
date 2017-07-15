package org.evanframework.persistence;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DynamicColumn {
    private final Set<String> sqlColumns = new HashSet<String>();

    public void addColumns(String table, String columnsString) {
        columnsString = table + "." + StringUtils.replace(columnsString, ",", ("," + table + "."));
        sqlColumns.add(columnsString);
    }

    public void clear() {
        sqlColumns.clear();
    }

    public String[] getArray() {
        return sqlColumns.toArray(new String[]{});
    }

    public Collection<String> getCollection() {
        return sqlColumns;
    }
}
