package com.esm.epam.controller;

import com.esm.epam.entity.Tag;
import com.esm.epam.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/tags")
public class TagController {

    @Autowired
    public TagService tagService;

    @GetMapping
    public ResponseEntity<List<Tag>> getTagList() {
        List<Tag> tags = tagService.getAllTags();
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Tag> getTag(@PathVariable("id") Long id) {
        Tag tag = tagService.getTagById(id);
        return new ResponseEntity<>(tag, HttpStatus.OK);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") Long id) {
        tagService.deleteTagById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    // todo redirect to  get request
    @PostMapping
    public ResponseEntity<String> addTag(@RequestBody Tag tag) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tagService.addTag(tag)).toUri().toString());

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
        // return "redirect:/tags";

    }

}