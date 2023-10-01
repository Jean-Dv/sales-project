package logic;

public class Detail {
    private Product product;
    private short cant;

    public Detail(Product product, short cant) {
        this.product = product;
        this.product.setStock((short) (this.product.getStock() - cant));
        this.cant = cant;
    }

    public double calcSubTotal() {
        return (this.product.getValue() + this.product.calcIva()) * this.cant;
    }

    public Product getProduct() {
        return product;
    }

    public short getCant() {
        return cant;
    }
}
