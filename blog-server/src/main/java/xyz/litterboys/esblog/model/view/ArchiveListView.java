package xyz.litterboys.esblog.model.view;

import java.util.List;

public class ArchiveListView {

    private List<ArchiveView> archiveViews;
    private long pages;
    private long current;

    public List<ArchiveView> getArchiveViews() {
        return archiveViews;
    }

    public void setArchiveViews(List<ArchiveView> archiveViews) {
        this.archiveViews = archiveViews;
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
        return "ArchiveListView{" +
                "archiveViews=" + archiveViews +
                ", pages=" + pages +
                ", current=" + current +
                '}';
    }
}
