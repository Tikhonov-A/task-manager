package org.train.tikhonov.authservice.entity;


import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RedisHash(value = "MailToken", timeToLive = 300)
public class MailTokenEntity implements Serializable {

    @Id
    private String id;
    private UUID personId;
}
