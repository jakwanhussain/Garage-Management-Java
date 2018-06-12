/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package gmsis.models.specialistRepairs;

import gmsis.models.bookings.Booking;
import gmsis.models.customers.Customer;
import gmsis.models.parts.PartsItem;
import gmsis.models.vehicles.Vehicle;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Stephen
 */
@Entity
@Table(name = "specialist_repairs")
public class SpecialistRepair implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "cost")
    private double cost;
    
    @Column(name = "delivery_date")
    private LocalDate deliveryDate;
    
    @Column(name = "return_date")
    private LocalDate returnDate;
    
    @Column(name = "completed")
    private String completed = "No";
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPC", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private SpecialistRepairCentre SPC;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Vehicle vehicle;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="spcBookingPart")
    private PartsItem partItem;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = true, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Booking booking;
    
    
    
    public SpecialistRepair(){};

    public SpecialistRepair(double cost, LocalDate deliveryDate, LocalDate returnDate, String completed, SpecialistRepairCentre spc, Vehicle vehicle, Customer customer, PartsItem partItem, Booking booking) {
        this.cost=cost;
        this.deliveryDate=deliveryDate;
        this.returnDate=returnDate;
        this.completed=completed;
        this.SPC=spc;
        this.vehicle=vehicle;
        this.customer=customer;
        this.partItem=partItem;
        this.booking=booking;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    
        public SpecialistRepairCentre getSPC() {
        return SPC;
    }

    public void setSPC(SpecialistRepairCentre SPC) {
        this.SPC = SPC;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public PartsItem getPartItem() {
        return partItem;
    }

    public void setPartItem(PartsItem partItem) {
        this.partItem = partItem;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.cost) ^ (Double.doubleToLongBits(this.cost) >>> 32));
        hash = 37 * hash + Objects.hashCode(this.deliveryDate);
        hash = 37 * hash + Objects.hashCode(this.returnDate);
        hash = 37 * hash + Objects.hashCode(this.SPC);
        hash = 37 * hash + Objects.hashCode(this.customer);
        hash = 37 * hash + Objects.hashCode(this.partItem);
        hash = 37 * hash + Objects.hashCode(this.booking);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SpecialistRepair other = (SpecialistRepair) obj;
        if (Double.doubleToLongBits(this.cost) != Double.doubleToLongBits(other.cost)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.deliveryDate, other.deliveryDate)) {
            return false;
        }
        if (!Objects.equals(this.returnDate, other.returnDate)) {
            return false;
        }
        if (!Objects.equals(this.SPC, other.SPC)) {
            return false;
        }
        if (!Objects.equals(this.customer, other.customer)) {
            return false;
        }
        if (!Objects.equals(this.partItem, other.partItem)) {
            return false;
        }
        if (!Objects.equals(this.booking, other.booking)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SpecialistRepair{" + "id=" + id + ", cost=" + cost + ", deliveryDate=" + deliveryDate + ", returnDate=" + returnDate + ", SPC=" + SPC + ", customer=" + customer + ", partItem=" + partItem + ", booking=" + booking + '}';
    }    
}
