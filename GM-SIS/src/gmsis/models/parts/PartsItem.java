package gmsis.models.parts;

import gmsis.models.bookings.Booking;
import gmsis.models.customers.Customer;
import gmsis.models.specialistRepairs.SpecialistRepair;
import gmsis.models.vehicles.Vehicle;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
@Entity
@Table (name="partsitem")
public class PartsItem{
	@Id
	private String serialNumber;
	
        @Column
	private boolean available;
       
        @Column
        private LocalDate installedOn;
        
        @Column 
        private LocalDate warrantyEnd;
        
        @Column 
        private LocalDate added;
        
        @ManyToOne (fetch=FetchType.LAZY) 
        @JoinColumn (name="model", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
        private PartsModel model;
        
        
        //@OneToOne(fetch= FetchType.LAZY)
        //@PrimaryKeyJoinColumn
        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="repairPart")
        private SpecialistRepair repair;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="vehicle", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
        private Vehicle vehicle;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="bookings", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
        private Booking bookings;
        
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name="customer", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
        private Customer customer;
        
        public PartsItem(){
	}
        
        public PartsItem(String sn, PartsModel m, boolean a, Customer cus, SpecialistRepair sr,LocalDate add,Vehicle veh, Booking booking, LocalDate io, LocalDate wo){
            this.serialNumber=sn;
            this.available= a;
            this.model=m;
            this.repair= sr;
            this.vehicle=veh;
            this.customer=cus;
            this.added= add;
            this.bookings=booking;
            this.installedOn=io;
            this.warrantyEnd=wo;
        }

        public LocalDate getWarrantyEnd() {
            return warrantyEnd;
     }

        public void setWarrantyEnd(LocalDate warrantyEnd) {
           this.warrantyEnd = warrantyEnd;
         }
	public String getSerialNumber(){
	return serialNumber;
	}
	
        public void setSerialNumber(String nSN){
	this.serialNumber=nSN;
	}


    public void setInstalledOn(LocalDate installedOn) {
       this.installedOn = installedOn;
        }


    public LocalDate getAdded() {
        return added;
    }

    public void setAdded(LocalDate added) {
        this.added = added;
    }
    
    public LocalDate getInstalledOn() {
        return installedOn;
   }

    public Booking getBookings() {
        return bookings;
    }

    public void setBookings(Booking bookings) {
        this.bookings = bookings;
    }
    
    
    
    public void setRepair(SpecialistRepair repair) {
        this.repair = repair;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public SpecialistRepair getRepair() {
        return repair;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
	
    public boolean getAvailable(){
	return this.available;
    }
	
	public void setAvailable(boolean nAva){
	this.available=nAva;
	}
        
        public void setPartsModel(PartsModel model){
            this.model = model;
        }
        
        public PartsModel getPartsModel(){
                return this.model;
        }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.serialNumber);
        hash = 43 * hash + (this.available ? 1 : 0);
        hash = 43 * hash + Objects.hashCode(this.model);
        hash = 43 * hash + Objects.hashCode(this.repair);
        hash = 43 * hash + Objects.hashCode(this.vehicle);
        hash = 43 * hash + Objects.hashCode(this.customer);
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
        final PartsItem other = (PartsItem) obj;
        if (this.available != other.available) {
            return false;
        }
        if (!Objects.equals(this.serialNumber, other.serialNumber)) {
            return false;
        }
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        if (!Objects.equals(this.repair, other.repair)) {
            return false;
        }
        if (!Objects.equals(this.vehicle, other.vehicle)) {
            return false;
        }
        if (!Objects.equals(this.customer, other.customer)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PartsItem{" + "serialNumber=" + serialNumber + ", available=" + available + ", model=" + model + ", repair=" + repair + ", vehicle=" + vehicle + ", customer=" + customer + '}';
    }
    
        
}
