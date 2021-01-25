package xyz.litterboys.esblog.service;

import xyz.litterboys.esblog.model.view.ArchiveListView;
import xyz.litterboys.esblog.model.view.ArticleListView;

public interface ArchiveService {

    ArchiveListView getArchiveList(long next, long size);

}
