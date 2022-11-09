package com.shopping.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "sarams")
@Getter @Setter @ToString
public class Saram {
    @Id
    @GenericGenerator(name = "id", strategy = "auto")
    private String id ;

    @Column(nullable = false, length = 30)
    private String name ;

    @Lob
    private String address ;

    @Column(nullable = false)
    private Integer salary ;
}
