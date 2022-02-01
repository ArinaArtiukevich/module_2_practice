package com.esm.epam.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private Long id;

    @NotBlank(message = "Tag name should not be empty.")
    private String name;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Tag(this.getId(), this.getName());
    }
}
