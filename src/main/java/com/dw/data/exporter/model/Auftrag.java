package com.dw.data.exporter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "auftraege")
@Entity
public class Auftrag {

    @Id
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    @Column(name = "auftragid")
    private String auftragId;

    private String artikelnummer;

    private String created;

    @Column(name = "lastchange")
    private String lastChange;


}
