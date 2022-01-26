package com.esm.epam.repository.impl;

import com.esm.epam.entity.Certificate;
import com.esm.epam.entity.Tag;
import com.esm.epam.extractor.CertificateExtractor;
import com.esm.epam.mapper.TagMapper;
import com.esm.epam.repository.CRDDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import static com.esm.epam.util.ParameterAttribute.*;
import static java.util.Objects.nonNull;

@Repository
public class TagDaoImpl implements CRDDao<Tag> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Tag> getAll() {
        return jdbcTemplate.query(GET_ALL_TAGS_QUERY, new TagMapper());
    }

    @Override
    public Long add(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_TAG_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);

        return (long) keyHolder.getKeys().get(TAG_ID);
    }

    @Override
    public Tag getById(Long id) {
        List<Tag> tags = jdbcTemplate.query(GET_TAG_BY_ID_QUERY, new TagMapper(), id);
        Tag tag = null;
        if (!tags.isEmpty()) {
            tag = tags.get(0);
        }
        return tag;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean isDeleted = false;
        jdbcTemplate.update(DELETE_TAG_BY_ID_CERTIFICATES_TAG_QUERY, id);
        int affectedRows = jdbcTemplate.update(DELETE_TAG_BY_ID_QUERY, id);
        if (affectedRows > 0) {
            isDeleted = true;
        }
        return isDeleted;

    }

}
