package com.gao.bing.fa.controller;

import com.gao.bing.fa.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : allengent@163.com
 * @date : 2019/10/22 10:01
 * description :
 */
@RestController
public class GoodsApi {

    @Autowired
    private GoodsService goodsService;

    @PostMapping("/buy1")
    public void buyGoods1(Long id,Integer num)
    {
        //错误的方法 ，可重复读 导致高并发 bug
        goodsService.updateStockWrong(id,num);

    }


    @PostMapping("/buy2")
    public void buyGoods2(Long id,Integer num)
    {
        //多版本 当前读
        goodsService.updateStockByCurrentRead(id,num);

    }

    @PostMapping("/buy3")
    public void buyGood3(Long id,Integer num)
    {
        //悲观锁
        goodsService.updateStockByPessimistic(id,num);

    }

    /**
     *
     * 测试乐观锁 时 需要添加 Goods 实体  version 字段，见Goods实体  最下面 注释掉的部分
     * @param id
     * @param num
     */
    @PostMapping("/buy4")
    public void buyGoods4(Long id,Integer num)
    {
        //乐观锁
        goodsService.updateStockOptimistic(id,num);


    }
}
