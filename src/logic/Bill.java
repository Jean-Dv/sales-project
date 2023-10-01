package logic;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bill {
    private String number;
    private LocalDate dateBill;
    private ArrayList<Detail> details;

    public Bill(String number, LocalDate dateBill) {
        this.number = number;
        this.dateBill = dateBill;
        this.details = new ArrayList<Detail>();
    }

    public double calcTotal() {
        double total = 0;
        for (Detail detail : this.details) {
            total += detail.calcSubTotal();
        }
        return total;
    }

    public boolean addDetail(Product product, short cant) {
        if (product.getStock() >= cant) {
            this.details.add(new Detail(product, cant));
            product.setStock((short) (product.getStock() - cant));
            return true;
        }
        return false;
    }

    public ArrayList<Detail> getDetails() {
        return new ArrayList<>(this.details);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDateBill() {
        return dateBill;
    }

    public void setDateBill(LocalDate dateBill) {
        this.dateBill = dateBill;
    }    
}
