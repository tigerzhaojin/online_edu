package com.tz.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tz.eduservice.entity.EduSubject;
import com.tz.eduservice.entity.excel.SubjectData;
import com.tz.eduservice.service.EduSubjectService;
import com.tz.servicebase.exceptionhandler.MyException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
  /**  SubjectExcelListener不能交给Spring管理，需要自己手动new，不能注入其他对象，无法实现数据库操作，
     因此在new这个对象的时候，要将subjectService作为有参构造，这样就可以使用了
   */
    public EduSubjectService subjectService;


    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }
    public SubjectExcelListener() {}


    //    逐行读取excel内容，相当于循环
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData==null){
            throw new MyException(20001,"文件为空");
        }
//        先查询是否存在一级分类
        EduSubject existOneSubject = this.existOneSubject(subjectService,subjectData.getOneSubjectName());
        if (existOneSubject==null){ //没有该数据，可以插入
            existOneSubject=new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }
//        先查询是否存在二级分类
        String pid=existOneSubject.getId(); //获取一级分类id值
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if (existTwoSubject==null){
            existTwoSubject =new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

//    判断是否存在一级分类
    private EduSubject existOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject> wrapper= new QueryWrapper<>();
        wrapper.eq("title",name).eq("parent_id","0");
        return subjectService.getOne(wrapper);
    }

    //    判断是否存在二级分类
    private EduSubject existTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject> wrapper= new QueryWrapper<>();
        wrapper.eq("title",name).eq("parent_id",pid);
        return subjectService.getOne(wrapper);
    }
}
