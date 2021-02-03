package xyz.litterboys.esblog.controller;

import org.springframework.web.bind.annotation.*;
import xyz.litterboys.esblog.service.LinkService;

import javax.annotation.Resource;

@RestController()
@RequestMapping("link")
public class LinkController {

    @Resource
    private LinkService linkService;

    @GetMapping("list")
    public Object getLinkList(){
        return linkService.getLinkList();
    }
}
