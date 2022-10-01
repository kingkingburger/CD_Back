package SilkLoad.entity.OrderEnum;

public enum OrderType {

    unRegistered("미신청"),waiting("대기"), cancel("취소"),
    trading("낙찰가"), complete("완료");
    private final String description;

    OrderType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
