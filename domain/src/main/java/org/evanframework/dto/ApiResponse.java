package org.evanframework.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Restful接口输出格式封装
 * @author shenwei
 * @version 17/3/11 下午11:57
 * @since 1.0
 */
public class ApiResponse extends OperateResult implements Serializable {
    private static final long serialVersionUID = 2941991011401190488L;

    private Page page;
    private List<?> list;

    public ApiResponse() {
        super();
    } 

    private ApiResponse(String code, String msg, Object data) {
        super(code, msg, data);
    }

    public static ApiResponse create() {
        return new ApiResponse();
    }

    public static ApiResponse create(String code, String msg, Serializable data) {
        return new ApiResponse(code, msg, data);
    }

    public static ApiResponse create(String code, String msg) {
        return new ApiResponse(code, msg, null);
    }

    public static ApiResponse create(Object data) {
        return new ApiResponse(null, null, data);
    }

    public static ApiResponse create(PageResult<?> pageResult) {
        ApiResponse response = new ApiResponse();
        response.setPageResult(pageResult);

        if (pageResult == null || pageResult.getData() == null) {
            response.setList(new ArrayList<Object>());
        } else {
            response.setList(pageResult.getData());
        }

        return response;
    }

    public static ApiResponse create(List<?> list) {
        ApiResponse response = new ApiResponse();
        if (list == null) {
            response.setList(new ArrayList<Object>());
        } else {
            response.setList(list);
        }

        return response;
    }

    public static ApiResponse create(OperateResult operateResult) {
        ApiResponse response = new ApiResponse();

        response.setCode(operateResult.getCode());
        response.setData(operateResult.getData());
        response.setMsg(operateResult.getMsg());

        return response;
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

//    public void setData(Object data) {
//        this.data = data;
//    }
//
//    public String getServersion() {
//        return serversion;
//    }
//
//    public void setServersion(String serversion) {
//        this.serversion = serversion;
//    }

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

    //    public EnumOperateResult getResult() {
//        return result;
//    }
//
//    public void setRsult(String code) {
//        this.result = result;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }

//    public Object getData() {
//        return data;
//    }
//
//    public void setData(Serializable data) {
//        this.data = data;
//    }

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
