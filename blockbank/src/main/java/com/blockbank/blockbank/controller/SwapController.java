package com.blockbank.blockbank.controller;

import com.blockbank.blockbank.dto.request.SwapRequest;
import com.blockbank.blockbank.dto.response.SwapResponse;
import com.blockbank.blockbank.dto.response.UserResponseDto;
import com.blockbank.blockbank.entity.User;
import com.blockbank.blockbank.service.SwapService;
import com.blockbank.blockbank.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/swap")
public class SwapController {
    private final SwapService swapService;
    private final UserService userService;

    public SwapController(SwapService swapService, UserService userService) {
        this.swapService = swapService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<SwapResponse> performSwap(@RequestBody @Valid SwapRequest request, @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();


        UserResponseDto user = userService.getUserByUsername(username);

        UUID userId = user.getId();

        SwapResponse response = swapService.swap(request, userId);

        return ResponseEntity.ok(response);
    }
}
