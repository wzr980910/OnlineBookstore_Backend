package com.bookStore.pojo.respojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wmh
 * @date 2024/01/15 16:53
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartBook {
   private Long id;
   private Long bookId;
   private Long userId;
   private String bookImg;
   private String bookName;
   private BigDecimal price;
   private Integer number;
}
