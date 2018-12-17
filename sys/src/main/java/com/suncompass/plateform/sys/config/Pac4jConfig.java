package com.suncompass.plateform.sys.config;

import com.suncompass.plateform.sys.security.FormUsernamePasswordAuthenticator;
import org.pac4j.cas.client.CasClient;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.config.CasConfiguration;
import org.pac4j.cas.config.CasProtocol;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.matching.PathMatcher;
import org.pac4j.http.authorization.generator.RememberMeAuthorizationGenerator;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.jwt.profile.JwtGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Pac4jConfig {
    private String loginUrl = "/login";
    private String prefixUrl = "http://localhost:8080/";
    private String callbackUrl= "/callback?client_name=form";
    private String salt = "a123456bcd";

    public CasConfiguration casConfiguration(){
        CasConfiguration casConfiguration = new CasConfiguration(loginUrl, prefixUrl);
        casConfiguration.setProtocol(CasProtocol.CAS30);
        return  casConfiguration;
    }

    public JwtGenerator jwtGenerator(){
        return  new JwtGenerator(new SecretSignatureConfiguration(salt), new SecretEncryptionConfiguration(salt));
    }

    protected JwtAuthenticator jwtAuthenticator(){
        JwtAuthenticator authenticator = new JwtAuthenticator();
        authenticator.addSignatureConfiguration(new SecretSignatureConfiguration(salt));
        authenticator.addEncryptionConfiguration(new SecretEncryptionConfiguration(salt));
        return authenticator;
    }

    protected ParameterClient parameterClient(){
        ParameterClient parameterClient = new ParameterClient("token", jwtAuthenticator());
        parameterClient.setSupportGetRequest(true);
        parameterClient.setName("jwt");
        return  parameterClient;
    }


    protected CasClient casClient(){
        CasClient casClient = new CasClient();
        casClient.setConfiguration(casConfiguration());
        casClient.setCallbackUrl(callbackUrl);
        casClient.setName("cas");
        return  casClient;
    }

    protected CasRestFormClient casRestFormClient(){
        CasRestFormClient casRestFormClient = new CasRestFormClient();
        casRestFormClient.setConfiguration(casConfiguration());
        casRestFormClient.setName("rest");
        return  casRestFormClient;
    }

    protected FormClient formClient(){
        RememberMeAuthorizationGenerator rememberMeAuthorizationGenerator = new RememberMeAuthorizationGenerator();

        FormClient formClient = new FormClient("/login", formUsernamePasswordAuthenticator());
        formClient.setName("form");
        formClient.setAuthorizationGenerator(rememberMeAuthorizationGenerator);
        return formClient;
    }

    @Bean
    public FormUsernamePasswordAuthenticator formUsernamePasswordAuthenticator(){
        return new FormUsernamePasswordAuthenticator();
    }

    @Bean
    protected Config config(){
        Clients clients = new Clients(callbackUrl, formClient(), casClient(), casRestFormClient(), parameterClient());

        Config config = new Config();
        config.setClients(clients);

        config.addMatcher("matcher", new PathMatcher().excludeRegex("^(/lib/|/bower_components/|/dist/|/plugins/|/login)*$"));
        return  config;
    }
}
