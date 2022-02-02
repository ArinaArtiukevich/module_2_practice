package com.esm.epam.controller;

import com.esm.epam.entity.Certificate;
import com.esm.epam.exception.ControllerException;
import com.esm.epam.exception.DaoException;
import com.esm.epam.exception.ResourceNotFoundException;
import com.esm.epam.exception.ServiceException;
import com.esm.epam.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

import static com.esm.epam.validator.ControllerValidator.validateIntToBeUpdated;
import static com.esm.epam.validator.ControllerValidator.validateSortValues;


@RestController
@RequestMapping("/certificates")
@Validated
public class CertificateController {

    @Autowired
    public CRUDService<Certificate> certificateService;

    @GetMapping
    public ResponseEntity<List<Certificate>> getCertificateList(@RequestParam(required = false) MultiValueMap<String, Object> params) throws ResourceNotFoundException, ControllerException {
        List<Certificate> certificates = new ArrayList<>();
        if (params.size() == 0) {
            certificates = certificateService.getAll();
        } else {
            validateSortValues(params);
            certificates = certificateService.getFilteredList(params);
        }
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Certificate> getCertificate(@PathVariable("id") @Min(1L) Long id) throws ResourceNotFoundException, DaoException {
        Certificate certificate = certificateService.getById(id);
        return new ResponseEntity<>(certificate, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable("id") @Min(1L) Long id) throws ServiceException, ResourceNotFoundException {
        certificateService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addCertificate(@Valid @RequestBody Certificate certificate, BindingResult bindingResult) throws ServiceException, DaoException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(certificateService.add(certificate)).toUri().toString());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Certificate> updateCertificate(@PathVariable("id") @Min(1L) Long id, @RequestBody Certificate certificate) throws ServiceException, ControllerException, ResourceNotFoundException, DaoException {
        validateIntToBeUpdated(certificate.getDuration());
        validateIntToBeUpdated(certificate.getPrice());
        Certificate certificateUpdated = certificateService.update(certificate, id);
        return new ResponseEntity<>(certificateUpdated, HttpStatus.OK);
    }
}
