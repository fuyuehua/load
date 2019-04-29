package com.rip.load.otherPojo.taoBao;
/**
 *商品信息
 */
public class Items {

    /**
     *商品 ID
     */
    private String itemId;

    /**
     *商品名称
     */
    private String itemName;

    /**
     *商品 Url
     */
    private String itemUrl;

    /**
     *商品单价
     */
    private String itemPrice;

    /**
     *商品数量
     */
    private String itemQuantity;


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
}
