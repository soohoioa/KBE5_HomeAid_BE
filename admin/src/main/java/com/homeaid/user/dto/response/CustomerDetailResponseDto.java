package com.homeaid.user.dto.response;

import com.homeaid.dto.response.CustomerAddressResponseDto;
import com.homeaid.dto.response.UserProfileResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CustomerDetailResponseDto {
  private UserProfileResponseDto profile;
  private List<CustomerAddressResponseDto> addresses;
}
