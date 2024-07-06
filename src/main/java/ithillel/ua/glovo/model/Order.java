package ithillel.ua.glovo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Order {
    private  Long id;
    private List<Product> products;
    private  String status;
}
