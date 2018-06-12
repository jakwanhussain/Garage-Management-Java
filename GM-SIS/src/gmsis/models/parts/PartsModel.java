/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */
package gmsis.models.parts;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Abdullah
 */
@Entity
@Table (name="partsmodel")       
public class PartsModel {
    @Id
    private int id;
    
    @Column (name = "description")
    private String des;
    
    @Column
    private String name;
    
    @Column
    private double cost;
    
    @OneToMany(fetch=FetchType.LAZY)
    private List<PartsItem> item;
    
    public PartsModel(){}
    
    public PartsModel(int id, String des, String name,  double cost, List<PartsItem> item){
        this.id=id;
        this.des= des;
        this.name=name;
        this.cost=cost;
        this.item =item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public List<PartsItem> getItem() {
        return item;
    }

    public void setItem(List<PartsItem> item) {
        this.item = item;
    }
    
    public void addItem(PartsItem pi)
    {   this.item.add(pi);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.id;
        hash = 17 * hash + Objects.hashCode(this.des);
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.cost) ^ (Double.doubleToLongBits(this.cost) >>> 32));
        hash = 17 * hash + Objects.hashCode(this.item);
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
        final PartsModel other = (PartsModel) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Double.doubleToLongBits(this.cost) != Double.doubleToLongBits(other.cost)) {
            return false;
        }
        if (!Objects.equals(this.des, other.des)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.item, other.item)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PartsModel{" + "id=" + id + ", des=" + des + ", name=" + name + ", cost=" + cost + ", item=" + item + '}';
    }
    
    
}
