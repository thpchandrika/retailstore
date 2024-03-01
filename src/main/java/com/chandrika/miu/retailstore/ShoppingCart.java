package com.chandrika.miu.retailstore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {
    private int productQuantity;
    private String productNumber;
    private String productName;
    private double productPrice;
}
