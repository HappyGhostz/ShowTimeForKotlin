package com.example.happyghost.showtimeforkotlin.loacaldao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Zhao Chenping
 * @creat 2017/9/27.
 * @description
 */
@Entity
public class LocalBookInfo {
    @Id(autoincrement = true)
    public Long id;

    public String url;
    public String author;
    public String cover;
    public String shortIntro;
    public String title;
    public boolean hasCp;
    public boolean isTop = false;
    public boolean isSeleted = false;
    public boolean showCheckBox = false;
    public boolean isFromSD = false;
    public String path = "";
    public int latelyFollower;
    public double retentionRatio;
    public String updated = "";
    public int chaptersCount;
    public String lastChapter;
    public String recentReadingTime = "";
    @Generated(hash = 525130181)
    public LocalBookInfo(Long id, String url, String author, String cover,
            String shortIntro, String title, boolean hasCp, boolean isTop,
            boolean isSeleted, boolean showCheckBox, boolean isFromSD, String path,
            int latelyFollower, double retentionRatio, String updated,
            int chaptersCount, String lastChapter, String recentReadingTime) {
        this.id = id;
        this.url = url;
        this.author = author;
        this.cover = cover;
        this.shortIntro = shortIntro;
        this.title = title;
        this.hasCp = hasCp;
        this.isTop = isTop;
        this.isSeleted = isSeleted;
        this.showCheckBox = showCheckBox;
        this.isFromSD = isFromSD;
        this.path = path;
        this.latelyFollower = latelyFollower;
        this.retentionRatio = retentionRatio;
        this.updated = updated;
        this.chaptersCount = chaptersCount;
        this.lastChapter = lastChapter;
        this.recentReadingTime = recentReadingTime;
    }
    @Generated(hash = 388922807)
    public LocalBookInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getCover() {
        return this.cover;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }
    public String getShortIntro() {
        return this.shortIntro;
    }
    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public boolean getHasCp() {
        return this.hasCp;
    }
    public void setHasCp(boolean hasCp) {
        this.hasCp = hasCp;
    }
    public boolean getIsTop() {
        return this.isTop;
    }
    public void setIsTop(boolean isTop) {
        this.isTop = isTop;
    }
    public boolean getIsSeleted() {
        return this.isSeleted;
    }
    public void setIsSeleted(boolean isSeleted) {
        this.isSeleted = isSeleted;
    }
    public boolean getShowCheckBox() {
        return this.showCheckBox;
    }
    public void setShowCheckBox(boolean showCheckBox) {
        this.showCheckBox = showCheckBox;
    }
    public boolean getIsFromSD() {
        return this.isFromSD;
    }
    public void setIsFromSD(boolean isFromSD) {
        this.isFromSD = isFromSD;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public int getLatelyFollower() {
        return this.latelyFollower;
    }
    public void setLatelyFollower(int latelyFollower) {
        this.latelyFollower = latelyFollower;
    }
    public double getRetentionRatio() {
        return this.retentionRatio;
    }
    public void setRetentionRatio(double retentionRatio) {
        this.retentionRatio = retentionRatio;
    }
    public String getUpdated() {
        return this.updated;
    }
    public void setUpdated(String updated) {
        this.updated = updated;
    }
    public int getChaptersCount() {
        return this.chaptersCount;
    }
    public void setChaptersCount(int chaptersCount) {
        this.chaptersCount = chaptersCount;
    }
    public String getLastChapter() {
        return this.lastChapter;
    }
    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }
    public String getRecentReadingTime() {
        return this.recentReadingTime;
    }
    public void setRecentReadingTime(String recentReadingTime) {
        this.recentReadingTime = recentReadingTime;
    }
   
}
