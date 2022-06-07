package com.fidev.avilasoft.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "authorizations")
public class Authorization {

    @Id
    @Column(name = "auth_id", length = 36)
    private String id;

    @Column(nullable = false, length = 10)
    private String auth;

    @Column(name = "description", length = 20, nullable = false)
    private String desc;

    @PrePersist
    protected void prePersist() {
        this.id = UUID.randomUUID().toString();
    }

    @ToString.Exclude
    @OneToMany(mappedBy = "roleAuthPK.auth", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RolePermission> roles = new ArrayList<>();

}
