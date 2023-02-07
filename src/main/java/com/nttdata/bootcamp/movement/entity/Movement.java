package com.nttdata.bootcamp.movement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nttdata.bootcamp.movement.entity.enums.TypeCurrency;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "movement")
public class Movement {

    @Id
    private String id;

    private String dni;
    
    private String accountNumber;

    private Integer accountId;

    @NotBlank
    private String productType;

    @NotBlank
    private String movementNumber;

    @NotNull
    @DecimalMin(value = "0")
    private Double amount;

    @NotBlank
    private String status;

    @NotNull
    @DecimalMin(value = "0")
    private Double commission;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @CreatedDate
    private Date transactionDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @LastModifiedDate
    private Date modificationDate;
    
    private TypeCurrency currency;
    private String customerId;
    private String transactionType;

}
