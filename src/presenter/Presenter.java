package presenter;

import logic.Service;
import logic.Product;
import logic.TypeProduct;
import logic.Bill;
import logic.Detail;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Presenter {
    private Service service;

    public Presenter() {
        this.service = new Service();
    }

    public boolean addProduct(String[] data) throws IllegalArgumentException {
        Product product = new Product(data[0], data[1], Double.parseDouble(data[2]), Short.parseShort(data[3]), this.parseTypeProduct(data[4]), this.parseLocalDate(data[5]));
        return this.service.addProduct(product);
    }

    public String[] updateProduct(String[] data) {
        Product product = new Product(data[0], data[1], Double.parseDouble(data[2]), Short.parseShort(data[3]), this.parseTypeProduct(data[4]), this.parseLocalDate(data[5]));
        Product productUpdated = this.service.updateProduct(product);
        return this.productToString(productUpdated);
    }

    public String[] deleteProduct(String[] data) {
        Product product = new Product(data[0], data[1], Double.parseDouble(data[2]), Short.parseShort(data[3]), this.parseTypeProduct(data[4]), this.parseLocalDate(data[5]));
        Product productDeleted = this.service.deleteProduct(product);
        return this.productToString(productDeleted);
    }

    public String[] findProduct(String idProduct) {
        int position = this.service.findProduct(idProduct);
        return this.productToString(this.service.getProducts().get(position));
    }

    public boolean addBill() {
        Bill bill = new Bill(String.valueOf(this.service.getBills().size() + 1), LocalDate.now());
        return this.service.addBill(bill);
    }

    public String[] addDetailsToBill(String[] data) {
        int positionBill = this.service.findBill(data[0]);
        int positionProduct = this.service.findProduct(data[1]);
        Bill bill = this.service.getBills().get(positionBill);
        Product product = this.service.getProducts().get(positionProduct);
        short cant = Short.parseShort(data[2]);
        return this.detailToString(this.service.addDetailsToBill(bill, product, cant));
    }

    public String[] calcTotalBill(String[] data) {
        int positionBill = this.service.findBill(data[0]);
        Bill bill = this.service.getBills().get(positionBill);
        return new String[]{String.valueOf(bill.calcTotal())};
    }

    public String[] updateStockProduct(String[] data) {
        int positionProduct = this.service.findProduct(data[0]);
        Product product = this.service.getProducts().get(positionProduct);
        short cant = Short.parseShort(data[1]);
        return this.productToString(this.service.updateStockProduct(product, cant));
    }

    public String[][] getDetailsOfBill(String[] data) {
        int positionBill = this.service.findBill(data[0]);
        Bill bill = this.service.getBills().get(positionBill);
        return this.detailsToString(this.service.getDetailsOfBill(bill));
    }

    public String[][] getBills() {
        ArrayList<Bill> bills = this.service.getBills();
        String[][] billsString = new String[bills.size()][];
        for (int i = 0; i < bills.size(); i++) {
            billsString[i] = this.billToString(bills.get(i));
        }
        return billsString;
    }

    public String[][] getProducts() {
        ArrayList<Product> products = this.service.getProducts();
        String[][] productsString = new String[products.size()][];
        for (int i = 0; i < products.size(); i++) {
            productsString[i] = this.productToString(products.get(i));
        }
        return productsString;
    }

    private String[] parseDate(String date) {
        return date.split("/");
    }

    private LocalDate parseLocalDate(String date) {
        String[] dateParsed = this.parseDate(date);
        return LocalDate.of(Integer.parseInt(dateParsed[2]), Integer.parseInt(dateParsed[1]), Integer.parseInt(dateParsed[0]));
    }

    private TypeProduct parseTypeProduct(String typeProduct) {
        return switch (typeProduct) {
            case "VIVERES" -> TypeProduct.VIVERES;
            case "LICORES" -> TypeProduct.LICORES;
            case "MEDICINAS" -> TypeProduct.MEDICINAS;
            case "ASEO" -> TypeProduct.ASEO;
            default -> null;
        };
    }

    public String[] productToString(Product product) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return product != null ? new String[]{product.getIdProduct(), product.getDescription(), String.valueOf(product.getValue()), String.valueOf(product.getStock()), product.getTypeProduct().toString(), product.getDateExpired().format(formatter)} : null;
    }

    public String[] billToString(Bill bill) {
        return bill != null ? new String[]{bill.getNumber(), bill.getDateBill().toString()} : null;
    }

    public String[] detailToString(Detail detail) {
        return detail != null ? new String[]{detail.getProduct().getIdProduct(), detail.getProduct().getDescription(), String.valueOf(detail.getProduct().getValue()), String.valueOf(detail.getCant()), String.valueOf(detail.calcSubTotal())} : null;
    }

    public String[][] detailsToString(ArrayList<Detail> details) {
        String[][] detailsString = new String[details.size()][];
        for (int i = 0; i < details.size(); i++) {
            detailsString[i] = this.detailToString(details.get(i));
        }
        return detailsString;
    }
}
