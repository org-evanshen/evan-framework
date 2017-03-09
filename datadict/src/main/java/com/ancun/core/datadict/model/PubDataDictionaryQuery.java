package com.ancun.core.datadict.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ancun.core.query.AbstractQueryParam;
import com.ancun.core.query.QueryParam;

/**
 * 数据字典查询对象
 */
public class PubDataDictionaryQuery extends AbstractQueryParam implements QueryParam, Serializable {
    private static final long serialVersionUID = 13817196699921L;

    private String dictText;//数据字典的显示文本
    private String dictValue;//数据字典的值
    private String dictGroup;//分组
    private BigDecimal sortNumFrom;//排序
    private BigDecimal sortNumTo;//排序
    private Date gmtCreateFrom;//
    private Date gmtCreateTo;//
    private String parentValue;//上级数据字典值
    private String textColor;//文本颜色
    private Date gmtModifyFrom;//
    private Date gmtModifyTo;//
    private String status;//1 正常 -1 已删除
    private String[] statusArray;//1 正常 -1 已删除
    private String subSystem;//数据字典所子频道 多个以','分割
    private String extend1;
    private String extend2;
    private String extend3;

    /***
     * 数据字典的显示文本
     */
    public String getDictText() {
        return dictText;
    }

    /***
     * 数据字典的显示文本
     */
    public void setDictText(String dictText) {
        this.dictText = dictText;
    }

    /***
     * 数据字典的值
     */
    public String getDictValue() {
        return dictValue;
    }

    /***
     * 数据字典的值
     */
    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    /***
     * 分组
     */
    public String getDictGroup() {
        return dictGroup;
    }

    /***
     * 分组
     */
    public void setDictGroup(String dictGroup) {
        this.dictGroup = dictGroup;
    }

    /***
     * 排序
     */
    public BigDecimal getSortNumFrom() {
        return sortNumFrom;
    }

    /***
     * 排序
     */
    public void setSortNumFrom(BigDecimal sortNumFrom) {
        this.sortNumFrom = sortNumFrom;
    }

    /***
     * 排序
     */
    public BigDecimal getSortNumTo() {
        return sortNumTo;
    }

    /***
     * 排序
     */
    public void setSortNumTo(BigDecimal sortNumTo) {
        this.sortNumTo = sortNumTo;
    }

    /****/
    public Date getGmtCreateFrom() {
        return gmtCreateFrom;
    }

    /****/
    public void setGmtCreateFrom(Date gmtCreateFrom) {
        this.gmtCreateFrom = gmtCreateFrom;
    }

    /****/
    public Date getGmtCreateTo() {
        return gmtCreateTo;
    }

    /****/
    public void setGmtCreateTo(Date gmtCreateTo) {
        this.gmtCreateTo = gmtCreateTo;
    }

    /***
     * 上级数据字典值
     */
    public String getParentValue() {
        return parentValue;
    }

    /***
     * 上级数据字典值
     */
    public void setParentValue(String parentValue) {
        this.parentValue = parentValue;
    }

    /***
     * 文本颜色
     */
    public String getTextColor() {
        return textColor;
    }

    /***
     * 文本颜色
     */
    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    /****/
    public Date getGmtModifyFrom() {
        return gmtModifyFrom;
    }

    /****/
    public void setGmtModifyFrom(Date gmtModifyFrom) {
        this.gmtModifyFrom = gmtModifyFrom;
    }

    /****/
    public Date getGmtModifyTo() {
        return gmtModifyTo;
    }

    /****/
    public void setGmtModifyTo(Date gmtModifyTo) {
        this.gmtModifyTo = gmtModifyTo;
    }

    /***
     * 1 正常 -1 已删除
     */
    public String getStatus() {
        return status;
    }

    /***
     * 1 正常 -1 已删除
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /***
     * 1 正常 -1 已删除
     */
    public String[] getStatusArray() {
        return statusArray;
    }

    /***
     * 1 正常 -1 已删除
     */
    public void setStatusArray(String... statusArray) {
        this.statusArray = statusArray;
    }

    /***
     * 数据字典所子频道 多个以','分割
     */
    public String getSubSystem() {
        return subSystem;
    }

    /***
     * 数据字典所子频道 多个以','分割
     */
    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }
}
