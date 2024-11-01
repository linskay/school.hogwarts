package ru.hogwarts.school.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@Entity
public class Avatar {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;
    private long fileSize;
    private String mediaType;

    @Lob
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setData(byte[] bytes) {
        this.data = bytes;
    }

    public void setMediaType(String contentType) {
        this.mediaType = contentType;
    }

    public void setFilePath(String string) {
        this.filePath = string;
    }

    public void setFileSize(long size) {
        this.fileSize = size;
    }

    public Long getId() {
        return id;
    }
}
