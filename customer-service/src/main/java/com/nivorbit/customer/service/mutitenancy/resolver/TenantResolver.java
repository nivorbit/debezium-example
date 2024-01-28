package com.nivorbit.customer.service.mutitenancy.resolver;

import org.springframework.lang.NonNull;

@FunctionalInterface
public interface TenantResolver<T> {
    String resolveTenantId(@NonNull T object);
}
