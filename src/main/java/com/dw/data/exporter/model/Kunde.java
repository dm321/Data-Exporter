package com.dw.data.exporter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "kunde")
public class Kunde {

//    @EmbeddedId
//    private KundeId kundeId;
    @Id
    @Column(name = "kundenid")
    private Long kundenId;
    private String vorname;
    private String nachname;
    private String strasse;
    private String strassenzusatz;
    private String ort;
    private String land;
    private String plz;
    private String firmenname;
    @OneToMany
    @JoinColumn(name = "kundeid" )
    private List<Auftrag> auftaege;

}
