package pt.isel.mpd.data_with_optionals;

import java.time.LocalDate;

public class Insurance {
    private final String company;
    private final LocalDate expirationDate;

    public Insurance(String company, LocalDate expirationDate) {
        this.company = company;
        this.expirationDate = expirationDate;
    }

    public String getCompany() {
        return company;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
