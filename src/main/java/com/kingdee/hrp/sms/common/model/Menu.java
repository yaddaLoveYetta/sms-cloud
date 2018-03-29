package com.kingdee.hrp.sms.common.model;

import com.kingdee.hrp.sms.common.RootModel;

public class Menu extends RootModel {
    private Integer id;

    private String name;

    private Integer index;

    private String icon;

    private String url;

    private Integer parentId;

    private String desc;

    private Boolean visible;

    private Integer formActionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getFormActionId() {
        return formActionId;
    }

    public void setFormActionId(Integer formActionId) {
        this.formActionId = formActionId;
    }
}