/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.models.bookings;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
/**
 *
 * @author jakwan
 */
@Entity
@Table(name="mechanics")
public class Mechanic implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="hourlyRate")
    private double hourlyRate;
    //see if it's ok
    @OneToMany(fetch = FetchType.LAZY)   
    private List<Booking> bookingList;
                 

    public List<Booking> getBookings() {
        return bookingList;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookingList = bookings;
    }
    
    public Mechanic(){}
    
    public Mechanic(double hourlyRate) {
       
        this.hourlyRate = hourlyRate;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public double getHourlyRate(){
        return hourlyRate;
    }
    public void setHourlyRate(double hourly){
        this.hourlyRate=hourly;
    }
    @Override
    public int hashCode() {
        int hash = 7;
        
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
        final Mechanic other = (Mechanic) obj;
        if (Double.doubleToLongBits(this.hourlyRate) != Double.doubleToLongBits(other.hourlyRate)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

                
     @Override
    public String toString() {
        return "Mechanics{" + "id=" + id + " , hourly rate=" + hourlyRate + '}';
    }
}
