package com.esm.epam.repository.impl;


import com.esm.epam.config.HsqlConfiguration;
import com.esm.epam.entity.Certificate;
import com.esm.epam.entity.Tag;
import com.esm.epam.repository.QueryBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CertificateDaoImplTest {

    private HsqlConfiguration hsqlConfiguration = new HsqlConfiguration();
    private QueryBuilder<Certificate> queryBuilder = new CertificateQueryBuilderImpl();
    private CertificateDaoImpl certificateDao = new CertificateDaoImpl(hsqlConfiguration.hsqlDataSource(), queryBuilder);

    private Tag tag_winter = new Tag(1L, "tag_winter");
    private Tag tag_relax = new Tag(2L, "tag_relax");
    private Tag tag_snow = new Tag(3L, "tag_snow");
    private List<Certificate> certificates = new ArrayList<>(Arrays.asList(
            new Certificate(1L, "skiing", "skiing school", 1000, 12, "2022-01-24T15:35:04.072", Arrays.asList(tag_winter, tag_snow)),
            new Certificate(2L, "massage", "massage center", 100, 2, "2022-01-24T15:36:18.987", Arrays.asList(tag_winter, tag_relax))
    ));

    @Test
    void testGetAll() {
        List<Certificate> actualCertificates = certificateDao.getAll();
        assertEquals(certificates, actualCertificates);
    }

    @Test
    void testGetFilteredList_findByPartDescription() {
        String partName = "ss";
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("name", partName);
        List<Certificate> expectedCertificates = Arrays.asList(certificates.get(1));
        List<Certificate> actualCertificates = certificateDao.getFilteredList(params);
        assertEquals(expectedCertificates, actualCertificates);

    }

    @Test
    void testGetFilteredList_getByTag() {
        String tagParameter = tag_relax.getName();
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("tag", tagParameter);

        List<Certificate> expectedCertificates = Arrays.asList(certificates.get(1));
        List<Certificate> actualCertificates = certificateDao.getFilteredList(params);
        assertEquals(expectedCertificates, actualCertificates);
    }

    @Test
    void testGetFilteredList_sortByNameAsc() {
        String sortParameter = "name_asc";
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("sort", sortParameter);

        List<Certificate> expectedCertificates = Arrays.asList(certificates.get(1), certificates.get(0));
        List<Certificate> actualCertificates = certificateDao.getFilteredList(params);
        assertEquals(expectedCertificates, actualCertificates);
    }

    @Test
    void testUpdate() {
        Certificate certificateWithFieldsToBeUpdated =
                new Certificate("snowboarding", Arrays.asList(tag_relax));
        Certificate expectedCertificate = new Certificate(1L, "snowboarding", "skiing school", 1000, 12, "2022-01-24T15:35:04.072", Arrays.asList(tag_winter, tag_snow, tag_relax));
        certificateDao.update(certificateWithFieldsToBeUpdated, 1L);
        Certificate actualCertificate = certificateDao.getById(1L);
        assertEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void testGetById() {
        Certificate expectedCertificate = certificates.get(0);
        Certificate actualCertificate = certificateDao.getById(1L);
        assertEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void testDeleteById() {
        boolean isDeleted = certificateDao.deleteById(1L);
        certificates.remove(0);
        List<Certificate> actualCertificates = certificateDao.getAll();
        assertTrue(isDeleted);
        assertEquals(certificates, actualCertificates);
    }
}