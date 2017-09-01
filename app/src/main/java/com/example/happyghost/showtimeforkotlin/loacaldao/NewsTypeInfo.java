package com.example.happyghost.showtimeforkotlin.loacaldao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Zhao Chenping
 * @creat 2017/8/30.
 * @description
 */
@Entity
public class NewsTypeInfo {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String typeId;
    @Generated(hash = 1707873593)
    public NewsTypeInfo(Long id, String name, String typeId) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
    }
    @Generated(hash = 215923915)
    public NewsTypeInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTypeId() {
        return this.typeId;
    }
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
