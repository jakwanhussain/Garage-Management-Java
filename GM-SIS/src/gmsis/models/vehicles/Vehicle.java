/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.models.vehicles;

import gmsis.models.bookings.Booking;
import gmsis.models.customers.Customer;
import gmsis.models.parts.PartsItem;
import gmsis.models.specialistRepairs.SpecialistRepair;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by sabrinasaytac
 */

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "registration_number", nullable = false)
    private String registrationNumber;

    @Column(name = "make", nullable = false)
    private String make;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "engine_size", nullable = false)
    private double engineSize;

    @Column(name = "fuel_type", nullable = false)
    private String fuelType;

    @Column(name = "last_service", nullable = false)
    private LocalDate lastService;

    @Column(name = "colour", nullable = false)
    private String colour;

    @Column(name = "mot_renewal", nullable = false)
    private LocalDate motRenewal;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "vehicle_type", nullable = false)
    private VehicleType vehicleType;

    @Column(name = "warranty_start_date")
    private LocalDate warrantyStartDate;

    @Column(name = "warranty_end_date")
    private LocalDate warrantyEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Customer owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warranty_company", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private WarrantyCompany warrantyCompany;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle")
    private List<SpecialistRepair> specialistRepair;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle")
    private List<PartsItem> parts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle")
    private List<Booking> bookings;

    public Vehicle(String registrationNumber, String make, String model, double engineSize, String fuelType, LocalDate lastService, String colour, LocalDate motRenewal,
                   LocalDate warrantyStartDate, LocalDate warrantyEndDate, WarrantyCompany warrantyCompany, Integer mileage, VehicleType vehicleType, Customer owner, List<SpecialistRepair> specialistRepair) {


        this.owner = owner;
        this.registrationNumber = registrationNumber;
        this.make = make;
        this.model = model;
        this.engineSize = engineSize;
        this.fuelType = fuelType;
        this.lastService = lastService;
        this.colour = colour;
        this.motRenewal = motRenewal;
        this.warrantyStartDate = warrantyStartDate;
        this.warrantyEndDate = warrantyEndDate;
        this.warrantyCompany = warrantyCompany;
        this.mileage = mileage;
        this.vehicleType = vehicleType;
        this.specialistRepair = specialistRepair;

    }

    public Vehicle() {
    }

    public int getID() {
        return id;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public List<SpecialistRepair> getSpecialistRepair() {
        return specialistRepair;
    }

    public void setSpecialistRepair(List<SpecialistRepair> specialistRepair) {
        this.specialistRepair = specialistRepair;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<PartsItem> getParts() {
        return parts;
    }

    public void setParts(List<PartsItem> parts) {
        this.parts = parts;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(double engineSize) {
        this.engineSize = engineSize;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public LocalDate getLastService() {
        return lastService;
    }

    public void setLastService(LocalDate lastService) {
        this.lastService = lastService;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public LocalDate getMotRenewal() {
        return motRenewal;
    }

    public void setMotRenewal(LocalDate motRenewal) {
        this.motRenewal = motRenewal;
    }

    public LocalDate getWarrantyStartDate() {
        return warrantyStartDate;
    }

    public void setWarrantyStartDate(LocalDate warrantyStartDate) {
        this.warrantyStartDate = warrantyStartDate;
    }

    public LocalDate getWarrantyEndDate() {
        return warrantyEndDate;
    }

    public void setWarrantyEndDate(LocalDate warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
    }

    public WarrantyCompany getWarrantyCompany() {
        return warrantyCompany;
    }

    public void setWarrantyCompany(WarrantyCompany warrantyCompany) {
        this.warrantyCompany = warrantyCompany;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean isUnderWarranty() {


        LocalDate now = LocalDate.now();
        if ((warrantyEndDate == null) || (warrantyEndDate.compareTo(now) < 0)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 37 * hash + this.id;
        return hash;
    }


    @Override
    public boolean equals(Object obj) {

        if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (getClass() != obj.getClass())
            return false;

        final Vehicle vehicle = (Vehicle) obj;

        if (this.id != vehicle.id)
            return false;

        return true;
    }
}
