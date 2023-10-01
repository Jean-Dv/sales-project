package logic;

import java.util.ArrayList;
import java.util.Comparator;

public class Service {
    private ArrayList<Product> products;
    private ArrayList<Bill> bills;

    public Service() {
        this.products = new ArrayList<Product>();
        this.bills = new ArrayList<Bill>();
    }

    public boolean addProduct(Product product) {
        if (this.findProduct(product.getIdProduct()) == -1) {
            this.products.add(product);
            return true;
        }
        return false;
    }

    public Product updateProduct(Product product) {
        int positionProduct = this.findProduct(product.getIdProduct());
        if (positionProduct != -1) {
            this.products.set(positionProduct, product);
            return product;
        }
        return null;
    }

    public Product deleteProduct(Product product) {
        int positionProduct = this.findProduct(product.getIdProduct());
        if (positionProduct != -1) {
            this.products.remove(positionProduct);
            return product;
        }
        return null;
    }

    public int findProduct(String idProduct) {
        ArrayList<Product> arraySorted = this.sortProducts(Comparator.comparing(Product::getIdProduct));
        int left = 0;
        int right = arraySorted.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int result = arraySorted.get(mid).getIdProduct().compareTo(idProduct);
            if (result == 0) {
                return mid;
            } else if (result < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public boolean addBill(Bill bill) {
        if (this.findBill(bill.getNumber()) == -1) {
            this.bills.add(bill);
            return true;
        }
        return false;
    }

    public Detail addDetailsToBill(Bill bill, Product product, short cant) {
        if (this.findBill(bill.getNumber()) != -1) {
            if (bill.addDetail(product, cant)) {
                return new Detail(product, cant);
            }
        }
        return null;
    }

    public Product updateStockProduct(Product product, short cant) {
        if (this.findProduct(product.getIdProduct()) != -1) {
            product.setStock(cant);
            return product;
        }
        return null;
    }

    public ArrayList<Detail> getDetailsOfBill(Bill bill) {
        if (this.findBill(bill.getNumber()) != -1) {
            return bill.getDetails();
        }
        return null;
    }

    public int findBill(String number) {
        ArrayList<Bill> arraySorted = this.sortBills(Comparator.comparing(Bill::getNumber));
        int left = 0;
        int right = arraySorted.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int result = arraySorted.get(mid).getNumber().compareTo(number);
            if (result == 0) {
                return mid;
            } else if (result < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public ArrayList<Product> getProducts() {
        return new ArrayList<>(this.products);
    }

    public ArrayList<Bill> getBills() {
        return new ArrayList<>(this.bills);
    }

    private ArrayList<Product> sortProducts(Comparator<Product> comparator) {
        ArrayList<Product> products = this.getProducts();
        products.sort(comparator);
        return products;
    }

    private ArrayList<Bill> sortBills(Comparator<Bill> comparator) {
        ArrayList<Bill> bills = this.getBills();
        bills.sort(comparator);
        return bills;
    }
}
