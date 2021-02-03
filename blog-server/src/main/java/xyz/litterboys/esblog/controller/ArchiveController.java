package xyz.litterboys.esblog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.litterboys.esblog.service.ArchiveService;

import javax.annotation.Resource;

@RestController
@RequestMapping("archive")
public class ArchiveController {

    private final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Resource
    private ArchiveService archiveService;

    @GetMapping("list")
    private Object getArticleList(@RequestParam("next") long next, @RequestParam("size") long size){
        logger.info("#next={}, #size={}", next, size);
        return archiveService.getArchiveList(next, size);
    }
}
