package dmytro.hadiuchko.springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Setter
@Table(name = "shopping_cart")
@SQLDelete(sql = "UPDATE shopping_cart SET is_deleted = true WHERE id =?")
@SQLRestriction(value = "is_deleted=false")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<CartItems> cartItems;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
