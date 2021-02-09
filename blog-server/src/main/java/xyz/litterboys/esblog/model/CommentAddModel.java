package xyz.litterboys.esblog.model;

public class CommentAddModel {

    private String comment;
    private Integer articleId;
    private Integer replyId;
    private String username;
    private String site;
    private String email;

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

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CommentAddModel{" +
                "comment='" + comment + '\'' +
                ", articleId=" + articleId +
                ", replyId=" + replyId +
                ", username='" + username + '\'' +
                ", site='" + site + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}