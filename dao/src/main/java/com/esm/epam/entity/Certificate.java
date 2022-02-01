package com.esm.epam.entity;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certificate {
    private Long id;

    @NotBlank(message = "Certificate name should not be empty.")
    private String name;

    @NotBlank(message = "Certificate description should not be empty.")
    private String description;

    @NotNull(message = "Price should not be null.")
    @Min(value = 0, message = "Price should be positive.")
    private Integer price;

    @NotNull(message = "Duration should not be null.")
    @Min(value = 0, message = "Duration should be positive.")
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;
    private List<Tag> tags;

}