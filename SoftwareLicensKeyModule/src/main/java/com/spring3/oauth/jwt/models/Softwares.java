package com.spring3.oauth.jwt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author sajjadhaider
 * Created By sajjadhaider on 03-02-2025
 * @project oauth-jwt
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "SOFTWARES")
public class Softwares {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SOFTWARE_ID")
    private Long id;

    @Column(name = "SOFTWARE_NAME")
    private String softwareName;

    @Column(name = "SOFTWARE_URL")
    private String softwareURL;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<LicenseKey> LiceseKeys = new HashSet<>();

}
