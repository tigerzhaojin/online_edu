package com.tz.eduservice.service;

import com.tz.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Zhao jin
 * @since 2022-05-17
 */
public interface EduVideoService extends IService<EduVideo> {

    List<EduVideo> getByChapterId(String chapterId);

    void removeByCourseId(String id);

}
