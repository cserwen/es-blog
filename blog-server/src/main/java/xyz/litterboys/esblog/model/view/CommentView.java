package xyz.litterboys.esblog.model.view;

import java.sql.Timestamp;

public class CommentView {
    
    private Integer id;
    private String comment;
    private Integer articleId;
    private Integer parentId;
    private Integer replyId;
    private String replyContent;

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    private Timestamp createTime;
    private Integer commentUserId;
    private String username;
    private String site;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(Integer commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String toString() {
        return "CommentView{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", articleId=" + articleId +
                ", parentId=" + parentId +
                ", replyId=" + replyId +
                ", replyContent=" + replyContent +
                ", createTime=" + createTime +
                ", commentUserId=" + commentUserId +
                ", username='" + username + '\'' +
                ", site='" + site + '\'' +
                '}';
    }
}
