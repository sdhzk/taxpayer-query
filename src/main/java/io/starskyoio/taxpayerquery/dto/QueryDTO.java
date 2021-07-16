package io.starskyoio.taxpayerquery.dto;

public class QueryDTO {
    private Integer pageIndex;
    private Integer pageSize;
    private String pjnd;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getPjnd() {
        return pjnd;
    }

    public void setPjnd(String pjnd) {
        this.pjnd = pjnd;
    }
}
