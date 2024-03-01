package com.chandrika.miu.retailstore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @NotBlank(message = "Product number cannot be empty")
    @Size(min = 2, max = 5, message = "size must be between 2 and 5")
    private String productNumber;

    @NotBlank
    @Size(min=2,max=20,message = "size must be between 2 and 20")
    private String name;

    private double price;
}
