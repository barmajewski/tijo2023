package pl.majewski.zichterrek.Enum;

public enum FileTypeEnum {

    FILE("Plik"),
    PHOTO("Zdjęcie");

    private final String value;

    FileTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
