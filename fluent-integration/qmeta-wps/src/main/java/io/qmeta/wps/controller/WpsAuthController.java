package io.qmeta.wps.controller;

import io.qmeta.wps.auth.WpsAuthService;
import io.qmeta.wps.common.WpsApiKeyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class WpsAuthController {
    @Autowired
    WpsApiKeyHolder apiKeyHolder;
    @Autowired
    WpsAuthService service;

    @GetMapping("/code/callback")
    public ResponseEntity receiveCodeRedirectRequest(HttpServletRequest request) {

        String code = request.getParameter("code");
        return ResponseEntity.ok(code);
    }

    @PostMapping("/code")
    public ResponseEntity setWpsAuthorizationCode(@RequestBody Map<String, String> body) {

        String code = body.getOrDefault("code", null);
        if (code == null) return ResponseEntity.badRequest().build();
        apiKeyHolder.setCode(code);
        String accessToken = service.getAccessToken();
        if (accessToken == null) return ResponseEntity.badRequest().build();
        apiKeyHolder.setAccessToken(accessToken);
        return ResponseEntity.ok("set code successfully and refresh the access Token");
    }
}
