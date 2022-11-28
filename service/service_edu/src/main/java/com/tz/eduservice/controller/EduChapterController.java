package com.tz.eduservice.controller;


import com.tz.commonutils.R;
import com.tz.eduservice.entity.EduChapter;
import com.tz.eduservice.entity.chapter.ChapterVo;
import com.tz.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Zhao jin
 * @since 2022-05-17
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    EduChapterService chapterService;
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable("courseId") String courseId){
        List<ChapterVo> list= chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    @PostMapping("/addCharpter")
    public R addCharpter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return R.ok();
    }
    @GetMapping("{chapterId}")
    public R getCharpterById(@PathVariable("chapterId") String chapterId){
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("charpter",eduChapter);
    }
    @PostMapping("/updateCharpter")
    public R updateCharpter(@RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    @DeleteMapping("{charpterId}")
    public R deleteCharpter(@PathVariable("charpterId") String charpterId){

        Boolean flag= chapterService.deleteCharpter(charpterId);
        return R.ok();
    }


}

