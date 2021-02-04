package xyz.litterboys.esblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import xyz.litterboys.esblog.dao.ArticleMapper;
import xyz.litterboys.esblog.model.Article;
import xyz.litterboys.esblog.model.view.ArchiveListView;
import xyz.litterboys.esblog.model.view.ArchiveView;
import xyz.litterboys.esblog.service.ArchiveService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArchiveServiceImpl implements ArchiveService {

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public ArchiveListView getArchiveList(long next, long size) {
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("is_deleted", false).eq("is_open", true).orderByDesc("create_time");
        Page<Article> articlePage = new Page<>(next, size);
        IPage<Article> articleIPage = articleMapper.selectPage(articlePage, articleQueryWrapper);

        List<ArchiveView> archiveViews = new ArrayList<>();
        articleIPage.getRecords().forEach(article -> {
            ArchiveView archiveView = new ArchiveView();
            BeanUtils.copyProperties(article, archiveView);
            archiveViews.add(archiveView);
        });

        ArchiveListView archiveListView = new ArchiveListView();
        archiveListView.setPages(articleIPage.getPages());
        archiveListView.setArchiveViews(archiveViews);
        archiveListView.setCurrent(articleIPage.getCurrent());
        return archiveListView;
    }
}
