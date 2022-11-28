package com.tz.eduservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tz.commonutils.R;
import com.tz.eduservice.config.ServerConfig;
import com.tz.eduservice.entity.EduCourse;
import com.tz.eduservice.entity.vo.CourseInfoVo;
import com.tz.eduservice.entity.vo.CoursePublishVo;
import com.tz.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Zhao jin
 * @since 2022-05-17
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    EduCourseService courseService;

    @Autowired
    ServerConfig serverConfig;

    @ApiOperation(value = "Page Condition Course List")
    @PostMapping("/getCourseList/{current}/{size}")
    public R getCourseList(@PathVariable("current") Long current,
                           @PathVariable("size") Long size,
                           @RequestBody(required = false) EduCourse eduCourse){

        IPage<EduCourse> coursePage= courseService.getCourstListPage(current,size,eduCourse);
        return R.ok().data("total",coursePage.getTotal()).data("rows",coursePage.getRecords());
    }

    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id=courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    @GetMapping("/serverConfig")
    public String serverConfig(){
        return serverConfig.getUrl();
    }


    @GetMapping("/getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable("id") String id){
        EduCourse eduCourse = courseService.getById(id);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        return R.ok().data("eduCourse",courseInfoVo);
    }

    @PostMapping("/updateCourse")
    public R getCourseInfo(@RequestBody EduCourse eduCourse){
        courseService.updateById(eduCourse);
        return R.ok().data("eduCourse",eduCourse);
    }

    @GetMapping("/getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable("id") String id){
        CoursePublishVo coursePublishVo= courseService.getPublishCourseInfo(id);
        return R.ok().data("courseInfo",coursePublishVo);
    }

    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable("id") String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }

    @DeleteMapping("{id}")
    public R deleteCourse(@PathVariable ("id") String id){
        Boolean result = courseService.deleteCoures(id);
        if (result) {
            return R.ok();
        }else {
            return R.error();
        }
    }
}

