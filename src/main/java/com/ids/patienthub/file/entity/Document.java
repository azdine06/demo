package com.ids.patienthub.file.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;


@Entity
@Getter
@Setter
@ToString

@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    private String id;

    @Column(length = 100)
    private String filename;

    @Column(length = 100)
    private String contentType;
    private Long size;

}
}
