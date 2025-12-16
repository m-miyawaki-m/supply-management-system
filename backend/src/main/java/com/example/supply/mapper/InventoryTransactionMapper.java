package com.example.supply.mapper;

import com.example.supply.entity.InventoryTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InventoryTransactionMapper {

    List<InventoryTransaction> findAll();

    InventoryTransaction findById(@Param("id") Long id);

    void insert(InventoryTransaction transaction);

    List<InventoryTransaction> findBySupplyId(@Param("supplyId") Long supplyId);
}
