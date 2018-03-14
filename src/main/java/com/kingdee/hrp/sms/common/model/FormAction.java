package com.kingdee.hrp.sms.common.model;

public class FormAction extends FormActionKey {
    private String name;

    private String nameModify;

    private String text;

    private String textModify;

    private Integer index;

    private Integer accessUse;

    private Integer display;

    private Integer ownerType;

    private Integer group;

    private String icon;

    private String iconModify;

    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNameModify() {
        return nameModify;
    }

    public void setNameModify(String nameModify) {
        this.nameModify = nameModify == null ? null : nameModify.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public String getTextModify() {
        return textModify;
    }

    public void setTextModify(String textModify) {
        this.textModify = textModify == null ? null : textModify.trim();
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getAccessUse() {
        return accessUse;
    }

    public void setAccessUse(Integer accessUse) {
        this.accessUse = accessUse;
    }

    public Integer getDisplay() {
        return display;
    }

    public void setDisplay(Integer display) {
        this.display = display;
    }

    public Integer getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Integer ownerType) {
        this.ownerType = ownerType;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getIconModify() {
        return iconModify;
    }

    public void setIconModify(String iconModify) {
        this.iconModify = iconModify == null ? null : iconModify.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }
}