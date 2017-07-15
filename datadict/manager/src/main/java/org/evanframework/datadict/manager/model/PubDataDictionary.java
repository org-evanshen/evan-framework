package org.evanframework.datadict.manager.model;

import org.evanframework.datadict.service.dto.DataDictionary;

import java.io.Serializable;
import java.util.Date;


/**
 * 数据字典
 */
public class PubDataDictionary implements Serializable {
    private static final long serialVersionUID = 1L;

    private String dictText;// 数据字典的显示文本
    private String dictValue;// 数据字典的值
    private String dictGroup;// 分组
    private Integer sortNum;// 排序
    private Date gmtCreate;//
    private String parentValue;// 上级数据字典值
    private String textColor;// 文本颜色
    private Date gmtModify;//
    private int status = 1;// 1 正常 -1 已删除
    private String subSystem;// 数据字典所子频道 多个以','分割
    private PubDataDictionary parentDataDictionary;
    private boolean disabled;
    private String extend1;//扩展1
    private String extend3;//扩展3
    private String extend2;//扩展2

    //private List<PubDataDictionary> children;
    private boolean leaf = true;

    public PubDataDictionary() {
    }

    /**
     * @param dictText  -- 数据字典的显示文本
     * @param dictValue -- 数据字典的值
     * @param dictGroup -- 分组
     */
    public PubDataDictionary(String dictText, String dictValue, String dictGroup) {
        this.dictText = dictText;
        this.dictValue = dictValue;
        this.dictGroup = dictGroup;
    }

    public PubDataDictionary(String dictValue, String dictGroup) {
        this.dictValue = dictValue;
        this.dictGroup = dictGroup;
    }

    public DataDictionary toDTO() {
        DataDictionary dto = new DataDictionary();
        dto.setColor(this.getTextColor());
        dto.setText(this.getDictText());
        dto.setValue(this.getDictValue());
        dto.setLeaf(this.isLeaf());
        dto.setExtend1(this.getExtend1());
        dto.setExtend2(this.getExtend2());
        dto.setExtend3(this.getExtend3());
        return dto;
    }

    public String toString() {
        return "PubDataDictionary [dictText=" + dictText + ", dictValue=" + dictValue + ", dictGroup=" + dictGroup
                + "]";
    }

    /**
     * 数据字典的显示文本
     */
    public String getDictText() {
        return dictText;
    }

    /**
     * 数据字典的显示文本
     */
    public void setDictText(String dictText) {
        this.dictText = dictText;
    }

    /**
     * 数据字典的值
     */
    public String getDictValue() {
        return dictValue;
    }

    /**
     * 数据字典的值
     */
    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    /**
     * 分组
     */
    public String getDictGroup() {
        return dictGroup;
    }

    /**
     * 分组
     */
    public void setDictGroup(String dictGroup) {
        this.dictGroup = dictGroup;
    }

    /**
     * 排序
     */
    public Integer getSortNum() {
        return sortNum;
    }

    /**
     * 排序
     */
    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    /***/
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /***/
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 上级数据字典值
     */
    public String getParentValue() {
        return parentValue;
    }

    /**
     * 上级数据字典值
     */
    public void setParentValue(String parentValue) {
        this.parentValue = parentValue;
    }

    /**
     * 文本颜色
     */
    public String getTextColor() {
        return textColor;
    }

    /**
     * 文本颜色
     */
    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    /***/
    public Date getGmtModify() {
        return gmtModify;
    }

    /***/
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    /**
     * 1 正常 -1 已删除
     */
    public int getStatus() {
        return status;
    }

    /**
     * 1 正常 -1 已删除
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 数据字典所子频道 多个以','分割
     */
    public String getSubSystem() {
        return subSystem;
    }

    /**
     * 数据字典所子频道 多个以','分割
     */
    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

    public PubDataDictionary getParentDataDictionary() {
        return parentDataDictionary;
    }

    public void setParentDataDictionary(PubDataDictionary parentDataDictionary) {
        this.parentDataDictionary = parentDataDictionary;
    }

    /**
     * 是否可用
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * 是否可用
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * 是否叶子
     */
    public boolean isLeaf() {
        return leaf;
    }

    /**
     * 是否叶子
     */
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

}
