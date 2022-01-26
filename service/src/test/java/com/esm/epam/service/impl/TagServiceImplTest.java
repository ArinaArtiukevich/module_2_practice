package com.esm.epam.service.impl;

import com.esm.epam.entity.Tag;
import com.esm.epam.exception.ResourceNotFoundException;
import com.esm.epam.exception.ServiceException;
import com.esm.epam.repository.impl.TagDaoImpl;
import com.esm.epam.validator.impl.ServiceTagValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(locations = "classpath:testSpringDispetcher-servlet.xml")
@ExtendWith(SpringExtension.class)
class TagServiceImplTest {
    @Mock
    private TagDaoImpl tagDao = Mockito.mock(TagDaoImpl.class);

    @Mock
    private ServiceTagValidatorImpl validator = Mockito.mock(ServiceTagValidatorImpl.class);

    @InjectMocks
    private TagServiceImpl tagService;

    private Long tagId = 1L;
    private Long invalidTagId = -1L;
    private Tag tag = new Tag(tagId, "tag_snow");
    private List<Tag> tags = Arrays.asList(
            new Tag(2L, "tag_outdoors"),
            new Tag(3L, "tag_indoors"),
            new Tag(4L, "tag_sport")
    );

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll_positive() throws ResourceNotFoundException {
        when(tagDao.getAll()).thenReturn(tags);
        List<Tag> actualTags = tagService.getAll();
        assertEquals(tags, actualTags);
    }

    @Test
    public void testAdd_positive() throws ServiceException {
        when(tagDao.add(tag)).thenReturn(tagId);
        Long actualId = tagService.add(tag);
        assertEquals(tagId, actualId);
    }

    @Test
    public void testGetById_positive() throws ResourceNotFoundException {
        when(tagDao.getById(1L)).thenReturn(tag);
        Tag actualTag = tagService.getById(1L);
        assertEquals(tag, actualTag);
    }

    @Test
    public void testDeleteById_positive() throws ServiceException {
        when(tagDao.deleteById(2L)).thenReturn(true);
        tagService.deleteById(tags.get(0).getId());
        Mockito.verify(tagDao).deleteById(tags.get(0).getId());
    }

    @Test
    public void testDeleteById_serviceException() {
        ServiceException serviceException = new ServiceException("Requested resource not found id = " + invalidTagId);
        when(tagDao.deleteById(invalidTagId)).thenReturn(false);
        Boolean actualResult = tagDao.deleteById(tagId);
        ServiceException actual = null;
        try {
            tagService.deleteById(invalidTagId);
        } catch (ServiceException e) {
            actual = e;
        }
        assertEquals(serviceException.getMessage(), actual.getMessage());
        assertEquals(false, actualResult);

    }
}