package com.esm.epam.service.impl;

import com.esm.epam.entity.Tag;
import com.esm.epam.repository.TagDao;
import com.esm.epam.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public List<Tag> getAllTags() {
       return tagDao.getAllTags();
    }

    @Override
    public Long addTag(Tag tag) {
        return  tagDao.addTag(tag);
    }

    @Override
    public Tag getTagById(Long id) {
       return tagDao.getTagById(id);
    }

    @Override
    public void deleteTagById(Long id) {
        tagDao.deleteTagById(id);
    }

    @Override
    public void updateTag(Tag tag) {
        tagDao.updateTag(tag);
    }
}
