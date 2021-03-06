package com.esm.epam.repository.impl;

import com.esm.epam.entity.Certificate;
import com.esm.epam.repository.QueryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CertificateQueryBuilderImplTest {

    private QueryBuilder<Certificate> queryBuilder = new CertificateQueryBuilderImpl();

    @Test
    void getUpdateQuery() {
        Certificate certificateWithFieldsToBeUpdated =
                Certificate.builder()
                        .name("snowboarding")
                        .description("snowboarding school")
                        .build();
        String expectedQuery = "UPDATE gift_certificates SET name = 'snowboarding', description = 'snowboarding school' WHERE gift_certificates.id = 1";
        String actualQuery = queryBuilder.getUpdateQuery(certificateWithFieldsToBeUpdated, 1L);
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    void getFilteredList() {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("name", "sn");
        params.add("desc", "sc");
        params.add("tag", "tag_winter");
        params.add("sort", "name");
        params.add("direction", "asc");
        String expectedQuery = "SELECT * FROM gift_certificates \n" +
                "LEFT JOIN certificates_tags ON (gift_certificates.id=certificates_tags.certificate_id) \n" +
                "LEFT JOIN tags ON (tags.tag_id = certificates_tags.tag_id) WHERE  ( gift_certificates.name LIKE '%sn%' )  AND  ( gift_certificates.id IN ( SELECT certificates_tags.certificate_id FROM certificates_tags WHERE certificates_tags.tag_id = tag_winter ))  ORDER BY gift_certificates.name ASC";
        String actualQuery = queryBuilder.getFilteredList(params);
        assertEquals(expectedQuery, actualQuery);
    }
}