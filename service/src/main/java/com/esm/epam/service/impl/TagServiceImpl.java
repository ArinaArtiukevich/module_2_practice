package com.esm.epam.service.impl;

import com.esm.epam.entity.Tag;
import com.esm.epam.repository.CRDDao;
import com.esm.epam.service.CRDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements CRDService<Tag> {

    @Autowired
    private CRDDao<Tag> tagDao;

    @Override
    public List<Tag> getAll() {
       return tagDao.getAll();
    }

    @Override
    public Long add(Tag tag) {
        return  tagDao.add(tag);
    }

    @Override
    public Tag getById(Long id) {
       return tagDao.getById(id);
    }

    @Override
    public boolean deleteById(Long id) {
         return tagDao.deleteById(id);
    }

}
