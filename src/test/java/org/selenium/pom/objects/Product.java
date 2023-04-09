package org.selenium.pom.objects;

import org.selenium.utils.JacksonUtils;

import java.io.IOException;

public class Product {
    public void setId(int id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private int id;
    private boolean featured;
    private String productName;

    public Product(){}

    public Product(int id) throws IOException {
        Product[] products = JacksonUtils.deserializeJson("products.json",Product[].class);
        for(Product product: products ){
        if(product.getId() == id){
            this.id = id;
            this.productName =  product.getProductName();
            this.featured = product.isFeatured();
        }
        }
    }
    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }
    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

}
