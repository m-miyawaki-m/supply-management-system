package com.example.supply.mapper;

import com.example.supply.entity.Supply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SupplyMapper {

    List<Supply> findAll();

    Supply findById(@Param("id") Long id);

    void insert(Supply supply);

    void update(Supply supply);

    void delete(@Param("id") Long id);

    List<Supply> findByCategory(@Param("category") String category);
}
