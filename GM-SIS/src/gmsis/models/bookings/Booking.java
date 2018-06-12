/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.models.bookings;

import gmsis.models.customers.Bill;
import gmsis.models.customers.Customer;
import gmsis.models.parts.PartsItem;
import gmsis.models.specialistRepairs.SpecialistRepair;
import gmsis.models.vehicles.Vehicle;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/*
* @author jakwan
*/
@Entity
@Table(name = "booking")
@OnDelete(action = OnDeleteAction.CASCADE)
public class Booking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_date", nullable = true)
    private LocalDate bookingDate;

    @Column(name = "booking_time", nullable = true)
    private String bookingTime;
    
    @Column(name = "mileage", nullable = true)
    private Integer mileage;
    
    @Column(name = "duration", nullable = true)
    private Double duration;

    @Column(name = "Mechanic_cost", nullable = true)
    private Double cost;
    
    @Column(name="detail", nullable = true)
    private String detail;
    
    @Column(name="type")
    private String type;
    
    @Column(name= "done")
    private String done;
    
    //changed all 3 nullable=false
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Customer customer;
    //place for vehicle_reg in database
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_Id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mechanic_Id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Mechanic mechanics;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookings", cascade = CascadeType.ALL)
    private List<PartsItem> part;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "booking", cascade = CascadeType.ALL)
    private List<SpecialistRepair> spcRepair;
            
            
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "booking")
    private Bill bill;

    public Booking(Customer customer, LocalDate bookingDate, String bookingTime, double duration, int mileage, double cost, String detail,Vehicle vehicle, String type,String bookingStatus, Mechanic mechanics, Bill bill){
        this.customer=customer;
        this.bookingDate = bookingDate;
        this.bookingTime = bookingTime;
        this.duration = duration;
        this.mileage = mileage;
        this.cost = cost;
        this.detail = detail;
        this.type = type;
        this.vehicle=vehicle;
        this.done=bookingStatus; 
        this.mechanics = mechanics;
        this.bill=bill;
    }

    public Booking() {
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PartsItem> getPart() {
        return part;
    }

    public void setPart(List<PartsItem> part) {
        this.part = part;
    }
    
    public Mechanic getMechanics() {
        return mechanics;
    }

    public void setMechanics(Mechanic mechanics) {
        this.mechanics = mechanics;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate booking) {
        this.bookingDate = booking;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String time) {
        this.bookingTime = time;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double dur) {
        this.duration = dur;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mil) {
        this.mileage = mil;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cst) {
        this.cost = cst;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Bill getBill() {
        return bill;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;

        Booking booking = (Booking) o;

        if (getId() != null ? !getId().equals(booking.getId()) : booking.getId() != null) return false;
        if (getBookingDate() != null ? !getBookingDate().equals(booking.getBookingDate()) : booking.getBookingDate() != null)
            return false;
        if (getBookingTime() != null ? !getBookingTime().equals(booking.getBookingTime()) : booking.getBookingTime() != null)
            return false;
        if (getMileage() != null ? !getMileage().equals(booking.getMileage()) : booking.getMileage() != null)
            return false;
        if (getDuration() != null ? !getDuration().equals(booking.getDuration()) : booking.getDuration() != null)
            return false;
        if (getCost() != null ? !getCost().equals(booking.getCost()) : booking.getCost() != null) return false;
        if (getDetail() != null ? !getDetail().equals(booking.getDetail()) : booking.getDetail() != null) return false;
        if (getCustomer() != null ? !getCustomer().equals(booking.getCustomer()) : booking.getCustomer() != null)
            return false;
        if (getVehicle() != null ? !getVehicle().equals(booking.getVehicle()) : booking.getVehicle() != null)
            return false;
        return getMechanics() != null ? getMechanics().equals(booking.getMechanics()) : booking.getMechanics() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getBookingDate() != null ? getBookingDate().hashCode() : 0);
        result = 31 * result + (getBookingTime() != null ? getBookingTime().hashCode() : 0);
        result = 31 * result + (getMileage() != null ? getMileage().hashCode() : 0);
        result = 31 * result + (getDuration() != null ? getDuration().hashCode() : 0);
        result = 31 * result + (getCost() != null ? getCost().hashCode() : 0);
        result = 31 * result + (getDetail() != null ? getDetail().hashCode() : 0);
        result = 31 * result + (getCustomer() != null ? getCustomer().hashCode() : 0);
        result = 31 * result + (getVehicle() != null ? getVehicle().hashCode() : 0);
        result = 31 * result + (getMechanics() != null ? getMechanics().hashCode() : 0);
        return result;
    }
    @Override
    public String toString() {
        return "Booking{" + "id=" + id + ", booking Date=" + bookingDate + ", booking time=" + bookingTime + ", duration=" + duration + ", detail =" + detail  + ", type=" + type + ", vehicle =" + vehicle  + ", part=" + part +'}';
    }
}       