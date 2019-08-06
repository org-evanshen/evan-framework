package test.org.evanframework.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created on 2017/8/15.
 *
 * @author evan.shen
 */
public class DemoList extends ArrayList<Demo> {
    private static final long serialVersionUID = 1063711023723603249L;

    public DemoList() {
    }
    public DemoList(List<Demo> demos) {
        if (demos != null) {
            Collections.addAll(this, demos.toArray(new Demo[]{}));
        }
    }
}
