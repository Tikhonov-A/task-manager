package org.train.tikhonov.authservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.train.tikhonov.authservice.entity.StatusEntity;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StatusRepositoryTest {

    private final StatusRepository statusRepository;

    @Autowired
    public StatusRepositoryTest(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Test
    void itShouldFindStatusByName() {
        statusRepository.save(StatusEntity.builder().name("unverified").build());
        StatusEntity status = statusRepository.findByName("unverified");
        assertThat(status).isNotNull();
    }

    @Test
    void itShouldNotFindStatusByName() {
        StatusEntity status = statusRepository.findByName("unverified");
        assertThat(status).isNull();
    }
}