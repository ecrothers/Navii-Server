package com.navii.server.persistence.controller;

import com.navii.server.persistence.domain.Tag;
import com.navii.server.persistence.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by sjung on 10/11/15.
 */

@RestController
@RequestMapping(value = "/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * Gets 20 random tags from the data
     * @return      If tags exist, return list of tags and HTTP status 200
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getTags() {
        return new ResponseEntity<>(tagService.findTags(), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createTag(@RequestBody Tag tag) {
        int createdUserId = tagService.create(tag);

        if (createdUserId > 0) {
            return new ResponseEntity<>(createdUserId, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(createdUserId, HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAll() {
        return new ResponseEntity<>(tagService.deleteAll(), HttpStatus.OK);
    }

}
