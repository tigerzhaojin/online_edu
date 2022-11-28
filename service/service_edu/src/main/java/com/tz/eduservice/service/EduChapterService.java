package com.tz.eduservice.service;

import com.tz.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tz.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Zhao jin
 * @since 2022-05-17
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    Boolean deleteCharpter(String charpterId);

    void removeByCourseId(String id);

}
