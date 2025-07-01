package com.homeaid.user.service;

import com.homeaid.domain.Customer;
import com.homeaid.user.dto.request.AdminCustomerSearchRequestDto;
import com.homeaid.user.dto.response.CustomerDetailResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminCustomerService {

  Page<Customer> searchCustomers(AdminCustomerSearchRequestDto dto, Pageable pageable);

  CustomerDetailResponseDto getCustomerDetail(Long userId);
}
