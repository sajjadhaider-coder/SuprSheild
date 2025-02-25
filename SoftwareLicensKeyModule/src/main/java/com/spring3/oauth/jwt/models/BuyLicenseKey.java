package com.spring3.oauth.jwt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BuyLicenseKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "userId")
    private long userId;

    @Column(name = "softwareId")
    private long softwareId;

    @Column(name = "softwareLicenseId")
    private long softwareLicenseId;

    @Column(name = "price")
    private long price;

    @Column(name = "purchaseDate")
    private LocalDateTime purchaseDate;

    @Column(name = "Duration")
    private String Duration; // MONTHLY, QUARTARLY, ANUALLY

    @Column(name = "expirydate")
    private LocalDateTime expirydate;

}
