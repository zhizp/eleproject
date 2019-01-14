package com.ele.project.common.pojo;

import java.io.Serializable;
import java.util.Map;

public class InitDTO implements Serializable {
    private static final long serialVersionUID = -201708231933L;

    private Map<String, Object> info;
    private Map<String, Object> user;
    private Map<String, Object> menus;

    public Map<String, Object> getMenus() {
        return menus;
    }

    public void setMenus(Map<String, Object> menus) {
        this.menus = menus;
    }

    public Map<String, Object> getUser() {
        return user;
    }

    public void setUser(Map<String, Object> user) {
        this.user = user;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }
}
