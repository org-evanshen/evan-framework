package com.ancun.core.web.webapi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ancun.core.model.PageResult;
import com.ancun.core.service.ServiceResult;

/**
 * <p/>
 * create at 2014年10月28日 下午5:02:07
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 */
public class AncunApiResponse {
    public static final String SUCCESS = "success";
    public static final String WARN = "warn";
    public static final String ERROR = "error";

    /**
     * 返回信息编号
     */
    private int code = AncunApiResponseCode._1000000.getCode();
    /**
     * 返回信息
     */
    private String msg = AncunApiResponseCode._1000000.getMsg();
    /**
     * 日志信息
     */
    private String logno;
    /**
     * 服务端版本号
     */
    private String serversion;
    private Object data;
    private Page page;
    private List<?> list;

    public static AncunApiResponse create() {
        AncunApiResponse ancunApiResponse = new AncunApiResponse();
        return ancunApiResponse;
    }

    public static AncunApiResponse create(Serializable data) {
        return createInner(data);
    }

    public static AncunApiResponse create(AncunApiResponseCode responseCode) {
        return create(responseCode.getCode(), responseCode.getMsg());
    }

    public static AncunApiResponse create(int code, String msg) {
        AncunApiResponse aap = AncunApiResponse.create();
        aap.setCode(code);
        aap.setMsg(msg);

        return aap;
    }

    public static AncunApiResponse create(PageResult<?> pageResult) {
        AncunApiResponse aap = AncunApiResponse.create();
        aap.setPageResult(pageResult);

        if (pageResult == null || pageResult.getData() == null) {
            aap.setList(new ArrayList<Object>());
        } else {
            aap.setList(pageResult.getData());
        }

        return aap;
    }

    public static AncunApiResponse create(List<?> list) {
        AncunApiResponse aap = AncunApiResponse.create();

        if (list == null) {
            aap.setList(new ArrayList<Object>());
        } else {
            aap.setList(list);
        }

        return aap;
    }

    public static AncunApiResponse create(ServiceResult<?> serviceResult) {
        AncunApiResponse aap = AncunApiResponse.create();

        aap.setData(serviceResult.getData());
        aap.setCodeAndMsg(serviceResult.getCode(), serviceResult.getMsg());

        return aap;
    }

    public static AncunApiResponse create(Map<String, ?> map) {
        return createInner(map);
    }

    private static AncunApiResponse createInner(Object obj0) {
        AncunApiResponse aap = AncunApiResponse.create();

        if (obj0 instanceof List) {
            aap.setList((List<?>) obj0);
        } else if (obj0 instanceof PageResult) {
            aap.setPageResult((PageResult<?>) obj0);
        } else if (obj0 == null) {
            aap.setData("");
        } else {
            aap.setData(obj0);
        }

        return aap;
    }

    public void setCodeAndMsg(int code, String msg) {
        setCode(code);
        setMsg(msg);
    }

    public void setResponseCode(AncunApiResponseCodeEnum responseCode, Object... args) {
        setCode(responseCode.getCode());
        setMsg(String.format(responseCode.getMsg(), args));
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLogno() {
        return logno;
    }

    public void setLogno(String logno) {
        this.logno = logno;
    }

    public String getServersion() {
        return serversion;
    }

    public void setServersion(String serversion) {
        this.serversion = serversion;
    }

    public String getType() {
        if (code == AncunApiResponseCode._1000000.getCode()) {
            return SUCCESS;
        } else if (code == AncunApiResponseCode._1000001.getCode() //
                || code == AncunApiResponseCode._1000002.getCode()) {
            return ERROR;
        } else {
            return WARN;
        }
    }

    public boolean isSuccess() {
        return code == AncunApiResponseCode._1000000.getCode();
    }

    public void setPageResult(PageResult<?> pageResult) {
        if (page == null) {
            page = new Page();
        }
        page.setPageNo(pageResult.getPageNo());
        page.setPageCount(pageResult.getPageCount());
        page.setPageSize(pageResult.getPageSize());
        page.setRecordCount(pageResult.getRecordCount());
        this.setPage(page);
        this.setList(pageResult.getData());
    }

    @Override
    public String toString() {
        return "AncunApiResponse [getList()=" + getList() + ", getPage()=" + getPage() + ", getMsg()=" + getMsg()
                + ", getData()=" + getData() + ", getCode()=" + getCode() + ", getLogno()=" + getLogno()
                + ", getServersion()=" + getServersion() + ", getType()=" + getType() + ", isSuccess()=" + isSuccess()
                + "]";
    }

    public class Page {
        private Integer pageSize;
        private Integer pageNo;
        private Long recordCount;
        private Integer pageCount;

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public Long getRecordCount() {
            return recordCount;
        }

        public void setRecordCount(long recordCount) {
            this.recordCount = recordCount;
        }

        public Integer getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        /**
         * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
         */
        public int getFirst() {
            return (pageNo - 1) * pageSize + 1;
        }

        /**
         * 是否还有下一页.
         */
        public boolean isHasNext() {
            return pageNo < pageCount;
        }

        /**
         * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
         */
        public int getNextPage() {
            if (isHasNext()) {
                return pageNo + 1;
            } else {
                return pageNo;
            }
        }

        /**
         * 是否还有上一页.
         */
        public boolean isHasPre() {
            return pageNo > 1;
        }

        /**
         * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
         */
        public int getPrePage() {
            if (isHasPre()) {
                return pageNo - 1;
            } else {
                return pageNo;
            }
        }

        @Override
        public String toString() {
            return "Page [getPageSize()=" + getPageSize() + ", getPageNo()=" + getPageNo() + ", getRecordCount()="
                    + getRecordCount() + ", getPageCount()=" + getPageCount() + "]";
        }

    }

}
