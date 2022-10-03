package SilkLoad.entity.ProductEnum;

public enum ProductType {

    sale("판매"),
    soldOut("판매 완료"),
    trading("거래"),
    cancel("거래 취소");
    private final String description;

    ProductType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    }
