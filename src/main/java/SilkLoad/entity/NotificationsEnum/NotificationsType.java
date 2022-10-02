package SilkLoad.entity.NotificationsEnum;

public enum NotificationsType {

    buyNow("즉시 구매"), buyAuction("경매 구매"), completion("구매 완료"),
    trade("낙찰") , cancle("취소");

    private final String description;

    NotificationsType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }



    }
