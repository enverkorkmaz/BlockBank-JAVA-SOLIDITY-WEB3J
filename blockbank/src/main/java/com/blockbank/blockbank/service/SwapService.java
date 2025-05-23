package com.blockbank.blockbank.service;

import com.blockbank.blockbank.dto.request.SwapRequest;
import com.blockbank.blockbank.dto.response.SwapResponse;

import java.util.UUID;

public interface SwapService {
    SwapResponse swap(SwapRequest request, UUID userId);
}
