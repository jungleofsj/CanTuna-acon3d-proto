package CanTuna.CanTunaacon3d.controller;

public class ItemForm {
    private long itemId;
    private String itemNameKor;
    private String itemNameEng;
    private String itemNameCHN;

    private String itemTextKor;
    private String itemTextEng;
    private String itemTextCHN;

    private String itemCreator;
    private String itemEditor;
    private Double itemPrice;

    private Double itemCommissonPct;

    private Boolean itemApproved;


    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemNameKor() {
        return itemNameKor;
    }

    public void setItemNameKor(String itemNameKor) {
        this.itemNameKor = itemNameKor;
    }

    public String getItemNameEng() {
        return itemNameEng;
    }

    public void setItemNameEng(String itemNameEng) {
        this.itemNameEng = itemNameEng;
    }

    public String getItemNameChn() {
        return itemNameCHN;
    }

    public void setItemNameChn(String itemNameCHN) {
        this.itemNameCHN = itemNameCHN;
    }

    public String getItemTextKor() {
        return itemTextKor;
    }

    public void setItemTextKor(String itemTextKor) {
        this.itemTextKor = itemTextKor;
    }

    public String getItemTextEng() {
        return itemTextEng;
    }

    public void setItemTextEng(String itemTextEng) {
        this.itemTextEng = itemTextEng;
    }

    public String getItemTextChn() {
        return itemTextCHN;
    }

    public void setItemTextChn(String itemTextCHN) {
        this.itemTextCHN = itemTextCHN;
    }

    public String getItemCreator() {
        return itemCreator;
    }

    public void setItemCreator(String itemCreator) {
        this.itemCreator = itemCreator;
    }

    public String getItemEditor() {
        return itemEditor;
    }

    public void setItemEditor(String itemEditor) {
        this.itemEditor = itemEditor;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Double getItemCommissonPct() {
        return itemCommissonPct;
    }

    public void setItemCommissonPct(Double itemCommissonPct) {
        this.itemCommissonPct = itemCommissonPct;
    }

    public Boolean getItemApproved() {
        return itemApproved;
    }

    public void setItemApproved(Boolean itemApproved) {
        this.itemApproved = itemApproved;
    }

}
