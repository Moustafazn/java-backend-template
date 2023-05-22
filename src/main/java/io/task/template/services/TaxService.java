package io.task.template.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaxService {


    public Double calculateTax(final Long income, final Long months) {
        return income * months * 0.3;
    }

}
