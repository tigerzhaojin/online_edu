package com.tz.eduservice.controller;


import com.tz.commonutils.R;
import com.tz.eduservice.entity.EduSubject;
import com.tz.eduservice.entity.vo.SubjectTree;
import com.tz.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Zhao jin
 * @since 2022-05-09
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    EduSubjectService subjectService;
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){
        subjectService.saveSubjectByFile(file,subjectService);
        return R.ok().message("数据导入成功");
    }

    @GetMapping("/getAllSubject")
    public R getAllSubject() {
        List<EduSubject> eduSubjects = subjectService.getAllSubject();
        return R.ok().data("rows",eduSubjects);
    }

    @GetMapping("/getSubject/{parentId}")
    public R getSubjectct(@PathVariable("parentId") String parentId){
        List<EduSubject> subjects =subjectService.getSubjectByParentId(parentId);
        return R.ok().data("rows",subjects);
    }
}

