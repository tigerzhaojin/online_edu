package com.tz.eduservice.entity.chapter;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ChapterVo {
    private String id;
    private String title;
//    小节
    List<VideoVo> children;
}
