package com.esm.epam.repository.impl;

import com.esm.epam.repository.QueryBuilder;
import com.esm.epam.entity.Certificate;
import com.esm.epam.entity.Tag;
import com.esm.epam.extractor.CertificateExtractor;
import com.esm.epam.mapper.TagMapper;
import com.esm.epam.repository.CRUDDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.esm.epam.util.ParameterAttribute.*;
import static java.util.Objects.*;

@Repository
public class CertificateDaoImpl implements CRUDDao<Certificate> {

    @Autowired
    @Qualifier("certificateQueryBuilder")
    private QueryBuilder<Certificate> queryBuilder;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CertificateDaoImpl(@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Certificate> getAll() {
        return jdbcTemplate.query(GET_ALL_CERTIFICATES_QUERY, new CertificateExtractor());
    }

    @Override
    public List<Certificate> getFilteredList(MultiValueMap<String, Object> params) {
        if (params.keySet().contains(TAG)) {
            params.replace(TAG, Collections.singletonList(jdbcTemplate.queryForObject(GET_TAG_BY_NAME_QUERY, new TagMapper(), params.get(TAG).get(0)).getId()));
        }
        String filteredListQuery = queryBuilder.getFilteredList(params);
        return jdbcTemplate.query(filteredListQuery, new CertificateExtractor());
    }

    @Override
    public void update(Certificate certificate, Long idCertificate) {
        String updateCertificateQuery = queryBuilder.getUpdateQuery(certificate, idCertificate);
        jdbcTemplate.update(updateCertificateQuery);

        List<Tag> tags = certificate.getTags();
        updateCertificateTags(idCertificate, tags);
    }

    @Override
    public Long add(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        final long certificate_id;
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ADD_CERTIFICATE_QUERY, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, certificate.getName());
            ps.setString(2, certificate.getDescription());
            ps.setLong(3, certificate.getDuration());
            ps.setString(4, certificate.getCreateDate());
            ps.setInt(5, certificate.getPrice());
            return ps;
        }, keyHolder);
        certificate_id = (long) keyHolder.getKeys().get(CERTIFICATE_ID);
        List<Tag> tags = certificate.getTags();
        updateCertificateTags(certificate_id, tags);
        return certificate_id;
    }

    @Override
    public Certificate getById(Long id) {
        List<Certificate> certificates = jdbcTemplate.query(GET_CERTIFICATE_BY_ID_QUERY, new CertificateExtractor(), id);
        Certificate certificate = null;
        if (nonNull(certificates)) {
            if (!certificates.isEmpty()) {
                certificate = certificates.get(0);
            }
        }
        return certificate;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean isDeleted = false;
        int affectedRows = jdbcTemplate.update(DELETE_CERTIFICATE_BY_ID_QUERY, id, id);
        if (affectedRows > 0) {
            isDeleted = true;
        }
        return isDeleted;
    }

    private void updateCertificateTags(long certificate_id, List<Tag> tags) {
        if (nonNull(tags)) {
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

    private List<Long> getTagsId(List<Tag> tags) {
        List<Long> idsTag = new ArrayList<>();

        List<Tag> tagsDB = jdbcTemplate.query(GET_ALL_TAGS_QUERY, new TagMapper());
        List<String> tagsNameDB = tagsDB.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());

        for (Tag tag : tags) {
            if (!tagsNameDB.contains(tag.getName())) {
                jdbcTemplate.update(ADD_TAG_QUERY, tag.getName());
            }
            idsTag.add(jdbcTemplate.queryForObject(GET_TAG_BY_NAME_QUERY, new TagMapper(), tag.getName()).getId());
        }
        return idsTag;
    }

}
