package com.gao.bing.fa.repository;

import com.gao.bing.fa.entity.Goods;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;


/**
 * @author : allengent@163.com
 * @date : 2019/10/22 09:56
 * description :
 */


@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long>, JpaSpecificationExecutor<Goods> {


    /**
     *    测试 mysql  update  是当前读 ，而不是 快照读  ，更新 读取以前的值然后设置新的值
     * @param id
     * @param var
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "update goods set stock = stock-:var where id=:id ", nativeQuery = true)
    Integer subStock(@Param("id") Long id,
                     @Param("var") Integer var);



    /**
     *    测试 mysql  悲观锁   排他锁
     *   此时不能用 nativeQuery，否则就报错 Illegal attempt to set lock mode on a native SQL query
     *   必须 在事务中 执行。。。 否则 报错 ： no transaction is in progress
     *   select for update获取的行锁会在当前事务结束时自动释放，因此必须在事务中使用。
     *   打印出 sql ，发现 sql 加了  for update
     * @param id
     * @return
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select g  from  Goods  g where  g.id=:id ")
    Goods getByGoodsId(@Param("id") Long id);


}