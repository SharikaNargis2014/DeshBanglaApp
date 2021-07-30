package com.deshbangla.shrimpandfish.response;

import com.deshbangla.shrimpandfish.model.Search;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("search")
    @Expose
    private Search search;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

}
