package ru.bazzar.organization.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bazzar.organization.entities.Logo;
import ru.bazzar.organization.services.OrganizationService;

import java.io.ByteArrayInputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/logo")
public class LogoController {
    private final OrganizationService organizationService;

    @GetMapping("/{title}")
    public ResponseEntity<?> getLogoByTitleOrganization(@PathVariable String title) {
        Logo logo = organizationService.findLogoByTitleOrganization(title);
        return ResponseEntity.ok()
                .header("fileName", logo.getOriginalFileName())
                .contentType(MediaType.valueOf(logo.getContentType()))
                .contentLength(logo.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(logo.getBytes())));
    }
}
