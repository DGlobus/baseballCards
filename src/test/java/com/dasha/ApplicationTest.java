package com.dasha;

import com.dasha.repository.EmployeeRepository;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnablePostgresIntegrationTest
public class ApplicationTest {
    @Autowired
    EmployeeRepository repository;

    @Test
    void contextLoad() {
    }
}
