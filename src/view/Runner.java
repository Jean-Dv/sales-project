package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import logic.Product;
import presenter.Presenter;

public class Runner {
    private Presenter presenter;
    private Scanner scanner;

    public Runner() {
        this.presenter = new Presenter();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Runner runner = new Runner();
        System.out.println("Bienvenido al sistema de facturación");
        runner.logIn();
    }

    public void logIn() {
        try {
            System.out.println("Ingrese su tipo de usuario: ");
            System.out.println("1. Administrador");
            System.out.println("2. Vendedor");
            int user = Integer.parseInt(this.scanner.nextLine());
            if (user == 1) {
                this.menuAdministrador();
            } else if (user == 2) {
                this.menuSeller();
            } else {
                System.out.println("Opción inválida");
                this.logIn();
            }

        } catch (Exception e) {
            System.out.println("Opción inválida");
            this.logIn();
        }
    }

    public void menuAdministrador() {
        try {
            System.out.println("Bienvenido al menú de administrador");
            System.out.println("1. Agregar producto");
            System.out.println("2. Actualizar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Buscar producto");
            System.out.println("5. Actualizar stock de producto");
            System.out.println("6. Regresar al menu principal");
            int option = Integer.parseInt(this.getIntegerInput("Ingrese una opción"));
            switch (option) {
                case 1:
                    this.addProduct();
                    break;
                case 2:
                    this.updateProduct();
                    break;
                case 3:
                    this.deleteProduct();
                    break;
                case 4:
                    this.findProduct();
                    break;
                case 5:
                    this.updateStockProduct();
                    break;
                case 6:
                    this.logIn();
                    break;
                default:
                    System.out.println("Opción inválida");
                    this.menuAdministrador();
                    break;
            }

        } catch (Exception e) {
            System.out.println("Opción inválida");
            System.out.println(e.getMessage());
            this.menuAdministrador();
        }
    }

    public void menuSeller() {
        try {
            System.out.println("Bienvenido al menú de vendedor");
            System.out.println("1. Agregar factura");
            System.out.println("2. Obtener los detalles de una factura");
            System.out.println("3. Añadir una compra a una factura");
            System.out.println("4. Lista de productos actuales");
            System.out.println("5. Regresar al menu principal");
            int option = Integer.parseInt(this.getIntegerInput("Ingrese una opción"));
            switch (option) {
                case 1:
                    this.addBill();
                    break;
                case 2:
                    this.getDetailsOfBill();
                    break;
                case 3:
                    this.addDetailsToBill();
                    break;
                case 4:
                    this.getAllProducts();
                    break;
                case 5:
                    this.logIn();
                    break;
                default:
                    System.out.println("Opción inválida");
                    this.menuSeller();
                    break;
            }

        } catch (Exception e) {
            System.out.println("Opción inválida");
            System.out.println(e.getMessage());
            this.menuSeller();
        }
    }

    public void addBill() {
        if (this.presenter.addBill()) {
            System.out.println("Factura agregada correctamente");
            System.out.println("Número de factura: " + this.presenter.getBills().length);
        } else {
            System.out.println("Factura no agregada");
        }
        this.menuSeller();
    }

    public void getDetailsOfBill() {
        String numberBill = this.getIntegerInput("Ingrese el número de la factura");
        String[][] details = this.presenter.getDetailsOfBill(new String[]{numberBill});
        if (details == null) {
            System.out.println("Factura no encontrada");
            this.getDetailsOfBill();
        }
        String[] total = this.presenter.calcTotalBill(new String[]{numberBill});
        this.showBill(this.showDetails(details), this.showTotal(total));
        this.menuSeller();
    }

    public void addDetailsToBill() {
        String numberBill = this.getIntegerInput("Ingrese el número de la factura");
        String idProduct = this.getInput("Ingrese el código del producto");
        String cant = this.getIntegerInput("Ingrese la cantidad del producto");
        String[] detail = this.presenter.addDetailsToBill(new String[]{numberBill, idProduct, cant});
        if (detail == null) {
            System.out.println("Factura o producto no encontrado");
            this.addDetailsToBill();
        }
        this.showBill(this.showDetail(detail));
        this.menuSeller();
    }

    public void getAllProducts() {
        String[][] products = this.presenter.getProducts();
        this.showTable(this.showProducts(products));
        this.menuSeller();
    }

    public void addProduct() {
        String[] product = new String[6];
        product[0] = this.getInput("Ingrese el código del producto");
        product[1] = this.getInput("Ingrese la descripción del producto");
        product[2] = this.getDoubleInput("Ingrese el valor del producto");
        product[3] = this.getStockInput("Ingrese el stock del producto");
        product[4] = this.getTypeProductInput("Ingrese el tipo de producto");
        product[5] = this.getDateInput("Ingrese la fecha de vencimiento del producto (dd/MM/yyyy)");

        if (!validateDate(product[5])) {
            System.out.println("Fecha inválida, intente con una fecha mayor al día de hoy.");
            this.addProduct();
        }

        this.presenter.addProduct(product);
        this.menuAdministrador();
    }

    public void updateProduct() {
        String idProduct = this.getInput("Ingrese el código del producto");
        String[] product = this.presenter.findProduct(idProduct);
        if (product == null) {
            System.out.println("Producto no encontrado");
            this.updateProduct();
        }
        System.out.println("Ingrese los nuevos datos del producto");
        product[0] = this.getInput("Ingrese el código del producto", product[0]);
        product[1] = this.getInput("Ingrese la descripción del producto", product[1]);
        product[2] = this.getDoubleInput("Ingrese el valor del producto", product[2]);
        product[3] = this.getStockInput("Ingrese el stock del producto", product[3]);
        product[4] = this.getTypeProductInput("Ingrese el tipo de producto");
        product[5] = this.getDateInput("Ingrese la fecha de vencimiento del producto (dd/MM/yyyy)", product[5]);

        if (!validateDate(product[5])) {
            System.out.println("Fecha inválida, intente con una fecha mayor al día de hoy.");
            this.updateProduct();
        }
        String[] productUpdated = this.presenter.updateProduct(product);
        this.showTable(this.showProduct(productUpdated));
        this.menuAdministrador();
    }

    public void deleteProduct() {
        String idProduct = this.getInput("Ingrese el código del producto");
        String[] product = this.presenter.findProduct(idProduct);
        if (product == null) {
            System.out.println("Producto no encontrado");
            this.deleteProduct();
        }
        String[] productDeleted = this.presenter.deleteProduct(product);
        this.showTable(this.showProduct(productDeleted));
        this.menuAdministrador();
    }

    public void findProduct() {
        String idProduct = this.getInput("Ingrese el código del producto");
        String[] product = this.presenter.findProduct(idProduct);
        if (product == null) {
            System.out.println("Producto no encontrado");
            this.findProduct();
        }
        this.showTable(this.showProduct(product));
        this.menuAdministrador();
    }

    public void updateStockProduct() {
        String idProduct = this.getInput("Ingrese el código del producto");
        String[] product = this.presenter.findProduct(idProduct);
        if (product == null) {
            System.out.println("Producto no encontrado");
            this.updateStockProduct();
        }
        String stock = this.getStockInput("Ingrese el nuevo stock del producto", product[3]);
        String[] productUpdated = this.presenter.updateStockProduct(new String[]{idProduct, stock});
        this.showTable(this.showProduct(productUpdated));
        this.menuAdministrador();
    }

    private String getInput(String prompt) {
        System.out.println(prompt);
        return this.scanner.nextLine();
    }

    private String getInput(String prompt, String valueActual) {
        System.out.println(prompt);
        System.out.println("Presione enter para mantener el valor actual: " + valueActual);
        String input = this.scanner.nextLine();
        if (input.equals("")) {
            return valueActual;
        }
        return input;
    }

    private String getDoubleInput(String prompt) {
        String input;
        do {
            input = getInput(prompt);
        } while (!isDouble(input));
        return input;
    }

    private String getDoubleInput(String prompt, String valueActual) {
        String input;
        do {
            input = getInput(prompt, valueActual);
        } while (!isDouble(input));
        return input;
    }

    private String getIntegerInput(String prompt) {
        String input;
        do {
            input = getInput(prompt);
        } while (!isInteger(input));
        return input;
    }

    private String getIntegerInput(String prompt, String valueActual) {
        String input;
        do {
            input = getInput(prompt, valueActual);
        } while (!isInteger(input));
        return input;
    }

    private String getStockInput(String prompt) {
        String input;
        do {
            input = getIntegerInput(prompt);
        } while (!validateStock(input));
        return input;
    }

    private String getStockInput(String prompt, String valueActual) {
        String input;
        do {
            input = getIntegerInput(prompt, valueActual);
        } while (!validateStock(input));
        return input;
    }

    private String getTypeProductInput(String prompt) {
        String input;
        System.out.println("1. Viveres");
        System.out.println("2. Licores");
        System.out.println("3. Medicinas");
        System.out.println("4. Aseo");
        System.out.println("5. Otros");
        do {
            input = getIntegerInput(prompt);
        } while (!isInteger(input) || Integer.parseInt(input) < 1 || Integer.parseInt(input) > 5);

        return switch (Integer.parseInt(input)) {
            case 1 -> "VIVERES";
            case 2 -> "LICORES";
            case 3 -> "MEDICINAS";
            case 4 -> "ASEO";
            default -> "";
        };
    }

    private String getDateInput(String prompt) {
        String input;
        do {
            input = getInput(prompt);
        } while (!isDate(input));
        return input;
    }

    private String getDateInput(String prompt, String valueActual) {
        String input;
        do {
            input = getInput(prompt, valueActual);
        } while (!isDate(input));
        return input;
    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un valor numérico");
            return false;
        }
    }

    private boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un valor numérico");
            return false;
        }
    }

    private boolean isDate(String input) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(input, formatter);
            return true;
        } catch (Exception e) {
            System.out.println("Ingrese una fecha válida (dd/MM/yyyy)");
            return false;
        }
    }

    private boolean validateDate(String dateStr) {
        LocalDate current = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate inputDate = LocalDate.parse(dateStr, formatter);

        return isDate(dateStr) && !inputDate.isBefore(current);
    }

    private boolean validateStock(String stock) {
        return isInteger(stock) && Integer.parseInt(stock) >= Product.STOCK_MIN;
    }

    private void showTable(String formattedProduct) {
        System.out.println("Tabla de Producto:");
        System.out.println("+------------+----------------------+------------+------------+------------+--------------+");
        System.out.println("|    ID      |     Descripción      |   Precio   |   Stock    |    Tipo    |   Caducidad  |");
        System.out.println("+------------+----------------------+------------+------------+------------+--------------+");
        System.out.println(formattedProduct);
        System.out.println("+------------+----------------------+------------+------------+------------+--------------+");
    }

    private void showBill(String formattedDetails, String formattedTotal) {
        System.out.println("Tabla de Detalles:");
        System.out.println("+------------+----------------------+------------+------------+------------+");
        System.out.println("|    ID      |     Descripción      |   Precio   |  Cantidad  |  SubTotal  |");
        System.out.println("+------------+----------------------+------------+------------+------------+");
        System.out.println(formattedDetails);
        System.out.println("+------------+----------------------+------------+------------+------------+");
        System.out.println(formattedTotal);
        System.out.println("+------------+----------------------+------------+------------+------------+");
    }

    private void showBill(String formattedDetails) {
        System.out.println("Tabla de Detalles:");
        System.out.println("+------------+----------------------+------------+------------+------------+");
        System.out.println("|    ID      |     Descripción      |   Precio   |  Cantidad  |  SubTotal  |");
        System.out.println("+------------+----------------------+------------+------------+------------+");
        System.out.println(formattedDetails);
        System.out.println("+------------+----------------------+------------+------------+------------+");
    }

    private String showProduct(String[] product) {
        String format = "| %-10s | %-20s | %-10s | %-10s | %-10s | %-12s |";
        return String.format(format, product[0], product[1],
                        product[2], product[3],
                        product[4], product[5]);
    }

    private String showProducts(String[][] products) {
        String format = "| %-10s | %-20s | %-10s | %-10s | %-10s | %-12s |";
        String formattedProducts = "";
        for (String[] product : products) {
            formattedProducts += String.format(format, product[0], product[1],
                    product[2], product[3],
                    product[4], product[5]) + "\n";
        }
        return formattedProducts;
    }

    private String showDetails(String[][] details) {
        String format = "| %-10s | %-20s | %-10s | %-10s | %-10s |";
        String formattedDetails = "";
        for (String[] detail : details) {
            formattedDetails += String.format(format, detail[0], detail[1],
                    detail[2], detail[3],
                    detail[4]) + "\n";
        }
        return formattedDetails;
    }

    private String showDetail(String[] detail) {
        String format = "| %-10s | %-20s | %-10s | %-10s | %-10s |";
        return String.format(format, detail[0], detail[1],
                detail[2], detail[3],
                detail[4]);
    }

    private String showTotal(String[] total) {
        String format = "| %-10s   %-20s | %-10s   %-10s | %-10s |";
        return String.format(format, "Total", "",
                "", "",
                total[0]);
    }
}
