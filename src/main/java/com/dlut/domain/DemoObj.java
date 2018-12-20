package com.dlut.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author fys
 * 这里得加xmlrootelement
 */
@XmlRootElement
public class DemoObj
{
    public DemoObj()
    {
        super();
    }

    public DemoObj(Long id, String name)
    {
        super();
        this.id = id;
        this.name = name;
    }

    private Long id;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    private String name;

}
