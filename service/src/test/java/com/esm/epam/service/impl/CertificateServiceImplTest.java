package com.esm.epam.service.impl;

import com.esm.epam.entity.Certificate;
import com.esm.epam.exception.ResourceNotFoundException;
import com.esm.epam.exception.ServiceException;
import com.esm.epam.repository.impl.CertificateDaoImpl;
import com.esm.epam.util.CurrentDate;
import com.esm.epam.validator.impl.ServiceCertificateValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ContextConfiguration(locations = "classpath:testSpringDispetcher-servlet.xml")
@ExtendWith(SpringExtension.class)
class CertificateServiceImplTest {

    @Mock
    private CertificateDaoImpl certificateDao = Mockito.mock(CertificateDaoImpl.class);

    @Mock
    private ServiceCertificateValidatorImpl validator = Mockito.mock(ServiceCertificateValidatorImpl.class);

    @Mock
    private CurrentDate date = new CurrentDate();

    @InjectMocks
    private CertificateServiceImpl certificateService;

    private Long invalidId = -1L;
    private Long newId = 5L;
    private Certificate certificateWithFieldsToBeUpdated = Certificate.builder()
            .name("football")
            .description("playing football")
            .build();
    private Certificate newCertificate = Certificate.builder()
            .id(null)
            .name("tennis")
            .description("playing tennis")
            .price(204)
            .duration(30)
            .build();
    private Certificate certificate = Certificate.builder()
            .id(1L)
            .name("skiing")
            .description("skiing in alps")
            .price(200)
            .duration(100)
            .build();

    private List<Certificate> certificates = Arrays.asList(Certificate.builder()
                    .id(2L)
                    .name("snowboarding")
                    .description("snowboarding school")
                    .price(1440)
                    .duration(12)
                    .build(),
            Certificate.builder()
                    .id(3L)
                    .name("sneakers")
                    .description("clothing and presents")
                    .price(200)
                    .duration(1)
                    .build(),
            Certificate.builder()
                    .id(4L)
                    .name("hockey")
                    .description("sport")
                    .price(120)
                    .duration(62)
                    .build()
    );

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testUpdate_positive() throws ServiceException {
        Certificate expectedCertificate = Certificate.builder().id(1L)
                .name("football")
                .description("playing football")
                .price(200)
                .duration(100)
                .build();
        doAnswer(new Answer<Void>() {
            public Void answer(InvocationOnMock invocation) {
                certificate.setName("football");
                certificate.setDescription("playing football");
                return null;
            }
        }).when(certificateDao).update(certificateWithFieldsToBeUpdated, 1L);
        when(certificateDao.getById(1L)).thenReturn(certificate);
        when(certificateDao.getAll()).thenReturn(Arrays.asList(certificate));

        certificateService.update(certificateWithFieldsToBeUpdated, 1L);
        Certificate actualCertificate = certificateDao.getById(1L);

        assertEquals(expectedCertificate, actualCertificate);
    }

    @Test
    void testGetAll_positive() throws ResourceNotFoundException {
        when(certificateDao.getAll()).thenReturn(certificates);
        List<Certificate> actualCertificates = certificateService.getAll();
        assertEquals(certificates, actualCertificates);
    }


    @Test
    public void testAdd_positive() throws ServiceException {
        when(certificateDao.add(newCertificate)).thenReturn(newId);
        Long actualId = certificateService.add(newCertificate);
        assertEquals(newId, actualId);
    }


    @Test
    public void testGetById_positive() throws ResourceNotFoundException {
        when(certificateDao.getById(1L)).thenReturn(certificate);
        Certificate actualCertificate = certificateService.getById(1L);
        assertEquals(certificate, actualCertificate);
    }

    @Test
    public void testDeleteById_positive() throws ServiceException {
        when(certificateDao.deleteById(2L)).thenReturn(true);
        certificateService.deleteById(certificates.get(0).getId());
        Mockito.verify(certificateDao).deleteById(certificates.get(0).getId());

    }

    @Test
    public void testDeleteById_serviceException() {
        ServiceException serviceException = new ServiceException("Requested resource not found id = " + invalidId);
        when(certificateDao.deleteById(invalidId)).thenReturn(false);
        Boolean actualResult = certificateDao.deleteById(invalidId);
        ServiceException actual = null;
        try {
            certificateService.deleteById(invalidId);
        } catch (ServiceException e) {
            actual = e;
        }
        assertEquals(serviceException.getMessage(), actual.getMessage());
        assertEquals(false, actualResult);

    }

    @Test
    void testGetFilteredList_filterByPartName() throws ResourceNotFoundException, ServiceException {
        String partName = "ing";
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("name", partName);
        List<Certificate> list = new LinkedList<>(certificates);
        doAnswer(new Answer<List<Certificate>>() {
            public List<Certificate> answer(InvocationOnMock invocation) {
                list.removeIf(e -> !e.getName().contains(partName));
                return list;
            }
        }).when(certificateDao).getFilteredList(params);

        List<Certificate> actualCertificates = certificateService.getFilteredList(params);
        assertEquals(list, actualCertificates);
    }

}