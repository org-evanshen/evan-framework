package org.evanframework.datadict.service.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataDictionaryList extends ArrayList<DataDictionary> {
    private static final long serialVersionUID = 1755675279049794414L;

    public DataDictionaryList() {

    }

    public DataDictionaryList(int size) {
        super(size);
    }

    public DataDictionaryList(List<DataDictionary> dataDictionary) {
        Collections.addAll(this, dataDictionary.toArray(new DataDictionary[]{}));
    }
}
