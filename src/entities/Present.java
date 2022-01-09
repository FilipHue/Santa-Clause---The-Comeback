package entities;


public final class Present {

    private String productName;
    private Double price;
    private String category;

    public Present() {
    }

    public Present(final Double price, final String productName, final String category) {
        this.productName = productName;
        this.price = price;
        this.category = category;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }
}
