package com.tz.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tz.commonutils.R;
import com.tz.eduservice.entity.EduTeacher;
import com.tz.eduservice.entity.vo.TeacherQuery;
import com.tz.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Zhao jin
 * @since 2022-05-05
 */
@Api("Teacher Manager")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
    @Autowired
    EduTeacherService eduTeacherService;


    @ApiOperation(value = "All Teacher list")
    @GetMapping("findAll")
    public R findAll(){
        List<EduTeacher> teachers = eduTeacherService.list(null);
        return R.ok().data("items",teachers);
    }

    @ApiOperation(value = "Delete Teacher by ID")
    @DeleteMapping("{id}")
    public R deleTeacher( @ApiParam(name = "id",value = "Teacher ID",required = true)
                                    @PathVariable("id") String id){
        boolean flag = eduTeacherService.removeById(id);
        if (flag){
            return R.ok();
        } else {
            return R.error();
        }
    }

//    分页返回教师列表
    @ApiOperation(value = "Page Teacher List")
    @GetMapping("pageTeacher/{current}/{size}")
    public R pageTeacherList(@PathVariable("current") Long current,
                             @PathVariable("size") Long size){
        Page<EduTeacher> page =new Page<>(current,size);
        eduTeacherService.page(page,null);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        Map map =new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);
    }

    @ApiOperation(value = "Page Condition Teacher List")
    @PostMapping("pageTeacherCondtion/{current}/{size}")
    public R pageTeacherCondtion(@PathVariable("current") Long current,
                                 @PathVariable("size") Long size,
                                 @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> teacherPage=new Page<>(current,size);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        eduTeacherService.page(teacherPage, wrapper);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }
}

