package com.digitalgoldwallet.usermodule.repositories;



import com.digitalgoldwallet.usermodule.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    // ── used by getUserAddress() ─────────────────────────────────────────
    // findById(Integer addressId) is already inherited from JpaRepository
    // → Optional<Address> findById(Integer id)

    // ── used by GET /users/by_city/{city} ────────────────────────────────
    // Spring Data derives this from: findBy + City
    // SQL: SELECT * FROM addresses WHERE city = ?
    List<Address> findByCity(String city);

    // ── used by GET /users/by_state/{state} ──────────────────────────────
    // SQL: SELECT * FROM addresses WHERE state = ?
    List<Address> findByState(String state);

    // ── combined filter ───────────────────────────────────────────────────
    // SQL: SELECT * FROM addresses WHERE city = ? AND state = ?
    List<Address> findByCityAndState(String city, String state);

    // ── existence check before saving a duplicate address ────────────────
    // SQL: SELECT COUNT(*) > 0 FROM addresses WHERE street=? AND city=? AND postal_code=?
    boolean existsByStreetAndCityAndPostalCode(String street,
                                               String city,
                                               String postalCode);
}
