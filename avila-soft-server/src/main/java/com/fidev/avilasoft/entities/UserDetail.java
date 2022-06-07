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
@Table(name = "usr_details")
public class UserDetail {

    @Id
    @Column(name = "detail_id", length = 36)
    private String id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(name = "last_name", length = 80, nullable = false)
    private String lastName;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth", nullable = false)
    private Date date;

    @Column(name = "zip_code", length = 6, nullable = false)
    private String zipCode;

    @PrePersist
    protected void prePersist() {
        this.id = UUID.randomUUID().toString();
    }

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", nullable = false)
    private User user;
}
