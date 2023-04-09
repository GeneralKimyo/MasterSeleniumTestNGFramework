package org.selenium.pom.objects;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.selenium.pom.constants.Codes;
import org.selenium.utils.OpenCsvUtils;

import java.io.IOException;

public class USTaxRates {
    private String country;

    private String stateCode;
    private String taxRate;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStateCode() {
        return stateCode;
    }
    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public USTaxRates getTaxRates(String filename, String state ) throws IOException, CsvValidationException {
        CSVReader taxRates =OpenCsvUtils.getDataFrom(filename);
        String[] cell;
        while((cell=taxRates.readNext())!= null)
        {
            String stateCode = cell[1];
            String taxRate = cell[4];
            if(stateCode.equals(Codes.getStateCode(state))){
                this.taxRate = taxRate;
            }
        }
        return this;
    }
}
