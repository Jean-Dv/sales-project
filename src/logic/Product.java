package logic;

import java.time.LocalDate;

public class Product {
    public static final byte STOCK_MIN = 5;

    private String idProduct;
    private String description;
    private double value;
    private int stock;
    private LocalDate dateExpired;
    private TypeProduct typeProduct;

    public Product() {
        this.idProduct = "";
        this.description = "";
        this.value = 0;
        this.stock = 6;
        this.dateExpired = LocalDate.now();
    }

    public Product(String idProduct, String description, double value, int stock, TypeProduct typeProduct, LocalDate dateExpired) {
        this.idProduct = idProduct;
        this.description = description;
        this.value = value;
        this.typeProduct = typeProduct;
        this.dateExpired = dateExpired;
        this.setStock(stock);
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(this.dateExpired);
    }

    public double calcIva() {
        return switch (this.typeProduct) {
            case VIVERES -> this.value * 0.08;
            case LICORES -> this.value * 0.19;
            case MEDICINAS -> this.value * 0.04;
            case ASEO -> this.value * 0.14;
            default -> 0.0;
        };
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) throws IllegalArgumentException {
        if (stock < STOCK_MIN) {
            throw new IllegalArgumentException("The stock must be greater than or equal to " + STOCK_MIN);
        }
        this.stock = stock;
    }

    public LocalDate getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(LocalDate dateExpired) {
        this.dateExpired = dateExpired;
    }

    public TypeProduct getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(TypeProduct typeProduct) {
        this.typeProduct = typeProduct;
    }

    @Override
    public String toString() {
        return "Product [idProduct=" + idProduct + ", description=" + description + ", value=" + value
                + ", dateExpired=" + dateExpired + "]";
    }
}
