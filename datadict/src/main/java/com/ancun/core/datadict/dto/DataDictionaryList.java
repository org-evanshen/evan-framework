package com.ancun.core.datadict.dto;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ancun.core.model.DataDictionary;

public class DataDictionaryList extends ArrayList<DataDictionary> {
    private static final long serialVersionUID = 1755675279049794414L;

    public DataDictionaryList() {

    }

    public DataDictionaryList(List<DataDictionary> dataDictionary) {
        Collections.addAll(this, dataDictionary.toArray(new DataDictionary[]{}));
    }
}
