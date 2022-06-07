package com.fidev.avilasoft.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "usr_config")
public class UserConfig {

    @Id
    @Column(name = "config_id", length = 36)
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date dateCreated;

    @Column(name = "last_update", nullable = false)
    private long lastUpdate;

    @PrePersist
    protected void prePersist() {
        id = UUID.randomUUID().toString();
        dateCreated = new Date();
        lastUpdate = dateCreated.getTime();
    }

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", nullable = false)
    private User user;

}
