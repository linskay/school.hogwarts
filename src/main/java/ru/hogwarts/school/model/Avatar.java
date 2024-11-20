package ru.hogwarts.school.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "avatar")
public class Avatar {

    @Id
    @GeneratedValue
    private Long id;

    private String filePath;
    private long fileSize;

    private String mediaType;

    @Lob
    @JsonIgnore
    private byte[] data;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

    public Avatar(String filePath, long fileSize, String mediaType, byte[] data, Student student) {
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.data = data;
        this.student = student;
    }

    public Avatar() {
    }

    public void setStudent(Student student) {
        this.student = student;
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

    public String getFilePath() {
        return filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public byte[] getData() {
        return data;
    }

    public String getMediaType() {
        return mediaType;
    }

    public Student getStudent() {
        return student;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
