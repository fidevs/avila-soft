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
@Table(name = "usr_transaction_token")
public class UsrTransaction {

    @Id
    @Column(name = "token_id", length = 36)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", length = 20, nullable = false)
    private UserTransType type;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "valid_until", nullable = false)
    private Date limitDate;

    @Column(length = 36, nullable = false, unique = true)
    private String token;

    @Column(name = "token_redeemed")
    private boolean redeemed;

    public UsrTransaction(UserTransType type, long tokenValidity) {
        this.type = type;
        createdDate = new Date(); // Calculate token validity range
        limitDate = new Date(createdDate.getTime() + tokenValidity);
        token = UUID.randomUUID().toString(); // Generate token
        redeemed = false;
    }

    @PrePersist
    protected void prePersist() {
        this.id = UUID.randomUUID().toString();
    }

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", nullable = false)
    private User user;

}
