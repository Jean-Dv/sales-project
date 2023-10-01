package logic;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServiceTest {
    private Service service;

    @Before
    public void setUp() {
        service = new Service();
    }

    @Test
    public void testAddProduct() {
        Product productValid = new Product("P1", "Product 1", 10.0, 50, TypeProduct.VIVERES, LocalDate.now());
        Product productWithSameId = new Product("P1", "Product 1", 10.0, 50, TypeProduct.VIVERES, LocalDate.now());

        assertTrue(service.addProduct(productValid));
        assertFalse(service.addProduct(productWithSameId));
    }

    @Test
    public void testAddProductInvalidWithStock() {
        try {
            Product productWithStockInvalid = new Product("P2", "Product 2", 10.0, 2, TypeProduct.VIVERES,
                    LocalDate.now());
            service.addProduct(productWithStockInvalid);
            fail("Error: " + productWithStockInvalid.getStock() + " is less than " + Product.STOCK_MIN);
        } catch (IllegalArgumentException e) {
            assertEquals("The stock must be greater than or equal to " + Product.STOCK_MIN, e.getMessage());
        }
    }

    @Test
    public void testUpdateProduct() {
        Product productValid = new Product("P1", "Product 1", 10.0, 50, TypeProduct.VIVERES, LocalDate.now());
        Product updatedProduct = new Product("P1", "Updated Product 1", 12.0, 60, TypeProduct.VIVERES, LocalDate.now());

        assertTrue(service.addProduct(productValid));
        Product result = service.updateProduct(updatedProduct);
        assertNotNull(result);
        assertEquals(updatedProduct, result);
    }

    @Test
    public void testDeleteProduct() {
        Product productValid = new Product("P1", "Product 1", 10.0, 50, TypeProduct.VIVERES, LocalDate.now());

        assertTrue(service.addProduct(productValid));
        Product result = service.deleteProduct(productValid);
        assertNotNull(result);
        assertEquals(productValid, result);
    }

    @Test
    public void testAddBill() {
        Bill billValid = new Bill("B1", LocalDate.now());

        assertTrue(service.addBill(billValid));
    }

    @Test
    public void testAddDetailsToBill() {
        Bill bill = new Bill("B1", LocalDate.now());
        Product product = new Product("P1", "Product 1", 10.0, 50, TypeProduct.VIVERES, LocalDate.now());

        assertTrue(service.addBill(bill));

        Detail detail = service.addDetailsToBill(bill, product, (short) 10);

        assertNotNull(detail);
        assertEquals(product, detail.getProduct());
    }

    @Test
    public void testUpdateStockProduct() {
        Product product = new Product("P1", "Product 1", 10.0, 50, TypeProduct.VIVERES, LocalDate.now());
        service.addProduct(product);

        Product updatedProduct = service.updateStockProduct(product, (short) 10);

        assertNotNull(updatedProduct);
        assertEquals(60, updatedProduct.getStock());
    }

    @Test
    public void testGetDetailsOfBill() {
        Bill bill = new Bill("B1", LocalDate.now());
        Product product = new Product("P1", "Product 1", 10.0, 50, TypeProduct.VIVERES, LocalDate.now());

        service.addBill(bill);
        service.addProduct(product);
        service.addDetailsToBill(bill, product, (short) 10);

        ArrayList<Detail> details = service.getDetailsOfBill(bill);

        assertNotNull(details);
        assertEquals(1, details.size());
    }

}
