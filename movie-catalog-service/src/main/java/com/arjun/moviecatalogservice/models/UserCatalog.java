package com.arjun.moviecatalogservice.models;

import java.util.List;

public class UserCatalog {

    private String userId;
    private List<CatalogItem> catalogItems;

    public UserCatalog(String userId, List<CatalogItem> catalogItems) {
        this.userId = userId;
        this.catalogItems = catalogItems;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CatalogItem> getCatalogItems() {
        return catalogItems;
    }

    public void setCatalogItems(List<CatalogItem> catalogItems) {
        this.catalogItems = catalogItems;
    }
}
