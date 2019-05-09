package com.izettle.authmanagement.config;

import com.izettle.authmanagement.access.PermissionVoter;
import com.izettle.authmanagement.access.SecurityMetadataSource;
import java.util.Collections;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity
class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
        return new SecurityMetadataSource();
    }

    @Override
    protected AccessDecisionManager accessDecisionManager() {
        return new AffirmativeBased(Collections.singletonList(new PermissionVoter()));
    }
}