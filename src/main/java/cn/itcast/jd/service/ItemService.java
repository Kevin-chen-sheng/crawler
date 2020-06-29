package cn.itcast.jd.service;

import cn.itcast.jd.pojo.Item;

import java.util.List;

/**
 * @author kai
 * @date 2020/5/21 10:04
 */
public interface ItemService {

    public void save(Item item);

    public List<Item> findAll(Item item);
}

