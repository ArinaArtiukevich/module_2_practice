package com.esm.epam.service.impl;

import com.esm.epam.entity.Certificate;
import com.esm.epam.exception.ResourceNotFoundException;
import com.esm.epam.exception.ServiceException;
import com.esm.epam.repository.CRUDDao;
import com.esm.epam.service.CRUDService;
import com.esm.epam.validator.CertificateValidator;
import com.esm.epam.validator.ServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.esm.epam.util.ParameterAttribute.*;
import static java.util.Objects.nonNull;

@Service
public class CertificateServiceImpl implements CRUDService<Certificate> {

    @Autowired
    private CRUDDao<Certificate> certificateDao;
    @Autowired
    private CertificateValidator validator;

    @Override
    public void update(Certificate certificate, Long idCertificate) throws ServiceException {
        List<Certificate> certificates = certificateDao.getAll();
        List<Long> certificatesId = certificates.stream()
                .map(Certificate::getId)
                .collect(Collectors.toList());
        if (!certificatesId.contains(idCertificate)) {
            throw new ServiceException("Requested resource not found id = " + idCertificate);
        }
        validator.validateIntToBeUpdated(certificate.getDuration());
        validator.validateIntToBeUpdated(certificate.getPrice());
        certificate.setLastUpdateDate(getCurrentDate());
        certificateDao.update(certificate, idCertificate);
    }

    @Override
    public List<Certificate> getAll() {
        List<Certificate> certificates = certificateDao.getAll();
        validator.validateList(certificates);
        return certificates;
    }

    @Override
    public Long add(Certificate certificate) throws ServiceException {
        validator.validateEntityParameters(certificate);
        certificate.setCreateDate(getCurrentDate());
        return certificateDao.add(certificate);
    }

    @Override
    public Certificate getById(Long id) {
        Certificate certificate = certificateDao.getById(id);
        validator.validateEntity(certificate, id);
        return certificate;
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        if (!certificateDao.deleteById(id)) {
            throw new ServiceException("Requested resource not found id = " + id);
        }

    }

    @Override
    public List<Certificate> getFilteredList(MultiValueMap<String, Object> params) throws ServiceException {
        validator.validateSortValues(params);
        List<Certificate> certificates = certificateDao.getFilteredList(params);
        validator.validateList(certificates);
        return certificates;
    }

    private String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

}
