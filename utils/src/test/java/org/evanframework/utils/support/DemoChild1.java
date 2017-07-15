package org.evanframework.utils.support;

import java.io.Serializable;
import java.util.Date;

/**
 * DEMO 子表
 */
public class DemoChild1 implements Serializable {
    private static final long serialVersionUID = 13800282362952L;

    private Long id;//
    private Long demoId;//
    private Date gmtModify;//
    private String fieldText;//
    private Date gmtCreate;//
    private Integer status;//1:正常,  -1:已删除

    private Demo demo;

    public DemoChild1() {
    }

    /**
     * @param id --
     */
    public DemoChild1(Long id) {
        this.id = id;
    }

    /**
     *
     */
    public Long getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     */

    public Long getDemoId() {
        return demoId;
    }

    /**
     *
     */
    public void setDemoId(Long demoId) {
        this.demoId = demoId;
    }

    /**
     *
     */
    public Date getGmtModify() {
        return gmtModify;
    }

    /**
     *
     */
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    /**
     *
     */
    public String getFieldText() {
        return fieldText;
    }

    /**
     *
     */
    public void setFieldText(String fieldText) {
        this.fieldText = fieldText;
    }

    /**
     *
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     *
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 1:正常, -1:已删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1:正常, -1:已删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DemoChild1 [ id=" + id + ", demoId=" + demoId + ", gmtModify=" + gmtModify + ", fieldText=" + fieldText
                + ", gmtCreate=" + gmtCreate + ", status=" + status + "]";

    }

    public Demo getDemo() {
        return demo;
    }

    public void setDemo(Demo demo) {
        this.demo = demo;
    }
}
