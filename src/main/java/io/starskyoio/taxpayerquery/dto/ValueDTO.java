package io.starskyoio.taxpayerquery.dto;

import java.util.List;

public class ValueDTO {
    private List<TaxpayerInfo> result;
    private Integer total;
    public List<TaxpayerInfo> getResult() {
        return result;
    }

    public void setResult(List<TaxpayerInfo> result) {
        this.result = result;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
