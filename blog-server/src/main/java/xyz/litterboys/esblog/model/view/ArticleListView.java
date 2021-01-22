package xyz.litterboys.esblog.model.view;

import xyz.litterboys.esblog.model.ArticleCard;

import java.util.List;

public class ArticleListView {

    private List<ArticleCard> articleCards;
    private long pages;
    private long current;

    public List<ArticleCard> getArticleCards() {
        return articleCards;
    }

    public void setArticleCards(List<ArticleCard> articleCards) {
        this.articleCards = articleCards;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "ArticleListView{" +
                "articleCards=" + articleCards +
                ", pages=" + pages +
                ", current=" + current +
                '}';
    }
}
