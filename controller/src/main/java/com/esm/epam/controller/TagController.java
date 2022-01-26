package com.esm.epam.controller;

import com.esm.epam.entity.Certificate;
import com.esm.epam.entity.Tag;
import com.esm.epam.exception.ControllerException;
import com.esm.epam.exception.ResourceNotFoundException;
import com.esm.epam.exception.ServiceException;
import com.esm.epam.repository.CRDDao;
import com.esm.epam.service.CRDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static com.esm.epam.validator.Validator.*;
import static java.util.Objects.isNull;

@RestController
@RequestMapping(value = "/tags")
public class TagController {

    @Autowired
    public CRDService<Tag> tagService;

    @GetMapping
    public ResponseEntity<List<Tag>> getTagList() throws ResourceNotFoundException {
        List<Tag> tags = tagService.getAll();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable("id") Long id) throws ResourceNotFoundException, ControllerException {
        validateId(id);
        Tag tag = tagService.getById(id);
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") Long id) throws ServiceException, ControllerException {
        validateId(id);
        tagService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<String> addTag(@RequestBody Tag tag) throws ServiceException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tagService.add(tag)).toUri().toString());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);

    }

}