package com.tz.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tz.eduservice.entity.EduVideo;
import com.tz.eduservice.mapper.EduVideoMapper;
import com.tz.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Zhao jin
 * @since 2022-05-17
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Override
    public List<EduVideo> getByChapterId(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        List<EduVideo> list = this.list(wrapper);
        return list;
    }

    @Override
    public void removeByCourseId(String id) {
        QueryWrapper<EduVideo> wrapper =
                new QueryWrapper<EduVideo>().eq("course_id", id);
        this.baseMapper.delete(wrapper);
    }
}
