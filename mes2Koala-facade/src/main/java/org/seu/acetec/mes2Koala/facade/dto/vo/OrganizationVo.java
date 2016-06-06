package org.seu.acetec.mes2Koala.facade.dto.vo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yuxiangque
 * @version 2016/4/27
 */
public class OrganizationVo {
    public static final String COMPANY = "Company";
    public static final String DEPARTMENT = "Department";

    private Long id;

    private String name;

    private Long pid;

    private Set<OrganizationVo> children = new HashSet<>();

    private List<EmployeeVo> employeeVos = new ArrayList<>();

    public OrganizationVo(Long id, Long pid, String name) {
        this.setId(id);
        this.setName(name);
        this.setPid(pid);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<OrganizationVo> getChildren() {
        return children;
    }

    public void setChildren(Set<OrganizationVo> children) {
        this.children = children;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public List<EmployeeVo> getEmployeeVos() {
        return employeeVos;
    }

    public void setEmployeeVos(List<EmployeeVo> employeeVos) {
        this.employeeVos = employeeVos;
    }
}
