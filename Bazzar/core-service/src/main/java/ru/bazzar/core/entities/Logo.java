package ru.bazzar.core.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@Table(name = "logo")
public class Logo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "size")
    private Long size;

    @Column(name = "content_type")
    private String contentType;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] bytes;

}
