package com.navii.server.persistence.service;

import com.navii.server.persistence.dao.TagDAO;
import com.navii.server.persistence.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by stevejung on 2015-11-17.
 */
public interface TagService {


    List<Tag> findTags();

}
