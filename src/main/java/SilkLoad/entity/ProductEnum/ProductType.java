package SilkLoad.entity.ProductEnum;

public enum ProductType {

    sale("판매"), soldOut("판매 완료"), waiting("거래 중"), timeOut("마감");

    private final String description;

    ProductType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    }
