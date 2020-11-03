package com.mcubes.minoxidilbd.repository;

import com.mcubes.minoxidilbd.entity.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by A.A.MAMUN on 10/8/2020.
 */
@Repository
public interface ClientRepository extends CrudRepository<Client, String> {

    @Query("select new Client(c.email, c.password, c.role) from Client c where c.email=?1")
    Client findLoginInfoByEmail(String email);

    @Query("select c.name from Client c where c.email=?1")
    String findClientNameByEmail(String email);

}
