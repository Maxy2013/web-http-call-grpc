package com.grpc.service.grpcservice.base;

/**
 * @author lz
 * 2018/10/23 10:19
 */
public class User {

    private String name;

    private String id;

    private String product;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
