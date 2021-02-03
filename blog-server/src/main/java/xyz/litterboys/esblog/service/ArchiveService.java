package xyz.litterboys.esblog.service;

import xyz.litterboys.esblog.model.view.ArchiveListView;

public interface ArchiveService {

    ArchiveListView getArchiveList(long next, long size);

}
