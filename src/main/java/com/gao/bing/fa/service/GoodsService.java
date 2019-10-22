package com.gao.bing.fa.service;

import com.gao.bing.fa.annotation.RetryOnOptimisticLockingFailure;
import com.gao.bing.fa.entity.Goods;
import com.gao.bing.fa.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : allengent@163.com
 * @date : 2019/10/22 09:59
 * description :
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;


    @Transactional
    public  void updateStockByCurrentRead(Long id, Integer num)
    {
        goodsRepository.subStock(id,num);
    }


    @Transactional
    public  void updateStockByPessimistic(Long id,Integer num)
    {
        Goods g= goodsRepository.getByGoodsId(id);
        g.setStock(g.getStock()-num);
        goodsRepository.save(g);
    }

    /**
     * 下面这个方式 在高并发 时 会出现 严重的bug
     * @param id
     * @param num
     */
    @Transactional
    public  void updateStockWrong(Long id,Integer num)
    {
        Goods g= null;
        try {
            g = goodsRepository.findById(id).orElseThrow(()->{ return new Exception("aa");});
        } catch (Exception e) {
            e.printStackTrace();
        }
        g.setStock(g.getStock()-num);
        goodsRepository.save(g);
    }


    /**
     * 测试乐观锁 时 需要添加 Goods 实体  version 字段，见Goods实体  最下面 注释掉的部分
     * @param id
     * @param num
     */
    @Transactional
    @RetryOnOptimisticLockingFailure
    public  void updateStockOptimistic(Long id,Integer num)
    {
        Goods g= null;
        try {
            g = goodsRepository.findById(id).orElseThrow(()->{ return new Exception("aa");});
        } catch (Exception e) {
            e.printStackTrace();
        }
        g.setStock(g.getStock()-num);
        goodsRepository.save(g);
    }
}
