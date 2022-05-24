package SilkLoad.entity.ProductEnum;

public enum ProductType {

    sale("판매 중"), soldOut("판매 완료"), waiting("거래 중"), cancel("거래 취소");

    private final String description;

    ProductType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    }
