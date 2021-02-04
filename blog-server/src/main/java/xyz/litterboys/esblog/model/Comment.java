package xyz.litterboys.esblog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Timestamp;

@TableName("comment")
public class Comment {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String comment;
    private Integer articleId;
    private Integer parentId;
    private Integer replyId;
    private Timestamp createTime;
    private boolean isDeleted;
    private Integer commentUserId;

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

    public Integer getCommentUserId() {
        return commentUserId;
    }
    
    public void setCommentUserId(Integer commentUserId) {
        this.commentUserId = commentUserId;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Comment [articleId=" + articleId + ", comment=" + comment + ", commentUserId=" + commentUserId
                + ", createTime=" + createTime + ", id=" + id + ", isDeleted=" + isDeleted + ", parentId=" + parentId
                + ", replyId=" + replyId + "]";
    }

}
