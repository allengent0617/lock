package com.gao.bing.fa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author : allengent@163.com
 * @date : 2019/10/22 09:53
 * description :
 */



@Entity
@Data
@Table(name = "goods")
@NoArgsConstructor
@ToString
public class Goods implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 库存数量
     */
    @NotNull
    @Column(name = "stock")
    private Long stock;


/**
 * 测试乐观锁 时需要 取消 下面的注释
  */
//    @Version
//    @Column(name = "version")
//    private Long version;


}