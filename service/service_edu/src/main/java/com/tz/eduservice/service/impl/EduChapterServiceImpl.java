package com.tz.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tz.commonutils.ResultCode;
import com.tz.eduservice.entity.EduChapter;
import com.tz.eduservice.entity.EduVideo;
import com.tz.eduservice.entity.chapter.ChapterVo;
import com.tz.eduservice.entity.chapter.VideoVo;
import com.tz.eduservice.mapper.EduChapterMapper;
import com.tz.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tz.eduservice.service.EduVideoService;
import com.tz.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Zhao jin
 * @since 2022-05-17
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
//        1.根据课程Id查询出所有章节
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        List<EduChapter> chapters = this.baseMapper.selectList(chapterWrapper);
//        2.根据课程Id查询出所有小节
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        List<EduVideo> videos = videoService.list(videoWrapper);
//        3.遍历章节，加入到vo
        List<ChapterVo> finalList=new ArrayList<>();
        for (EduChapter chapter : chapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            //遍历小节，加入到章节
            List<VideoVo> videoVos=new ArrayList<>();
            for (EduVideo video : videos) {
                if (video.getChapterId().equals(chapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);
            finalList.add(chapterVo);
        }


        return finalList;
    }

    @Override
    public Boolean deleteCharpter(String charpterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",charpterId);
        int count = videoService.count(wrapper);
        if (count>0){
            throw new MyException(ResultCode.ERROR,"该章节下存在小节，不能删除");
        } else {
            int i = this.baseMapper.deleteById(charpterId);
            return i>0;

        }
    }

    @Override
    public void removeByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper =
                new QueryWrapper<EduChapter>().eq("course_id", id);
        this.baseMapper.delete(wrapper);
    }
}
