package io.starskyoio.taxpayerquery.dto;

public class QueryResultDTO {
    private String message;
    private ValueDTO value;
    private Boolean success;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ValueDTO getValue() {
        return value;
    }

    public void setValue(ValueDTO value) {
        this.value = value;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
