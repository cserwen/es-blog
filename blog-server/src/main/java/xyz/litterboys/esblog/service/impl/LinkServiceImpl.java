package xyz.litterboys.esblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import xyz.litterboys.esblog.dao.LinkMapper;
import xyz.litterboys.esblog.model.Link;
import xyz.litterboys.esblog.service.LinkService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    @Resource
    private LinkMapper linkMapper;

    @Override
    public List<Link> getLinkList() {
        QueryWrapper<Link> queryWrapper = new QueryWrapper<>();
        return linkMapper.selectList(queryWrapper);
    }
}
