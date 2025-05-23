package com.blockbank.blockbank.service.impl;

import com.blockbank.blockbank.dto.request.LoginRequest;
import com.blockbank.blockbank.dto.request.RegisterRequest;
import com.blockbank.blockbank.dto.response.AuthResponse;
import com.blockbank.blockbank.dto.response.UserResponseDto;
import com.blockbank.blockbank.entity.User;
import com.blockbank.blockbank.entity.enums.Role;
import com.blockbank.blockbank.repository.UserRepository;
import com.blockbank.blockbank.service.AuthService;
import com.blockbank.blockbank.service.WalletService;
import com.blockbank.blockbank.util.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final WalletService walletService;
    @Value("${web3.private-key}")
    private String PrivateKey;
    @Value("${web3.wallet.address}")
    private String WalletAddress;


    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil, AuthenticationManager authenticationManager, WalletService walletService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.walletService = walletService;
    }


    @Override
    public UserResponseDto register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build();
        
         User saved = userRepository.save(user);


         //JUST TRYING TO CREATE WALLET FOR ADMIN :)
         if(Objects.equals(saved.getUsername(), "ADMIN-USERNAME")){
            walletService.createWalletForMe(saved, WalletAddress, PrivateKey);
        }else{
            walletService.createWalletForUser(saved);
        }

        return UserResponseDto.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .createdAt(saved.getCreatedAt())
                .build();

    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            User king = userRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("kullanıcı bulunamadı"));
            System.out.println("Kayıtlı şifre: " + king.getPassword());
            System.out.println("Gelen şifre: " + loginRequest.getPassword());
            System.out.println("Eşleşme sonucu: " + passwordEncoder.matches(loginRequest.getPassword(), king.getPassword()));

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            if (auth == null) {
                throw new RuntimeException("Kullanıcı adı veya şifre hatalı");
            }

            String username = auth.getName();


            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

            String userId = user.getId().toString();

            String ethAddress = user.getWallet() != null ? user.getWallet().getAddress() : null;

            String token = jwtUtil.generateToken(username, userId,ethAddress);

            return new AuthResponse(token); //

        } catch (Exception e) {
            throw new RuntimeException("Kullanıcı adı veya şifre hatalı");
        }
    }
}
