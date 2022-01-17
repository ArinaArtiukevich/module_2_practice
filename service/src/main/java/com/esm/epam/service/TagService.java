package com.esm.epam.service;

import com.esm.epam.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags();

    Long addTag(Tag tag);

    Tag getTagById(Long id);

    void deleteTagById(Long id);

    void updateTag(Tag tag);
}
