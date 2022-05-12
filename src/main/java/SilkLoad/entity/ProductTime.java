package SilkLoad.entity;

public enum ProductTime {

    ONE_DAY("24시간"), TWO_DAY("48시간"), NONE("비경매");

    // description = 24시간 or 48시간 or 비경매매, radio button 만들 때 사용
    private final String description;

    ProductTime(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}
