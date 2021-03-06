package gregmachado.com.panappfirebase.domain;

/**
 * Created by gregmachado on 12/11/16.
 */
public class Offer {

    private String id;
    private String productID;
    private String productName;
    private Double productPrice;
    private Double priceInOffer;
    private String productImage;
    private String type;
    private String bakeryId;
    private int itensSale;
    private int discount;
    private String bakeryName;

    public Offer() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Double getPriceInOffer() {
        return priceInOffer;
    }

    public void setPriceInOffer(Double priceInOffer) {
        this.priceInOffer = priceInOffer;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBakeryId() {
        return bakeryId;
    }

    public void setBakeryId(String bakeryId) {
        this.bakeryId = bakeryId;
    }

    public int getItensSale() {
        return itensSale;
    }

    public void setItensSale(int itensSale) {
        this.itensSale = itensSale;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getBakeryName() {
        return bakeryName;
    }

    public void setBakeryName(String bakeryName) {
        this.bakeryName = bakeryName;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
}
