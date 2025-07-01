package com.homeaid.user.controller;

import com.homeaid.common.response.CommonApiResponse;
import com.homeaid.common.response.PagedResponseDto;
import com.homeaid.domain.Customer;
import com.homeaid.dto.request.CustomerResponseDto;
import com.homeaid.user.dto.request.AdminCustomerSearchRequestDto;
import com.homeaid.user.dto.response.CustomerDetailResponseDto;
import com.homeaid.user.service.AdminCustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AdminCustomerController", description = "관리자 수요자 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/customers")
public class AdminCustomerController {

  private final AdminCustomerService adminCustomerService;

  @Operation(
      summary = "고객 전체 목록 조회",
      description = "관리자가 모든 고객을 페이징하여 조회합니다."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "조회 성공",
          content = @Content(schema = @Schema(implementation = CustomerResponseDto.class))),
      @ApiResponse(responseCode = "401", description = "인증 실패",
          content = @Content(schema = @Schema(implementation = CommonApiResponse.class)))
  })
  @GetMapping
  public ResponseEntity<CommonApiResponse<PagedResponseDto<CustomerResponseDto>>> getCustomers(
      @ModelAttribute AdminCustomerSearchRequestDto dto,
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "10") int size
  ) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

    Page<Customer> result = adminCustomerService.searchCustomers(dto, pageable);

    return ResponseEntity.ok(
        CommonApiResponse.success(
            PagedResponseDto.fromPage(result, CustomerResponseDto::toDto)
        )
    );
  }

  @GetMapping("/{userId}")
  @Operation(summary = "회원 상세조회", description = "특정 회원의 프로필 및 주소 목록을 통합 조회합니다.")
  @RolesAllowed("ADMIN")
  public ResponseEntity<CommonApiResponse<CustomerDetailResponseDto>> getCustomerDetail(
      @PathVariable Long userId) {
    CustomerDetailResponseDto detailDto = adminCustomerService.getCustomerDetail(userId);
    return ResponseEntity.ok(CommonApiResponse.success(detailDto));
  }
}
