package com.esm.epam.controller;

import com.esm.epam.entity.Certificate;
import com.esm.epam.entity.Tag;
import com.esm.epam.exception.ControllerException;
import com.esm.epam.exception.ResourceNotFoundException;
import com.esm.epam.exception.ServiceException;
import com.esm.epam.service.CRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.esm.epam.validator.Validator.*;
import static java.util.Objects.isNull;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    @Autowired
    public CRUDService<Certificate> certificateService;

    @GetMapping
    public ResponseEntity<List<Certificate>> getCertificateList(@RequestParam(required = false) MultiValueMap<String, Object> params) throws ServiceException, ResourceNotFoundException {
        List<Certificate> certificates = new ArrayList<>();
        if (params.size() == 0) {
            certificates = certificateService.getAll();
        } else {
            certificates = certificateService.getFilteredList(params);
        }
        return new ResponseEntity<>(certificates, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Certificate> getCertificate(@PathVariable("id") Long id) throws ResourceNotFoundException, ControllerException {
        validateId(id);
        Certificate certificate = certificateService.getById(id);
        return new ResponseEntity<>(certificate, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable("id") Long id) throws ServiceException, ControllerException {
        validateId(id);
        certificateService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addCertificate(@RequestBody Certificate certificate) throws ServiceException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(certificateService.add(certificate)).toUri().toString());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateCertificate(@PathVariable("id") Long id, @RequestBody Certificate certificate) throws ServiceException, ControllerException {
        validateId(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        certificateService.update(certificate, id);
        httpHeaders.add("Location", ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(id).toUri().toString());

        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }
}
