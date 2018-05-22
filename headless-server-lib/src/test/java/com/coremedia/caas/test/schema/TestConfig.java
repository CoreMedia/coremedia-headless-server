package com.coremedia.caas.test.schema;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
        "com.coremedia.caas.link",
        "com.coremedia.caas.schema"
})
public class TestConfig {
}
