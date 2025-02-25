package com.spring3.oauth.jwt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LICENSE_KEY")
public class LicenseKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "KEY_NAME")
    private String keyName;

    @Column(name = "KEY_DURANTION")
    private String keyDuration;  // monthly, quatarly, annually

    @Column(name = "KEY_PRICE")
    private String keyPrice;

}
