package com.esm.epam.repository.impl;

import com.esm.epam.entity.Tag;
import com.esm.epam.exception.DaoException;
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
import java.util.Map;
import java.util.Optional;

import static com.esm.epam.util.ParameterAttribute.*;

@Repository
public class TagDaoImpl implements CRDDao<Tag> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<List<Tag>> getAll() {
        return Optional.of(jdbcTemplate.query(GET_ALL_TAGS_QUERY, new TagMapper()));
    }

    @Override
    public Long add(Tag tag) throws DaoException {
        Optional<Long> idAddedObject = Optional.empty();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_TAG_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);

        Optional<Map<String, Object>> keys = Optional.ofNullable(keyHolder.getKeys());
        if (keys.isPresent()) {
            idAddedObject = Optional.of((long) keys.get().get(TAG_ID));
        }
        if (!idAddedObject.isPresent()) {
            throw new DaoException("Tag was not added");
        }
        return idAddedObject.get();
    }

    @Override
    public Optional<Tag> getById(Long id) throws DaoException {
        List<Tag> tags = jdbcTemplate.query(GET_TAG_BY_ID_QUERY, new TagMapper(), id);
        if (tags.isEmpty()){
            throw new DaoException("No tag by id = " + id);
        }
        return Optional.of(tags.get(0));
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
