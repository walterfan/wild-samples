package com.github.walterfan.example.dto;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Module implements Comparable<Module> {

    private int moduleID;

    private String moduleName;

    private String description;

    private int parentModuleID;

    private int moduleStatus=0;

    private String moduleLink;

    private List<Module> subModuleList = new ArrayList<Module>(5);

    public Module() {

    }

    public Module(String moduleName, String description) {
        this.moduleName = moduleName;
        this.description = description;
    }

    public void removeSubModule(Module module) {
        this.subModuleList.remove(module);
    }

    public void addSubModule(Module module) {
        this.subModuleList.add(module);
    }

    public boolean hasSubModule() {
        return !CollectionUtils.isEmpty(this.subModuleList);
    }

    public int getModuleID() {
        return moduleID;
    }

    public void setModuleID(int moduleID) {
        this.moduleID = moduleID;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getParentModuleID() {
        return parentModuleID;
    }

    public void setParentModuleID(int parentModuleID) {
        this.parentModuleID = parentModuleID;
    }

    public int getModuleStatus() {
        return moduleStatus;
    }

    public void setModuleStatus(int moduleStatus) {
        this.moduleStatus = moduleStatus;
    }

    public String getModuleLink() {
        return moduleLink;
    }

    public void setModuleLink(String moduleLink) {
        this.moduleLink = moduleLink;
    }

    public List<Module> getSubModuleList() {
        return subModuleList;
    }

    public void setSubModuleList(List<Module> subModuleList) {
        this.subModuleList = subModuleList;
    }

    public static int compare(Module o1, Module o2) {
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 == null) {
            return 1;
        } else if (o2 == null) {
            return -1;
        } else {
            if (o1.getModuleID() > o2.getModuleID()) {
                return 1;
            } else if (o1.getModuleID() == o2.getModuleID()) {
                return 0;
            } else {
                return -1;
            }

        }

    }

    public int compareTo(Module o) {
        if(o == null || this.getModuleID() > o.getModuleID()) {
            return 1;
        } else if(this.getModuleID() == o.getModuleID()) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Module module = (Module) o;

        return moduleID == module.moduleID;
    }

    @Override
    public int hashCode() {
        return moduleID;
    }

    @Override
    public String toString() {
        return "Module{" +
                "moduleID=" + moduleID +
                ", moduleName='" + moduleName + '\'' +
                ", description='" + description + '\'' +
                ", parentModuleID=" + parentModuleID +
                ", moduleStatus=" + moduleStatus +
                ", moduleLink='" + moduleLink + '\'' +
                ", subModuleList=" + subModuleList +
                '}';
    }
}


