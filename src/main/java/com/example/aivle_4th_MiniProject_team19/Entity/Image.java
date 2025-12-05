package com.example.aivle_4th_MiniProject_team19.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Image extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "image_id")
    private Long id;
    private String originFileName;
    private String modifiedFileName;

    @OneToOne(mappedBy = "image")
    private Book book;

    public void setBook(Book book) {
        this.book = book;
    }
}
