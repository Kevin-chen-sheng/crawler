package cn.itcast.jd.dao;

import cn.itcast.jd.pojo.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kai
 * @date 2020/5/21 10:01
 */
public interface ItemDao extends JpaRepository<Item,Long> {

}

