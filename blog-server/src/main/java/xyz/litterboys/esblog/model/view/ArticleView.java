package xyz.litterboys.esblog.model.view;

import java.sql.Timestamp;

public class ArticleView {

    private Integer id;
    private String title;
    private String description;
    private String content;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Boolean isOpen;
    private Integer readTimes;
    private Integer viewerNums;
    private Boolean isReward;
    private Integer categoryId;
    private Integer lastId;
    private String lastName;
    private Integer nextId;
    private String nextName;
    private Boolean hasLast = false;
    private Boolean hasNext = false;

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

    public Integer getLastId() {
        return lastId;
    }

    public void setLastId(Integer lastId) {
        this.lastId = lastId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getNextId() {
        return nextId;
    }

    public void setNextId(Integer nextId) {
        this.nextId = nextId;
    }

    public String  getNextName() {
        return nextName;
    }

    public void setNextName(String  nextName) {
        this.nextName = nextName;
    }

    public Boolean getHasLast() {
        return hasLast;
    }

    public void setHasLast(Boolean hasLast) {
        this.hasLast = hasLast;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    @Override
    public String toString() {
        return "ArticleView{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isOpen=" + isOpen +
                ", readTimes=" + readTimes +
                ", viewerNums=" + viewerNums +
                ", isReward=" + isReward +
                ", categoryId=" + categoryId +
                ", lastId=" + lastId +
                ", lastName=" + lastName +
                ", nextId=" + nextId +
                ", nextName=" + nextName +
                ", hasLast=" + hasLast +
                ", hasNext=" + hasNext +
                '}';
    }
}
