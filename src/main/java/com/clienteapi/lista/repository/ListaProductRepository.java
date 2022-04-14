package com.clienteapi.lista.repository;

import com.clienteapi.lista.entity.ListaProduct;
import com.clienteapi.lista.entity.ListaProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListaProductRepository extends JpaRepository<ListaProduct, ListaProductKey> {

    @Query("from ListaProduct as lp where lp.key.list.id = :listId")
    List<ListaProduct> findByList(@Param("listId") Integer listId);

}
