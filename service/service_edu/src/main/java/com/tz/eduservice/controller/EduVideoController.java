package com.tz.eduservice.controller;


import com.tz.commonutils.R;
import com.tz.eduservice.entity.EduVideo;
import com.tz.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Zhao jin
 * @since 2022-05-17
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    EduVideoService videoService;

    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }
    @DeleteMapping("{videoId}")
    public R deleteById(@PathVariable("videoId") String videoId){
        videoService.removeById(videoId);
        return R.ok();
    }
    @GetMapping("{videoId}")
    public R getVideoById(@PathVariable("videoId") String videoId){
        return R.ok().data("video",videoService.getById(videoId));
    }
    @GetMapping("/chapterId/{chapterId}")
    public R getByChapterId(@PathVariable("chapterId") String chapterId ){
        List<EduVideo> videos= videoService.getByChapterId(chapterId);
        return R.ok().data("videos",videos);
    }
}

