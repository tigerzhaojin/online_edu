package com.tz.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tz.commonutils.ResultCode;
import com.tz.eduservice.entity.EduCourse;
import com.tz.eduservice.entity.EduCourseDescription;
import com.tz.eduservice.entity.vo.CourseInfoVo;
import com.tz.eduservice.entity.vo.CoursePublishVo;
import com.tz.eduservice.mapper.EduCourseMapper;
import com.tz.eduservice.service.EduChapterService;
import com.tz.eduservice.service.EduCourseDescriptionService;
import com.tz.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tz.eduservice.service.EduVideoService;
import com.tz.servicebase.exceptionhandler.MyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Zhao jin
 * @since 2022-05-17
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    EduCourseDescriptionService courseDescriptionService;

    @Autowired
    EduChapterService chapterService;

    @Autowired
    EduVideoService videoService;
//    添加课程信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
//        1.课程表里添加数据
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = this.baseMapper.insert(eduCourse);
        if (insert<=0){
            throw new MyException(ResultCode.ERROR,"课程添加失败");
        }
        String cid = eduCourse.getId();

//        2.课程简介表里添加数据
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
        return cid;
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = this.baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public IPage<EduCourse> getCourstListPage(Long current, Long size, EduCourse eduCourse) {
        Page<EduCourse> coursePage=new Page<>(current,size);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
//        String title=null;
//        if (eduCourse!=null){
//            title=eduCourse.getTitle();
//        }
        String title=eduCourse.getTitle();
        if (!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        IPage<EduCourse> page = this.page(coursePage, wrapper);
        return page;
    }

    @Override
    @Transactional
    public Boolean deleteCoures(String id) {
//        删除小节
        videoService.removeByCourseId(id);
//        删除章节
        chapterService.removeByCourseId(id);
//        删除描述
        courseDescriptionService.removeById(id);
//        Integer i=10/0;
//        删除课程
        int result = this.baseMapper.deleteById(id);
        if (result==0) {
            return false;
        } else {
            return true;
        }

    }
}
