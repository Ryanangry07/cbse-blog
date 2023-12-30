package com.loloao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class MergeRequestDTO {
    private String[] oldIDLists;
    private String newName;
}
