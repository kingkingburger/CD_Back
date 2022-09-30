package SilkLoad.entity.OrderEnum;

public enum OrderType {

    unRegistered("미신청"),waiting("경매대기"), cancel("취소"),
    trading("낙찰"), complete("거래완료");
    private final String description;

    OrderType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
