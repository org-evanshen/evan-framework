package org.evanframework.utils.support;

import org.evanframework.model.query.AbstractQueryParam;
import org.evanframework.model.query.QueryParam;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * Demo查询对象
 */
public class DemoQuery extends AbstractQueryParam implements QueryParam, Serializable {
	private static final long serialVersionUID = 13792697920061L;

	private long[] idArray;//
	private String fieldRegion;//
	private String imagePath;//
	private String fieldCheckbox;//
	private String fieldProvince;//
	private Date fieldDatetimeFrom;//
	private Date fieldDatetimeTo;//
	private Date gmtCreateFrom;//
	private Date gmtCreateTo;//
	private String imagePathList;//
	private String fieldHtmleditorCut;//
	private String fieldRadio;//
	private BigDecimal fieldNumberFrom;//
	private BigDecimal fieldNumberTo;//
	private String fieldTextarea;//
	private Integer fieldSelect;//
	private Date gmtModifyFrom;//
	private Date gmtModifyTo;//
	private String fieldText;//
	private Date fieldDateFrom;//
	private Date fieldDateTo;//
	private Integer status;//
	private Integer[] statusArray;//
	private String fieldHtmleditor;//
	private String fieldCity;//

	private boolean joinDemoChild1;

	public enum SortCode {
		/** GMT_CREATE DESC */
		_11("GMT_CREATE DESC"),
		/** GMT_CREATE ASC */
		_12("GMT_CREATE ASC"),
		/** GMT_MODIFY DESC */
		_21("GMT_MODIFY DESC"),
		/** GMT_MODIFY ASC */
		_22("GMT_MODIFY ASC"), ;

		SortCode(String sortExpression) {
			this.sortExpression = sortExpression;
		}

		private String sortExpression;

		public String getSortExpression() {
			return sortExpression;
		}
	}

	/****/
	public long[] getIdArray() {
		return idArray;
	}

	/****/
	public void setIdArray(long[] idArray) {
		this.idArray = idArray;
	}

	/****/
	public String getFieldRegion() {
		return fieldRegion;
	}

	/****/
	public void setFieldRegion(String fieldRegion) {
		this.fieldRegion = fieldRegion;
	}

	/****/
	public String getImagePath() {
		return imagePath;
	}

	/****/
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/****/
	public String getFieldCheckbox() {
		return fieldCheckbox;
	}

	/****/
	public void setFieldCheckbox(String fieldCheckbox) {
		this.fieldCheckbox = fieldCheckbox;
	}

	/****/
	public String getFieldProvince() {
		return fieldProvince;
	}

	/****/
	public void setFieldProvince(String fieldProvince) {
		this.fieldProvince = fieldProvince;
	}

	/****/
	public Date getFieldDatetimeFrom() {
		return fieldDatetimeFrom;
	}

	/****/
	public void setFieldDatetimeFrom(Date fieldDatetimeFrom) {
		this.fieldDatetimeFrom = fieldDatetimeFrom;
	}

	/****/
	public Date getFieldDatetimeTo() {
		return fieldDatetimeTo;
	}

	/****/
	public void setFieldDatetimeTo(Date fieldDatetimeTo) {
		this.fieldDatetimeTo = fieldDatetimeTo;
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

	/****/
	public String getImagePathList() {
		return imagePathList;
	}

	/****/
	public void setImagePathList(String imagePathList) {
		this.imagePathList = imagePathList;
	}

	/****/
	public String getFieldHtmleditorCut() {
		return fieldHtmleditorCut;
	}

	/****/
	public void setFieldHtmleditorCut(String fieldHtmleditorCut) {
		this.fieldHtmleditorCut = fieldHtmleditorCut;
	}

	/****/
	public String getFieldRadio() {
		return fieldRadio;
	}

	/****/
	public void setFieldRadio(String fieldRadio) {
		this.fieldRadio = fieldRadio;
	}

	/****/
	public BigDecimal getFieldNumberFrom() {
		return fieldNumberFrom;
	}

	/****/
	public void setFieldNumberFrom(BigDecimal fieldNumberFrom) {
		this.fieldNumberFrom = fieldNumberFrom;
	}

	/****/
	public BigDecimal getFieldNumberTo() {
		return fieldNumberTo;
	}

	/****/
	public void setFieldNumberTo(BigDecimal fieldNumberTo) {
		this.fieldNumberTo = fieldNumberTo;
	}

	/****/
	public String getFieldTextarea() {
		return fieldTextarea;
	}

	/****/
	public void setFieldTextarea(String fieldTextarea) {
		this.fieldTextarea = fieldTextarea;
	}

	/****/
	public Integer getFieldSelect() {
		return fieldSelect;
	}

	/****/
	public void setFieldSelect(Integer fieldSelect) {
		this.fieldSelect = fieldSelect;
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

	/****/
	public String getFieldText() {
		return fieldText;
	}

	/****/
	public void setFieldText(String fieldText) {
		this.fieldText = fieldText;
	}

	/****/
	public Date getFieldDateFrom() {
		return fieldDateFrom;
	}

	/****/
	public void setFieldDateFrom(Date fieldDateFrom) {
		this.fieldDateFrom = fieldDateFrom;
	}

	/****/
	public Date getFieldDateTo() {
		return fieldDateTo;
	}

	/****/
	public void setFieldDateTo(Date fieldDateTo) {
		this.fieldDateTo = fieldDateTo;
	}

	/****/
	public String getFieldHtmleditor() {
		return fieldHtmleditor;
	}

	/****/
	public void setFieldHtmleditor(String fieldHtmleditor) {
		this.fieldHtmleditor = fieldHtmleditor;
	}

	/****/
	public String getFieldCity() {
		return fieldCity;
	}

	/****/
	public void setFieldCity(String fieldCity) {
		this.fieldCity = fieldCity;
	}

	public boolean isJoinDemoChild1() {
		return joinDemoChild1;
	}

	public void setJoinDemoChild1(boolean joinDemoChild1) {
		this.joinDemoChild1 = joinDemoChild1;
	}

	@Override
	public String toString() {
		return "DemoQuery [idArray=" + Arrays.toString(idArray) + ", fieldRegion=" + fieldRegion
				+ ", imagePath=" + imagePath + ", fieldCheckbox=" + fieldCheckbox + ", fieldProvince="
				+ fieldProvince + ", fieldDatetimeFrom=" + fieldDatetimeFrom + ", fieldDatetimeTo="
				+ fieldDatetimeTo + ", gmtCreateFrom=" + gmtCreateFrom + ", gmtCreateTo=" + gmtCreateTo
				+ ", imagePathList=" + imagePathList + ", fieldHtmleditorCut=" + fieldHtmleditorCut
				+ ", fieldRadio=" + fieldRadio + ", fieldNumberFrom=" + fieldNumberFrom + ", fieldNumberTo="
				+ fieldNumberTo + ", fieldTextarea=" + fieldTextarea + ", fieldSelect=" + fieldSelect
				+ ", gmtModifyFrom=" + gmtModifyFrom + ", gmtModifyTo=" + gmtModifyTo + ", fieldText="
				+ fieldText + ", fieldDateFrom=" + fieldDateFrom + ", fieldDateTo=" + fieldDateTo
				+ ", status=" + status + ", statusArray=" + Arrays.toString(statusArray)
				+ ", fieldHtmleditor=" + fieldHtmleditor + ", fieldCity=" + fieldCity + ", joinDemoChild1="
				+ joinDemoChild1 + ", getSort()=" + getSort() + ", getPageNo()=" + getPageNo()
				+ ", getPageSize()=" + getPageSize() + ", getStartRow()=" + getStartRow() + ", getEndRow()="
				+ getEndRow() + ", getColumns()=" + Arrays.toString(getColumns()) + ", getSortCode()="
				+ getSortCode() + ", isSortByDefault()=" + isSortByDefault() + ", isIncludeDeleted()="
				+ isIncludeDeleted() + "]";
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer[] getStatusArray() {
		return statusArray;
	}

	public void setStatusArray(Integer... statusArray) {
		this.statusArray = statusArray;
	}

}
