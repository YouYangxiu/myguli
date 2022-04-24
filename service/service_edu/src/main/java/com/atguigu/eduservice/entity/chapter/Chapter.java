package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Chapter {
    String id;
    String title;
    List<Video> children = new ArrayList<>();
}
