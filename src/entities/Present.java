package entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Present {

    private String productName;
    private Double price;
    private String category;
    @JsonProperty(value = "quantity", access = JsonProperty.Access.WRITE_ONLY)
    private Integer quantity;

    public Present() {
    }

    public Present(final Double price, final String productName, final String category,
                   final Integer quantity) {
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
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

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
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

    @JsonIgnore
    public Integer getQuantity() {
        return quantity;
    }
}
