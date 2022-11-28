package com.tz.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tz.eduservice.entity.EduSubject;
import com.tz.eduservice.entity.excel.SubjectData;
import com.tz.eduservice.entity.vo.SubjectTree;
import com.tz.eduservice.listener.SubjectExcelListener;
import com.tz.eduservice.mapper.EduSubjectMapper;
import com.tz.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Zhao jin
 * @since 2022-05-09
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
// 根据文件添加分类
    @Override
    public void saveSubjectByFile(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream stream = file.getInputStream();
            EasyExcel.read(stream, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<EduSubject> getAllSubject() {
        List<EduSubject> subjects = this.list(null);
        List<EduSubject> eduSubjects = subjects.stream().filter(eduSubject ->
            eduSubject.getParentId().equals("0")
        ).map(menu -> {
            menu.setChildren(getChildren(menu, subjects));
            return menu;
        }).collect(Collectors.toList());

        return eduSubjects;
    }

    @Override
    public List<EduSubject> getSubjectByParentId(String parentId) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",parentId);
        List<EduSubject> eduSubjects = this.baseMapper.selectList(wrapper);
        return eduSubjects;
    }

    private List<EduSubject> getChildren(EduSubject root, List<EduSubject> all) {
        List<EduSubject> children = all.stream().filter(eduSubject ->
                root.getId().equals(eduSubject.getParentId())
        ).map(eduSubject -> {
            eduSubject.setChildren(getChildren(eduSubject, all));
            return eduSubject;
        }).collect(Collectors.toList());
        return children;

    }


}
