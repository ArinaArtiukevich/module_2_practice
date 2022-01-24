package com.esm.epam.service.impl;

import com.esm.epam.entity.Certificate;
import com.esm.epam.exception.ServiceException;
import com.esm.epam.repository.CRUDDao;
import com.esm.epam.service.CRUDService;
import com.esm.epam.validator.ServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.esm.epam.util.ParameterAttribute.DATE_PATTERN;
import static java.util.Objects.nonNull;

@Service
public class CertificateServiceImpl implements CRUDService<Certificate> {

    @Autowired
    private CRUDDao<Certificate> certificateDao;
    @Autowired
    private ServiceValidator<Certificate> validator;

    @Override
    public boolean update(Certificate certificate, Long idCertificate) throws ServiceException {
        boolean isExist = true;
        List <Certificate> certificates = certificateDao.getAll();
        List<Long> certificatesId = certificates.stream()
                .map(Certificate::getId)
                .collect(Collectors.toList());
        if(certificatesId.contains(idCertificate)) {
            validateIntToBeUpdated(certificate.getDuration());
            validateIntToBeUpdated(certificate.getPrice());
            certificate.setLastUpdateDate(getCurrentDate());
            certificateDao.update(certificate, idCertificate);
        } else {
            isExist = false;
        }
        return isExist;
    }

    @Override
    public List<Certificate> getAll() {
        return certificateDao.getAll();
    }

    @Override
    public Long add(Certificate certificate) throws ServiceException {
        validator.validate(certificate);
        certificate.setCreateDate(getCurrentDate());
        return certificateDao.add(certificate);
    }

    @Override
    public Certificate getById(Long id) {
        return certificateDao.getById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return certificateDao.deleteById(id);
    }

    @Override
    public List<Certificate> getFilteredList(MultiValueMap<String, Object> params) {
        return certificateDao.getFilteredList(params);
    }

    private String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }


    private void validateIntToBeUpdated(Integer value) throws ServiceException {
        if (nonNull(value)) {
            if (value <= 0){
                throw new ServiceException("Invalid integer value");
            }
        }
    }
}
