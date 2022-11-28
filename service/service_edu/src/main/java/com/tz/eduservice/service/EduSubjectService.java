package com.tz.eduservice.service;

import com.tz.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tz.eduservice.entity.vo.SubjectTree;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Zhao jin
 * @since 2022-05-09
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubjectByFile(MultipartFile file, EduSubjectService subjectService);

    List<EduSubject> getAllSubject();

    List<EduSubject> getSubjectByParentId(String parentId);

}
