package SilkLoad.entity.OrderEnum;

public enum OrderType {

    waiting("주문 대기"), refusal("주문 거부"), trading("거래 중"), complete("주문 완료");

    private final String description;

    OrderType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
