package io.starskyoio.taxpayerquery.dto;

import com.alibaba.excel.annotation.ExcelProperty;

public class TaxpayerInfo {
    @ExcelProperty("纳税人名称")
    private String nsrmc;
    @ExcelProperty("统一社会信用代码")
    private String nsrsbh;
    @ExcelProperty("评价年度")
    private String pjnd;
    @ExcelProperty("评价结果")
    private String pjjg;
    @ExcelProperty("主管税务机")
    private String swjgmc;

    public String getNsrmc() {
        return nsrmc;
    }

    public void setNsrmc(String nsrmc) {
        this.nsrmc = nsrmc;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getPjnd() {
        return pjnd;
    }

    public void setPjnd(String pjnd) {
        this.pjnd = pjnd;
    }

    public String getPjjg() {
        return pjjg;
    }

    public void setPjjg(String pjjg) {
        this.pjjg = pjjg;
    }

    public String getSwjgmc() {
        return swjgmc;
    }

    public void setSwjgmc(String swjgmc) {
        this.swjgmc = swjgmc;
    }
}
