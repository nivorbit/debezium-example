package com.nivorbit.customer.service.mutitenancy.resolver;

import com.nivorbit.customer.service.mutitenancy.TenantHttpProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HttpHeaderTenantResolver implements TenantResolver<HttpServletRequest> {

    private final TenantHttpProperties tenantHttpProperties;

    @Override
    public String resolveTenantId(HttpServletRequest request) {
        return request.getHeader(tenantHttpProperties.headerName());
    }
}
