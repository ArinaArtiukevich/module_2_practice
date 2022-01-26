package com.esm.epam.service.impl;

import com.esm.epam.entity.Certificate;
import com.esm.epam.entity.Tag;
import com.esm.epam.exception.ResourceNotFoundException;
import com.esm.epam.exception.ServiceException;
import com.esm.epam.repository.CRDDao;
import com.esm.epam.service.CRDService;
import com.esm.epam.validator.ServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements CRDService<Tag> {

    @Autowired
    private CRDDao<Tag> tagDao;
    @Autowired
    private ServiceValidator<Tag> validator;

    @Override
    public List<Tag> getAll() throws ResourceNotFoundException {
        List<Tag> tags = tagDao.getAll();
        validator.validateList(tags);
        return tags;
    }

    @Override
    public Long add(Tag tag) throws ServiceException {
        validator.validateEntityParameters(tag);
        return tagDao.add(tag);
    }

    @Override
    public Tag getById(Long id) throws ResourceNotFoundException {
        Tag tag = tagDao.getById(id);
        validator.validateEntity(tag, id);
        return tag;
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        if (!tagDao.deleteById(id)) {
            throw new ServiceException("Requested resource not found id = " + id);
        }
    }
}
