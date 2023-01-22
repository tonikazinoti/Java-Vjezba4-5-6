package com.programiranjeujavi.vjezba4.repository;

import com.programiranjeujavi.vjezba4.entity.Device;
import com.programiranjeujavi.vjezba4.entity.DeviceReading;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DeviceReadingRepository extends JpaRepository<DeviceReading, Long> {
    List<DeviceReading> findAllByDevice_Id(Long deviceId, PageRequest pageRequest);
    List<DeviceReading> findDeviceReadingByDeviceAndTimePeriodBetweenOrderByTimePeriodAsc(Device device, @PastOrPresent LocalDate startDate, @PastOrPresent LocalDate endDate);
    List<DeviceReading> findDeviceReadingByDeviceAndTimePeriodBetweenOrderByTimePeriodAsc(Device device, @PastOrPresent LocalDate startDate, @PastOrPresent LocalDate endDate, PageRequest pageRequest);
    @Query("select SUM(dr.energyConsumptionKwh) from DeviceReading dr where dr.device = ?1 and dr.timePeriod BETWEEN ?2 and ?3")
    Integer findDeviceReadingByDeviceAndTimePeriodBetweenAndSumEnergyConsumption(Device device, @PastOrPresent LocalDate startDate, @PastOrPresent LocalDate endDate, PageRequest pageRequest);
}
