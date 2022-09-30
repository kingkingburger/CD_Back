package SilkLoad.entity.NotificationsEnum;

public enum NotificationsType {

    buyNow("즉시 구매"), buyAuction("경매 구매");

    private final String description;

    NotificationsType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }



    }
