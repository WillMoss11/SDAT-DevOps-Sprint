package come.example;

public class AirportDTO {
    private Long id;
    private String name;
    private String code;
    private String cityName; // for including city name instead of the whole city object

    // The basic constructor
    public AirportDTO(Long id, String name, String code, String cityName) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.cityName = cityName;
    }

    // Getts n Setts
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }
}
