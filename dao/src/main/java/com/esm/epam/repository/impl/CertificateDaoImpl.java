package com.esm.epam.repository.impl;

import com.esm.epam.entity.Certificate;
import com.esm.epam.entity.Tag;
import com.esm.epam.exception.DaoException;
import com.esm.epam.extractor.CertificateExtractor;
import com.esm.epam.mapper.TagMapper;
import com.esm.epam.repository.CRUDDao;
import com.esm.epam.repository.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

import static com.esm.epam.util.ParameterAttribute.*;

@Repository
public class CertificateDaoImpl implements CRUDDao<Certificate> {

    private QueryBuilder<Certificate> queryBuilder;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateDaoImpl(@Qualifier("dataSource") DataSource dataSource, QueryBuilder<Certificate> queryBuilder) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.queryBuilder = queryBuilder;
    }

    @Override
    public Optional<List<Certificate>> getAll() {
        return Optional.ofNullable(jdbcTemplate.query(GET_ALL_CERTIFICATES_QUERY, new CertificateExtractor()));
    }

    @Override
    public Optional<List<Certificate>> getFilteredList(MultiValueMap<String, Object> params) {
        if (params.containsKey(TAG)) {
            params.replace(TAG, Collections.singletonList(jdbcTemplate.queryForObject(GET_TAG_BY_NAME_QUERY, new TagMapper(), params.get(TAG).get(0)).getId()));
        }
        String filteredListQuery = queryBuilder.getFilteredList(params);
        return Optional.ofNullable(jdbcTemplate.query(filteredListQuery, new CertificateExtractor()));
    }

    @Override
    public Certificate update(Certificate certificate, Long idCertificate) throws DaoException {
        String updateCertificateQuery = queryBuilder.getUpdateQuery(certificate, idCertificate);
        jdbcTemplate.update(updateCertificateQuery);

        List<Tag> tags = certificate.getTags();
        updateCertificateTags(idCertificate, tags);
        Optional<Certificate> updatedCertificate = getById(idCertificate);
        if (!updatedCertificate.isPresent()) {
            throw new DaoException("Can not find updated certificate");
        }
        return updatedCertificate.get();
    }

    @Override
    public Long add(Certificate certificate) throws DaoException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Optional<Long> idAddedObject = Optional.empty();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_CERTIFICATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, certificate.getName());
            ps.setString(2, certificate.getDescription());
            ps.setLong(3, certificate.getDuration());
            ps.setString(4, certificate.getCreateDate());
            ps.setInt(5, certificate.getPrice());
            return ps;
        }, keyHolder);

        Optional<Map<String, Object>> keys = Optional.ofNullable(keyHolder.getKeys());
        if (keys.isPresent()) {
            idAddedObject = Optional.of((long) keys.get().get(CERTIFICATE_ID));
        } else {
            throw new DaoException("Certificate was not added");
        }
        Long idCertificate = idAddedObject.get();
        List<Tag> tags = certificate.getTags();
        updateCertificateTags(idCertificate, tags);
        return idCertificate;
    }

    @Override
    public Optional<Certificate> getById(Long id) throws DaoException {
        Optional<List<Certificate>> certificates = Optional.ofNullable(jdbcTemplate.query(GET_CERTIFICATE_BY_ID_QUERY, new CertificateExtractor(), id));
        if (!certificates.isPresent()) {
            throw new DaoException("Certificate was not found");
        }
        if (certificates.get().isEmpty()) {
            throw new DaoException("Certificate was not found");
        }
        return Optional.of(certificates.get().get(0));
    }

    @Override
    public boolean deleteById(Long id) {
        boolean isDeleted = false;
        jdbcTemplate.update(DELETE_CERTIFICATE_BY_ID_CERTIFICATES_TAGS_QUERY, id);
        int affectedRows = jdbcTemplate.update(DELETE_CERTIFICATE_BY_ID_QUERY, id);
        if (affectedRows > 0) {
            isDeleted = true;
        }
        return isDeleted;
    }

    private void updateCertificateTags(long certificate_id, List<Tag> tags) throws DaoException {
        if (tags != null) {
            if (tags.size() != 0) {
                List<Long> idsAddedTag = getTagsId(tags);
                addCertificateTags(certificate_id, idsAddedTag);
            }
        }
    }

    private void addCertificateTags(long certificate_id, List<Long> idsAddedTag) {
        for (Long idTag : idsAddedTag) {
            jdbcTemplate.update(ADD_CERTIFICATE_TAG_QUERY, certificate_id, idTag);
        }
    }

    private List<Long> getTagsId(List<Tag> tags) throws DaoException {
        List<Long> idsTag = new ArrayList<>();

        List<Tag> tagsDB = jdbcTemplate.query(GET_ALL_TAGS_QUERY, new TagMapper());
        List<String> tagsNameDB = tagsDB.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());

        for (Tag tag : tags) {
            if (!tagsNameDB.contains(tag.getName())) {
                jdbcTemplate.update(ADD_TAG_QUERY, tag.getName());
            }
            Optional<Tag> requiredTag = Optional.ofNullable(jdbcTemplate.queryForObject(GET_TAG_BY_NAME_QUERY, new TagMapper(), tag.getName()));
            if (!requiredTag.isPresent()) {
                throw new DaoException("No required tags to update certificates");
            }
            idsTag.add(requiredTag.get().getId());
        }
        return idsTag;
    }

}
