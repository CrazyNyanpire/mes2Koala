package org.seu.acetec.mes2Koala.core.domain;

import javax.persistence.*;

@Entity
@Table(name = "E_TESTING_TEMPLATE")
@Access(AccessType.PROPERTY)
public class TestingTemplate extends MES2AbstractEntity {

    /**
     *
     */
    private static final long serialVersionUID = -5689767762215622480L;

    private String name;
    private String content;

    @Column(name = "name", unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String[] businessKeys() {
        return new String[0];
    }

}
