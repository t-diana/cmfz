package com.nsc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Chapter implements Serializable {
    private String id;

    private String title;

    private String albumId;

    private String size;

    private String duration;

    private String src;

    private String status;

    private String other;
}