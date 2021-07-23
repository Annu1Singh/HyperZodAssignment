package in.sundram.hyperzodassignment.datamodel;

public class MerchantDataModel {
    private String merchant_id;
    private String merchant_name;
    private String merchant_rating;
    private String merchant_img;
    private String merchant_email;
    private String merchant_description;
    private String merchant_display_offer;
    private String merchant_location;
    private String merchant_special_dish;

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getMerchant_email() {
        return merchant_email;
    }

    public void setMerchant_email(String merchant_email) {
        this.merchant_email = merchant_email;
    }

    public String getMerchant_location() {
        return merchant_location;
    }

    public void setMerchant_location(String merchant_location) {
        this.merchant_location = merchant_location;
    }

    public String getMerchant_img() {
        return merchant_img;
    }

    public void setMerchant_img(String merchant_img) {
        this.merchant_img = merchant_img;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getMerchant_rating() {
        return merchant_rating;
    }

    public void setMerchant_rating(String merchant_rating) {
        this.merchant_rating = merchant_rating;
    }

    public String getMerchant_description() {
        return merchant_description;
    }

    public void setMerchant_description(String merchant_description) {
        this.merchant_description = merchant_description;
    }

    public String getMerchant_display_offer() {
        return merchant_display_offer;
    }

    public void setMerchant_display_offer(String merchant_display_offer) {
        this.merchant_display_offer = merchant_display_offer;
    }

    public String getMerchant_special_dish() {
        return merchant_special_dish;
    }

    public void setMerchant_special_dish(String merchant_special_dish) {
        this.merchant_special_dish = merchant_special_dish;
    }

    @Override
    public String toString() {
        return "MerchantDataModel{" +
                "merchant_id='" + merchant_id + '\'' +
                ", merchant_name='" + merchant_name + '\'' +
                ", merchant_rating='" + merchant_rating + '\'' +
                ", merchant_description='" + merchant_description + '\'' +
                ", merchant_display_offer='" + merchant_display_offer + '\'' +
                ", merchant_special_dish='" + merchant_special_dish + '\'' +
                '}';
    }
}
