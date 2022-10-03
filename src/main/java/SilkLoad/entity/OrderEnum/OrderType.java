package SilkLoad.entity.OrderEnum;

public enum OrderType {


    unRegistered("미신청"),
    bid("입찰"),
    successfulBid("낙찰"),
    buyNow("바로 구매"),
    cancel("취소"),
    complete("거래완료");
    private final String description;

    OrderType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
