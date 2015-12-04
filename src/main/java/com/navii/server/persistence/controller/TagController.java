package com.navii.server.persistence.controller;

import com.navii.server.persistence.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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


    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody List<String> getTags() {
        return tagService.findTags();
    }

}
