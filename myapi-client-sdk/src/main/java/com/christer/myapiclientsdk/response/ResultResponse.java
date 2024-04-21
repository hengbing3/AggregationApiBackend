package com.christer.myapiclientsdk.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-04-21 17:10
 * Description:
 */
public class ResultResponse implements Serializable {

    private static final long serialVersionUID = 739187579297437618L;

    private Map<String, Object> data = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
