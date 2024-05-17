package dmytro.hadiuchko.springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Setter
@Table(name = "cart_item")
@SQLDelete(sql = "UPDATE cart_item SET is_deleted = true WHERE id =?")
@SQLRestriction(value = "is_deleted=false")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    private ShoppingCart shoppingCart;
    @ManyToOne
    private Book book;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted = false;
}
