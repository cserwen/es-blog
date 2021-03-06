package xyz.litterboys.esblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;

@TableName("article")
public class Article {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String description;
    private String content;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Boolean isDeleted;
    private Boolean isDiscuss;
    private Boolean isOpen;
    private Integer readTimes;
    private Integer viewerNums;
    private Boolean isReward;
    private Integer categoryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Boolean getDiscuss() {
        return isDiscuss;
    }

    public void setDiscuss(Boolean discuss) {
        isDiscuss = discuss;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public Integer getReadTimes() {
        return readTimes;
    }

    public void setReadTimes(Integer readTimes) {
        this.readTimes = readTimes;
    }

    public Integer getViewerNums() {
        return viewerNums;
    }

    public void setViewerNums(Integer viewerNums) {
        this.viewerNums = viewerNums;
    }

    public Boolean getReward() {
        return isReward;
    }

    public void setReward(Boolean reward) {
        isReward = reward;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDeleted=" + isDeleted +
                ", isDiscuss=" + isDiscuss +
                ", isOpen=" + isOpen +
                ", readTimes=" + readTimes +
                ", viewerNums=" + viewerNums +
                ", isReward=" + isReward +
                ", categoryId=" + categoryId +
                '}';
    }
}
