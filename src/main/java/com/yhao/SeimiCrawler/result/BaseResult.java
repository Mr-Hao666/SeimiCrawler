package com.yhao.SeimiCrawler.result;



import com.yhao.SeimiCrawler.enums.ResultStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Page;

@ApiModel("基础返回类型")
public class BaseResult<V> {
    @ApiModelProperty("返回的VO对象")
    private V data;

    @ApiModelProperty("返回编号")
    private int code;
    @ApiModelProperty("返回分页信息")
    private PageVo page; // 分页信息

    @ApiModelProperty("提示信息")
    private String message;

    @ApiModelProperty("操作是否成功")
    private boolean successful = true;

    public BaseResult() {
        super();
    }

    public BaseResult(int code, String message, boolean successful) {
        super();
        this.message = message;
        this.code = code;
        this.successful = successful;

    }

//	/**
//	 * 子类如果需要方便设值  则复写带Page参数的构造方法
//	 * @param page
//	 */
//	public BaseResult(Page page){
//		this.page=new PageVo(page);
//		this.data=page.getContent()
//
//	}

    public BaseResult(ResultStatus resultStatus) {
        super();
        this.message = resultStatus.getMessage();
        this.code = resultStatus.getCode();
    }

    public BaseResult(ResultStatus resultStatus, boolean successful) {
        super();
        this.message = resultStatus.getMessage();
        this.code = resultStatus.getCode();
        this.successful = successful;
    }

    public BaseResult(V v, ResultStatus resultStatus) {
        this.data = v;
        this.code = resultStatus.getCode();
        this.message = resultStatus.getMessage();
    }

    public BaseResult(PageVo page, V v, ResultStatus resultStatus) {
        this.page = page;
        this.data = v;
        this.message = resultStatus.getMessage();
        this.code = resultStatus.getCode();
    }

    public static BaseResult success() {
        return new BaseResult(ResultStatus.SUCCESS);
    }

    public static BaseResult success(String msg) {
        return new BaseResult(ResultStatus.SUCCESS.setMessage(msg));
    }

    public BaseResult(V v, int code, String msg) {
        super();
        this.data = v;
        this.message = msg;
        this.code = code;
    }

    public BaseResult(int code, String msg) {
        super();
        this.message = msg;
        this.code = code;
    }

    public static <T> BaseResult success(T page) {

        if (page == null) {
            return new BaseResult<>(null, ResultStatus.SUCCESS);
        } else if (page instanceof Page) {
            Page<T> pd = (Page<T>) page;
            return new BaseResult<>(new PageVo(pd), pd.getContent(), ResultStatus.SUCCESS);
        } else {
            return new BaseResult<>(page, ResultStatus.SUCCESS);
        }
    }

    public static BaseResult failure(String msg) {
        return new BaseResult(ResultStatus.FAILURE.setMessage(msg), false);
    }

    public static BaseResult failure(ResultStatus resultStatus) {
        return new BaseResult(resultStatus, false);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public PageVo getPage() {
        return page;
    }

    public void setPage(PageVo page) {
        this.page = page;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }

    public void setPageByPage(Page page) {
        PageVo pageVo = new PageVo(page.getNumber(), page.getSize(), (int) page.getTotalElements(), page.getTotalPages());
        this.setPage(pageVo);
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public static class PageVo {

        @ApiModelProperty("页码数")
        private int pageNo;
        @ApiModelProperty("每页记录数")
        private int pageSize;
        @ApiModelProperty("记录总数")
        private int total;
        @ApiModelProperty("总页数")
        private int pageTotal;


        public PageVo(Page page) {
            super();
            this.pageNo = page.getNumber();
            this.pageSize = page.getSize();
            this.total = (int) page.getTotalElements();
            this.pageTotal = page.getTotalPages();
        }

        public PageVo() {
            super();
        }

        public PageVo(int pageNo, int pageSize, int total) {
            super();
            this.pageNo = pageNo;
            this.pageSize = pageSize;
            this.total = total;
        }

        public PageVo(int pageNo, int pageSize, int total, int pageTotal) {
            super();
            this.pageNo = pageNo;
            this.pageSize = pageSize;
            this.total = total;
            this.pageTotal = pageTotal;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageTotal() {
            return pageTotal;
        }

        public void setPageTotal(int pageTotal) {
            this.pageTotal = pageTotal;
        }
    }
}
