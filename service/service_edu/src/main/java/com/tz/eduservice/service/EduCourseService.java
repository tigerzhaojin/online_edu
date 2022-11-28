package com.tz.eduservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tz.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tz.eduservice.entity.vo.CourseInfoVo;
import com.tz.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Zhao jin
 * @since 2022-05-17
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String id);

    IPage<EduCourse> getCourstListPage(Long current, Long size, EduCourse eduCourse);

    Boolean deleteCoures(String id);

}
