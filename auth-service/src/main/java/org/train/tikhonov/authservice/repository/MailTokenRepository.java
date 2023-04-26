package org.train.tikhonov.authservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.train.tikhonov.authservice.entity.MailTokenEntity;


@Repository
public interface MailTokenRepository extends CrudRepository<MailTokenEntity, String> {

}