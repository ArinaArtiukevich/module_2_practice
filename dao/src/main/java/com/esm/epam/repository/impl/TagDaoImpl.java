package com.esm.epam.repository.impl;

import com.esm.epam.entity.Tag;
import com.esm.epam.mapper.TagMapper;
import com.esm.epam.repository.TagDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import static com.esm.epam.util.ParameterAttribute.TAG_ID;

@Repository
public class TagDaoImpl implements TagDao {
    private final String GET_ALL_TAGS_QUERY = "SELECT * FROM tags";
    private final String GET_TAG_BY_ID_QUERY = "SELECT * FROM tags WHERE tags.id = ?";
    private final String ADD_TAG_QUERY = "INSERT INTO tags(name) VALUES (?)";
    private final String UPDATE_TAG_QUERY = "UPDATE tags SET tags.name = ? WHERE tags.id=?";
    private final String DELETE_TAG_BY_ID_QUERY = "DELETE FROM tags WHERE tags.id = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> getAllTags() {
        return jdbcTemplate.query(GET_ALL_TAGS_QUERY, new TagMapper());
    }

    @Override
    public Long addTag(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(ADD_TAG_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);

        return (long) keyHolder.getKeys().get(TAG_ID);

    }

    @Override
    public Tag getTagById(Long id) {
        return jdbcTemplate.queryForObject(GET_TAG_BY_ID_QUERY, new TagMapper(), id);
    }

    @Override
    public void deleteTagById(Long id) {
        jdbcTemplate.update(DELETE_TAG_BY_ID_QUERY, id);
    }

    @Override
    public void updateTag(Tag tag) {
        jdbcTemplate.update(UPDATE_TAG_QUERY, tag.getName(), tag.getId());
    }
}
