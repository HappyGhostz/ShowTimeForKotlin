package com.example.happyghost.showtimeforkotlin.loacaldao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Zhao Chenping
 * @creat 2017/11/13.
 * @description
 */
@Entity
public class BookChapterInfo {
    @Id(autoincrement = true)
    public Long id;
    public String bookId;
    public String title;
    public String link;
    @Generated(hash = 1094774466)
    public BookChapterInfo(Long id, String bookId, String title, String link) {
        this.id = id;
        this.bookId = bookId;
        this.title = title;
        this.link = link;
    }
    @Generated(hash = 2099363825)
    public BookChapterInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBookId() {
        return this.bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getLink() {
        return this.link;
    }
    public void setLink(String link) {
        this.link = link;
    }

}
