package org.evanframework.utils.support;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 示例
 */
public class Demo implements Serializable {
    private static final long serialVersionUID = 13792681968342L;

    private Long id;//
    private String fieldRegion;//
    private String fieldRegionName;//
    private String imagePath;//
    private String fieldCheckbox;//
    private String[] fieldCheckboxArray;//
    private String fieldCheckboxText;//
    private EnumSex[] fieldCheckboxEnumArray;//
    private String fieldProvince;//
    private String fieldProvinceName;//
    private Date fieldDatetime;//
    private Date gmtCreate;//
    private String gmtCreateText;//
    private String imagePathList;//
    private String fieldHtmleditorCut;//
    private String fieldRadio;//
    private String fieldRadioText;//
    private EnumSex fieldRadioEnum;//
    private BigDecimal fieldNumber;//
    private String fieldTextarea;//
    private String fieldSelect;//
    private String fieldSelectText;//
    private Date gmtModify;//
    private String fieldText;//
    private Date fieldDate;//
    private Integer status;//
    private String statusText;//
    private String statusColor;//
    private String fieldHtmleditor;//
    private String fieldCity;//
    private String fieldCityName;//

    private List<DemoChild1> demoChild1s;

    public Demo() {
    }

    /**
     * @param id --
     */
    public Demo(Long id) {
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
    public String getFieldRegion() {
        return fieldRegion;
    }

    /**
     *
     */
    public void setFieldRegion(String fieldRegion) {
        this.fieldRegion = fieldRegion;
    }

    /**
     *
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     *
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     *
     */
    public String getFieldProvince() {
        return fieldProvince;
    }

    /**
     *
     */
    public void setFieldProvince(String fieldProvince) {
        this.fieldProvince = fieldProvince;
    }

    /**
     *
     */
    public Date getFieldDatetime() {
        return fieldDatetime;
    }

    /**
     *
     */
    public void setFieldDatetime(Date fieldDatetime) {
        this.fieldDatetime = fieldDatetime;
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
     *
     */
    public String getImagePathList() {
        return imagePathList;
    }

    /**
     *
     */
    public void setImagePathList(String imagePathList) {
        this.imagePathList = imagePathList;
    }

    /**
     *
     */
    public String getFieldHtmleditorCut() {
        return fieldHtmleditorCut;
    }

    /**
     *
     */
    public void setFieldHtmleditorCut(String fieldHtmleditorCut) {
        this.fieldHtmleditorCut = fieldHtmleditorCut;
    }

    /**
     *
     */
    public BigDecimal getFieldNumber() {
        return fieldNumber;
    }

    /**
     *
     */
    public void setFieldNumber(BigDecimal fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    /**
     *
     */
    public String getFieldTextarea() {
        return fieldTextarea;
    }

    /**
     *
     */
    public void setFieldTextarea(String fieldTextarea) {
        this.fieldTextarea = fieldTextarea;
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
    public Date getFieldDate() {
        return fieldDate;
    }

    /**
     *
     */
    public void setFieldDate(Date fieldDate) {
        this.fieldDate = fieldDate;
    }

    /**
     *
     */
    public String getFieldHtmleditor() {
        return fieldHtmleditor;
    }

    /**
     *
     */
    public void setFieldHtmleditor(String fieldHtmleditor) {
        this.fieldHtmleditor = fieldHtmleditor;
    }

    /**
     *
     */
    public String getFieldCity() {
        return fieldCity;
    }

    /**
     *
     */
    public void setFieldCity(String fieldCity) {
        this.fieldCity = fieldCity;
    }

    public List<DemoChild1> getDemoChild1s() {
        return demoChild1s;
    }

    public void setDemoChild1s(List<DemoChild1> demoChild1s) {
        this.demoChild1s = demoChild1s;
    }

    public void addDemoChild1s(DemoChild1... demoChild1s) {
        if (this.demoChild1s == null) {
            this.demoChild1s = new ArrayList<DemoChild1>();
        }
        if (demoChild1s.length > 0) {

            for (DemoChild1 e : demoChild1s) {
                this.demoChild1s.add(e);
            }
        }
    }

    public String getFieldProvinceName() {
        return fieldProvinceName;
    }

    public void setFieldProvinceName(String fieldProvinceName) {
        this.fieldProvinceName = fieldProvinceName;
    }

    public String getFieldRegionName() {
        return fieldRegionName;
    }

    public void setFieldRegionName(String fieldRegionName) {
        this.fieldRegionName = fieldRegionName;
    }

    public String getFieldCityName() {
        return fieldCityName;
    }

    public void setFieldCityName(String fieldCityName) {
        this.fieldCityName = fieldCityName;
    }

    public String getFieldCheckboxText() {
        return fieldCheckboxText;
    }

    public void setFieldCheckboxText(String fieldCheckboxText) {
        this.fieldCheckboxText = fieldCheckboxText;
    }

    public String getFieldCheckbox() {
        return fieldCheckbox;
    }

    public void setFieldCheckbox(String fieldCheckbox) {
        this.fieldCheckbox = fieldCheckbox;
        if (StringUtils.isNotBlank(fieldCheckbox)) {
            String[] tmps = fieldCheckbox.split(",");
            StringBuilder text = new StringBuilder(128);
            List<EnumSex> sexes = new ArrayList<EnumSex>(tmps.length);
            List<String> sexvalues = new ArrayList<String>(tmps.length);
            int sexValue;
            EnumSex sex = null;
            for (String tmp : tmps) {
                sexValue = Integer.valueOf(tmp);
                sexvalues.add(sexValue + "");
                sex = EnumSex.valueOf(sexValue + "");
                sexes.add(sex);
                text.append("," + sex.getText());
            }
            text.delete(0, 1);

            this.fieldCheckboxText = text.toString();
            this.fieldCheckboxArray = sexvalues.toArray(new String[]{});
            this.fieldCheckboxEnumArray = sexes.toArray(new EnumSex[]{});
        }
    }

    public String[] getFieldCheckboxArray() {
        return fieldCheckboxArray;
    }

    public void setFieldCheckboxArray(String[] fieldCheckboxArray) {
        this.fieldCheckboxArray = fieldCheckboxArray;
        if (fieldCheckboxArray != null && fieldCheckboxArray.length > 0) {
            StringBuilder text = new StringBuilder(128);
            StringBuilder value = new StringBuilder(32);
            List<EnumSex> sexes = new ArrayList<EnumSex>(fieldCheckboxArray.length);
            int sexValue;
            EnumSex sex = null;
            for (String fieldCheckbox : fieldCheckboxArray) {
                sexValue = Integer.valueOf(fieldCheckbox);
                sex = EnumSex.valueOf(sexValue+"");
                sexes.add(sex);
                text.append("," + sex.getText());
                value.append("," + sexValue);
            }
            text.delete(0, 1);
            value.delete(0, 1);

            this.fieldCheckboxText = text.toString();
            this.fieldCheckbox = value.toString();
            this.fieldCheckboxEnumArray = sexes.toArray(new EnumSex[]{});
        }

    }

    public EnumSex[] getFieldCheckboxEnumArray() {
        return fieldCheckboxEnumArray;
    }

    public void setFieldCheckboxEnumArray(EnumSex[] fieldCheckboxEnumArray) {
        this.fieldCheckboxEnumArray = fieldCheckboxEnumArray;
    }

    public String getFieldRadio() {
        return fieldRadio;
    }

    public void setFieldRadio(String fieldRadio) {
        this.fieldRadio = fieldRadio;
        if (StringUtils.isNotBlank(fieldRadio)) {
            this.fieldRadioEnum = EnumSex.valueOf(fieldRadio);
            this.fieldRadioText = this.fieldRadioEnum.getText();
        }
    }

    public EnumSex getFieldRadioEnum() {
        return fieldRadioEnum;
    }

    public void setFieldRadioEnum(EnumSex fieldRadioEnum) {
        this.fieldRadioEnum = fieldRadioEnum;

        if (this.fieldRadioEnum != null) {
            this.fieldRadio = fieldRadioEnum.getValue() + "";
            this.fieldRadioText = this.fieldRadioEnum.getText();
        }
    }

    public String getFieldRadioText() {
        return fieldRadioText;
    }

    public String getFieldSelect() {
        return fieldSelect;
    }

    public void setFieldSelect(String fieldSelect) {
        this.fieldSelect = fieldSelect;
    }

    public Integer getStatus() {
        return status;
    }


    @Override
    public String toString() {
        return "Demo [id=" + id + ", fieldRegion=" + fieldRegion + ", fieldRegionName=" + fieldRegionName
                + ", imagePath=" + imagePath + ", fieldCheckbox=" + fieldCheckbox + ", fieldCheckboxArray="
                + Arrays.toString(fieldCheckboxArray) + ", fieldCheckboxText=" + fieldCheckboxText
                + ", fieldCheckboxEnumArray=" + Arrays.toString(fieldCheckboxEnumArray) + ", fieldProvince="
                + fieldProvince + ", fieldProvinceName=" + fieldProvinceName + ", fieldDatetime=" + fieldDatetime
                + ", gmtCreate=" + gmtCreate + ", imagePathList=" + imagePathList + ", fieldHtmleditorCut="
                + fieldHtmleditorCut + ", fieldRadio=" + fieldRadio + ", fieldRadioEnum=" + fieldRadioEnum
                + ", fieldNumber=" + fieldNumber + ", fieldTextarea=" + fieldTextarea + ", fieldSelect=" + fieldSelect
                + ", fieldSelectText=" + fieldSelectText + ", gmtModify=" + gmtModify + ", fieldText=" + fieldText
                + ", fieldDate=" + fieldDate
                + ", fieldHtmleditor=" + fieldHtmleditor + ", fieldCity=" + fieldCity + ", fieldCityName="
                + fieldCityName + ", demoChild1s=" + demoChild1s + "]";
    }

    public String getFieldSelectText() {
        return fieldSelectText;
    }

    public void setFieldSelectText(String fieldSelectText) {
        this.fieldSelectText = fieldSelectText;
    }

    public String getGmtCreateText() {
        return gmtCreateText;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getStatusColor() {
        return statusColor;
    }

    public void setFieldRadioText(String fieldRadioText) {
        this.fieldRadioText = fieldRadioText;
    }

}
